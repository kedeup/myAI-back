package com.freedom.chatmodule.wx.controller;

import com.freedom.chatmodule.utils.SecurityEncodeUtils;
import com.freedom.chatmodule.wx.service.IwxdevService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author kede·W  on  2023/4/15
 */
@Slf4j
@RestController
public class WechatServerController {
    @Autowired
    IwxdevService wxdevService;


    private final static String TOKEN = "tongfuyule1234";

    @GetMapping("/wxfirstcheck")
    public String firstCheck(HttpServletRequest request) throws Exception {
        log.info("\n ==========================微信服务器初次校验签名==========================\n");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String[] arr = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        String sha1Str =
                SecurityEncodeUtils.sha1Encode(arr[0] + arr[1] + arr[2]);

        return sha1Str.equals(signature) ? echostr : "";

    }
    @PostMapping(value = "wxfirstcheck",consumes = {"text/xml;charset=UTF-8"}
    , produces = {"text/xml;charset=UTF-8"})
    public String getUserMessage(@RequestBody String xmlString,
                                          HttpServletRequest request) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("xmlString" +
                "============================================\n");
        System.out.println(xmlString);
//        log.info("用户发来的消息===>:", receiveMsgBody);
        String responseMsgBody = wxdevService.handleMsg(xmlString);
        return responseMsgBody;

    }
}
