package com.freedom.chatmodule.tools;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


/**
 * @Author kede·W  on  2023/4/17
 */
public class XmlTools {
    public static String toXml(Object object) throws JsonProcessingException  {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(object);
        return xml;
    }


    public static <T> T fromXml(String xml, Class<T> valueType) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return (T) xmlMapper.readValue(xml, valueType);
    }
}
