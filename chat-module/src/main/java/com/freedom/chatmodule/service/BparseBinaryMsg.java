package com.freedom.chatmodule.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.freedom.chatmodule.common.enums.Danmaku.DMMessageType;
import com.freedom.chatmodule.entity.Danmaku.DataPacket;
import com.freedom.chatmodule.tools.danmaku.BinaryTools;
import com.freedom.chatmodule.tools.danmaku.ByteTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kede·W  on  2023/3/27
 */
@Slf4j
@Service
public class BparseBinaryMsg {
    public static void parseBinaryMsg(ByteBuffer buffer) {
        BinaryTools binaryTools = new BinaryTools(buffer);
        try {
            DataPacket dataPacket = binaryTools.getDataPacket();
//            log.info("获取到的数据包：{}", dataPacket.toString());

            int protocolVersion = dataPacket.getProtocolVersion();
            int operationType = dataPacket.getOperationType();
            log.info("协议号：{}===========操作类型：{}",protocolVersion,
                    operationType);
            byte[] content = dataPacket.getContent();

            switch (protocolVersion) {
                case 0:
                    parseNotice(content, operationType);
                    break;
                case 1:
                    //语句
                    parsePopularity(content, operationType);
                    break;
                case 2:
                    log.info("协议号2：{}",protocolVersion);
                    break;
                case 3:
                    //broli解压
                    byte[] decompress = decompress(content);
                    List<byte[]> contentList = binaryDecode(decompress);
                    parseNoticeList(contentList,operationType);
                    break; //可选
                default: //可选
                    //语句
                    log.error("数据包解析失败！!!获取到的版本号为：{}", protocolVersion);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseNoticeList(List<byte[]> contentList, int operationType) {
        contentList.forEach(content ->{
            try {
                parseNotice(content, operationType);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    private static void parseObject(JSONObject jsonObject){
        String cmd = jsonObject.get("cmd").toString();
        if(cmd.equals(DMMessageType.DANMU_MSG.getCmd())){
            JSONArray info1 = (JSONArray) jsonObject.get("info");
            log.info("info Json :{}",info1);
            String dmMsg = info1.get(1).toString();
            JSONArray speakInfo = (JSONArray)info1.get(2);
            String speakName = speakInfo.get(1).toString();

            log.info("{} 说, {}", speakName, dmMsg);

        }else {
            log.info("非弹幕信息忽略！");
        }
    }


    private static void parseNotice(byte[] content, int operationType) throws UnsupportedEncodingException {
        switch (operationType) {
            case 5:
                String notice = ByteTools.byteArrayToString(content);
                log.info("字符串结构：{}",notice);
                JSONObject jsonObject = JSON.parseObject(notice);
                parseObject(jsonObject);
//处理返回对象（目前只关注 弹幕信息
                break; //可选
            default: //可选
                //语句
                log.debug("通知解析失败！!!获取到的操作类型为：{}", operationType);
        }
    }

    private static void parsePopularity(byte[] content, int operationType) throws UnsupportedEncodingException {

        switch (operationType) {
            case 3:
                int popularity = ByteTools.getInt(content);
                log.info("解析后的人气值：{}", popularity);
                break; //可选
            case 8:
                //语句
                String okCode = ByteTools.byteArrayToString(content);
                log.info("操作类型为8的返回信息：{}", okCode);
                break; //可选
            default: //可选
                //语句
                log.debug("数据包解析失败！!!获取到的操作类型为：{}", operationType);
        }

    }


    public static byte[] decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BrotliCompressorInputStream brotliInputStream =
                     new BrotliCompressorInputStream(inputStream)) {
            final byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = brotliInputStream.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
        }
        return outputStream.toByteArray();
    }

    public static List<byte[]> binaryDecode(byte[] subCompressedData) throws IOException {
        int oriLen = subCompressedData.length;
        ArrayList<byte[]>  result = new ArrayList<>();
        ByteBuffer wrap = ByteBuffer.wrap(subCompressedData);

        int oriPos = 0;
        int currentPacketLength = 0;
        int content_length = 0;
        int headerLen = 16;
        while (oriLen>0){
            wrap.position(oriPos);
            currentPacketLength = wrap.getInt();
            content_length = currentPacketLength - headerLen;
            byte[] content = new byte[content_length];
            wrap.position(oriPos + headerLen);
            wrap.get(content,0,content_length);
            result.add(content);
            oriPos += currentPacketLength;
            oriLen -= currentPacketLength;
        }
        return result;
    }


    public static void main(String[] args) throws IOException {

//        byte[] bytes = DatatypeConverter.parseHexBinary(s);
        String s = "{\"uid\":399231588,\"roomid\":5975890,\"protover\":3,\"platform\":\"web\",\"type\":2,\"key\":\"EmN9hvn4CtdHQPqTuZqfg-70POAoxxvuitQgDxeHRpo7n18CmSzK4MgZj5EwXwTt8RPwZBxb4vh74NxLXiKzb0nWey23r53nwBOc15xaZXdUUS-dnNVw0RjOTJv3mlnYtlpcbNHJdlKoenWio1AbWVxMhQ==\"}";
        byte[] bytes = s.getBytes();
        int length = bytes.length;
        System.out.println(length);
//        byte[] decompress = decompress(bytes);
//        ByteBuffer wrap = ByteBuffer.wrap(decompress);
//        wrap.position(16);
//
//        int content_length = wrap.limit() - 16;
////        int content_length = getPacketLength();
//        byte[] content = new byte[content_length];
//        wrap.get(content);
//        String okCode = ByteTools.byteArrayToString(content);
//        System.out.println(okCode);
//        int anInt = ByteBuffer.wrap(bytes).getInt(0);
//        short aShort = ByteBuffer.wrap(bytes).getShort(4);
//        short aShort1 = ByteBuffer.wrap(bytes).getShort(6);
//        int anInt1 = ByteBuffer.wrap(bytes).getInt(8);
//        int anInt2 = ByteBuffer.wrap(bytes).getInt(12);
//        System.out.println(anInt);
//        System.out.println(aShort);
//        System.out.println(aShort1);
//        System.out.println(anInt1);
//        System.out.println(anInt2);

    }
}
