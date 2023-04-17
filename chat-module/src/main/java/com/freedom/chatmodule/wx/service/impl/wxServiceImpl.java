package com.freedom.chatmodule.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.freedom.chatmodule.common.OpenaiRequestUtil;
import com.freedom.chatmodule.domain.UserAuthInfo;
import com.freedom.chatmodule.domain.UserInfo;
import com.freedom.chatmodule.domapper.UserAuthInfoMapper;
import com.freedom.chatmodule.domapper.UserInfoMapper;
import com.freedom.chatmodule.tools.XmlTools;
import com.freedom.chatmodule.tools.danmaku.TimeTools;
import com.freedom.chatmodule.wx.WxProperties;
import com.freedom.chatmodule.wx.entity.ReceiveEventBody;
import com.freedom.chatmodule.wx.entity.ReceiveText;
import com.freedom.chatmodule.wx.entity.ResponseMsgBody;
import com.freedom.chatmodule.wx.enums.MsgType;
import com.freedom.chatmodule.wx.service.IwxdevService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

/**
 * @Author kede·W  on  2023/4/16
 */
@Service
@Slf4j
public class wxServiceImpl implements IwxdevService {
    private final static  String ACCESS_TOKEN_URL="https://api.weixin.qq" +
            ".com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    @Autowired
    WxProperties wxProperties;
    @Autowired
    OpenaiRequestUtil requestUtil;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserAuthInfoMapper authInfoMapper;
    @Override
    public String getAccessToken() {
        if (wxProperties.getAccesstoken()==null
                || wxProperties.getAccesstoken().isEmpty()
                || TimeTools.getCurrentTimestamp() > wxProperties.getExpiredDDL()){
            JSONObject jsonObject = requestAccessToken();
            String access_token = jsonObject.get("access_token").toString();
            String expires_in = jsonObject.get("expires_in").toString();
            wxProperties.setExpiredDDL(TimeTools.getCurrentTimestamp() + Integer.parseInt(expires_in));
            wxProperties.setAccesstoken(access_token);
        }
        return wxProperties.getAccesstoken();
    }



    private JSONObject requestAccessToken(){
        String appid = wxProperties.getAppid();
        String appsecret = wxProperties.getAppsecret();
        String accessTokenUrl = String.format(ACCESS_TOKEN_URL,appid,appsecret);
        String accesstokenstr="";
        try {
            accesstokenstr = requestUtil.get(accessTokenUrl);
            log.info("access_token = {}", accesstokenstr);
        } catch (Exception e) {
            log.error("获取access_token失败。", e);
        }
        return JSON.parseObject(accesstokenstr);
    }


    @Override
    public String handleMsg(String xmlstr) throws
            ParserConfigurationException, SAXException, IOException {
        String msgType = getMsgTypeFromXml(xmlstr);
        if ( msgType == null || msgType.isEmpty()){
            return null;
        }
        switch (msgType){
            case "text":
                ReceiveText receiveText = XmlTools.fromXml(xmlstr, ReceiveText.class);
                return textHandler(receiveText);
//            case "image":
//                return image;
//            case "voice":
//                return voice;
//            case "video":
//                return video;
//            case "shortvideo":
//                return shortvideo;
//            case "location":
//                return location;
//            case "link":
//                return link;
//            case "music":
//                return music;
//            case "news":
//                return news;
            case "event":
                ReceiveEventBody receiveEventBody = XmlTools.fromXml(xmlstr, ReceiveEventBody.class);
                return eventHandler(receiveEventBody);
            default:
                return null;
        }
    }


    private String textHandler(ReceiveText receiveText) throws JsonProcessingException {
        ResponseMsgBody responseMsgBody = new ResponseMsgBody();
        responseMsgBody.setToUserName(receiveText.getFromUserName());
        responseMsgBody.setFromUserName(receiveText.getToUserName());
        responseMsgBody.setCreateTime(new Date().getTime());
        responseMsgBody.setMsgType(MsgType.text.getmsgType());
        responseMsgBody.setContent("获取信息<a href=\"http://www.qq.com\" " +
                "data-miniprogram-appid=\"wx7b67a63aa35d1f58\" data-miniprogram-path=\"pages/tabbar/index/index\">点击跳小程序</a>");
        String result = XmlTools.toXml(responseMsgBody);
        return result;
    }


    private String eventHandler(ReceiveEventBody receiveEventBody) throws JsonProcessingException {
        String eventName = receiveEventBody.getEvent();
        ResponseMsgBody responseMsgBody = new ResponseMsgBody();
//        发送者的appid
        String fromUserName = receiveEventBody.getFromUserName();
        String toUserName = receiveEventBody.getToUserName();
        log.info("开发者微信号：{}", toUserName);
        log.info("登陆者秘钥：{}", toUserName);
        String result="";
        switch (eventName){
            case "subscribe":
                UserInfo newUserInfo = new UserInfo();
                newUserInfo.setUsername(fromUserName);
                userInfoMapper.insert(newUserInfo);

                UserAuthInfo userAuthInfo = new UserAuthInfo();
                userAuthInfo.setUserId(userInfoMapper.getUserIdByUsername(fromUserName));
                userAuthInfo.setIdentityType("订阅号");
                userAuthInfo.setIdentifier("openid");
                userAuthInfo.setCredential(fromUserName);
                authInfoMapper.insert(userAuthInfo);

                responseMsgBody.setToUserName(fromUserName);
                responseMsgBody.setFromUserName(toUserName);
                responseMsgBody.setCreateTime(new Date().getTime());
                responseMsgBody.setMsgType(MsgType.text.getmsgType());
                responseMsgBody.setContent(String.format("%s \n 您登陆成功,",
                        fromUserName));
                result = XmlTools.toXml(responseMsgBody);
                return result;

            case "unsubscribe":
                long userId = userInfoMapper.getUserIdByUsername(fromUserName);
                userInfoMapper.deleteByPrimaryKey(userId);
                authInfoMapper.deleteByUserId(userId);
//取消订阅事件无法发送回复
                responseMsgBody.setToUserName("osFOUw3iRmudU_oJSLTN4Ei6VsdA");
                responseMsgBody.setFromUserName(toUserName);
                responseMsgBody.setCreateTime(new Date().getTime());
                responseMsgBody.setMsgType(MsgType.text.getmsgType());
                responseMsgBody.setContent(String.format("%s 取关了,",
                        receiveEventBody.getFromUserName()));
                result = XmlTools.toXml(responseMsgBody);

                return result;
            default:
                result = XmlTools.toXml(responseMsgBody);
                return result;

        }

    }


    @Override
    public String getMsgTypeFromXml(String xmlstring) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlstring));
        Document doc = builder.parse(is);

        // get message type from XML
        NodeList msgTypeNodes = doc.getElementsByTagName("MsgType");
        String msgType = msgTypeNodes.item(0).getTextContent();
        System.out.println(
                "========================msgType=============================");
        System.out.println(msgType);
        return msgType;
    }

}
