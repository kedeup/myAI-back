package com.freedom.chatmodule.wx.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @Author kede·W  on  2023/4/16
 */
@JacksonXmlRootElement(localName = "xml")
@Data
public class ResponseMsgBody {
    /**接收方帐号（收到的OpenID）*/
    @JacksonXmlProperty(localName = "ToUserName")
    private String ToUserName;
    /** 开发者微信号 */
    @JacksonXmlProperty(localName = "FromUserName")
    private String FromUserName;
    /** 消息创建时间 */
    @JacksonXmlProperty(localName = "CreateTime")
    private long CreateTime;
    /** 消息类型*/
    @JacksonXmlProperty(localName = "MsgType")
    private String MsgType;
    /** 文本消息的消息体 */
    @JacksonXmlProperty(localName = "Content")
    private String Content;
    // setter/getter
}
