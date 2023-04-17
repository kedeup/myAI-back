package com.freedom.chatmodule.wx.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;


/**
 * @Author kede·W  on  2023/4/16
 */
@JacksonXmlRootElement(localName = "xml")
@Data
public class ReceiveEventBody {
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
    /**消息类型*/
    @JacksonXmlProperty(localName = "Event")
    private String Event;
    /**消息类型*/
    @JacksonXmlProperty(localName = "EventKey")
    private String EventKey;
    @JacksonXmlProperty(localName = "Ticket")
    private String Ticket;




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
