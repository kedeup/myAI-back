package com.freedom.chatmodule.wx.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;


/**
 * @Author kede·W  on  2023/4/17
 */
@JacksonXmlRootElement(localName = "xml")
@Data
public class ReceiveText extends ReceiveMsgBody {

    /** 文本消息的消息体 */
    @JacksonXmlProperty(localName = "Content")
    private String Content;
}
