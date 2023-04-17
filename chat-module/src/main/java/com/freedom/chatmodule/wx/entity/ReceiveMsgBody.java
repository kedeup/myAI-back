package com.freedom.chatmodule.wx.entity;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;



/**
 * @Author kede·W  on  2023/4/16
 */
@JacksonXmlRootElement(localName = "xml")
@Data
public class ReceiveMsgBody {
    /**开发者微信号*/
    @JacksonXmlProperty(localName = "ToUserName")
    private String ToUserName;
    /** 发送消息用户的openId */
    @JacksonXmlProperty(localName = "FromUserName")
    private String FromUserName;
    /** 消息创建时间 */
    @JacksonXmlProperty(localName = "CreateTime")
    private long CreateTime;
    /**消息类型*/
    @JacksonXmlProperty(localName = "MsgType")
    private String MsgType;
    /** 消息ID，根据该字段来判重处理 */
    @JacksonXmlProperty(localName = "MsgId")
    private long MsgId;
    /** 消息ID，根据该字段来判重处理 */
    @JacksonXmlProperty(localName = "MsgDataId")
    private long MsgDataId;
    /** 消息ID，根据该字段来判重处理 */
    @JacksonXmlProperty(localName = "Idx")
    private long Idx;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
    // setter/getter



}
