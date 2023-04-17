package com.freedom.chatmodule.wx.service;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @Author kede·W  on  2023/4/15
 */
public interface IwxdevService {

    Object getAccessToken();

    String handleMsg(String xmlstr) throws  ParserConfigurationException, SAXException, IOException;
    String getMsgTypeFromXml(String xmlstring) throws IOException, SAXException, ParserConfigurationException;
}
