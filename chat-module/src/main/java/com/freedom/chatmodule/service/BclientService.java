package com.freedom.chatmodule.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.freedom.chatmodule.entity.Danmaku.AuthData;
import com.freedom.chatmodule.entity.Danmaku.Conf;
import com.freedom.chatmodule.entity.Danmaku.HostServer;
import com.freedom.chatmodule.httptool.danmaku.DanmakuInfo;
import com.freedom.chatmodule.tools.danmaku.ByteTools;
import com.freedom.chatmodule.tools.danmaku.DanmakuTools;
import com.freedom.chatmodule.tools.danmaku.TimeTools;
import com.freedom.chatmodule.websocket.DanmakuSessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @Author kede·W  on  2023/3/25
 */
@Slf4j
@Service
public class BclientService {
    private static final String SERVER_URL = "wss://broadcastlv.chat.bilibili.com:2245/sub";

    @Autowired
    private WebSocketClient webSocketClient;
    @Autowired
    private DanmakuSessionHandler sessionHandler;

    @Autowired
    private DanmakuInfo danmakuInfo;

    private final static String headerStr = "000000ff001000010000000700000001";


    public void requestDanmaku() throws JsonProcessingException {
//        long roomId = 5975890;
        long roomId = 27248922;
        long uid = 0;  //我的xioazhi
        Conf conf = danmakuInfo.httpGetConf(roomId);
        List<HostServer> hostServerList = conf.getHost_list();
        log.debug(hostServerList.toString());
        String wssUrl = DanmakuTools.GetWssUrl(hostServerList);

        AuthData authData = new AuthData(uid, roomId, conf.getToken());
        log.info("认证信息：{}",authData.toString());
//      16进制的字符串 每两个字符 等价于一个字节
//       直接调用方法将16进制字符串转换为字节数组
//        java对象转换为字符串
        //        获取字符串的16进制表示再转字节数组，或者指定编码方式直接转换为字节数组
        String auth = JSON.toJSONString(authData);

        ByteTools.strToHexString(auth);
        byte[] bytes2 = auth.getBytes();
        int len2 = bytes2.length;
        int lenSum = len2 + 16;
        String lenSumStr = ByteTools.intToHexString(lenSum);

        String headerPost =
                headerStr.substring(0, 6) + lenSumStr + headerStr.substring(6 + 2);
        log.info("headerPost：{}",headerPost);

        byte[] bytes1 = DatatypeConverter.parseHexBinary(headerPost);
        log.info("bytes1.length {}",bytes1.length);
        log.info("bytes2.length {}",bytes2.length);
        byte[] result = new byte[bytes1.length + len2];
        System.arraycopy(bytes1, 0, result, 0, bytes1.length);
        System.arraycopy(bytes2, 0, result, bytes1.length, len2);
        try {
            ListenableFuture<WebSocketSession> future =
                    webSocketClient.doHandshake(sessionHandler, wssUrl);
            WebSocketSession webSocketSession = future.get();

            BinaryMessage binaryMessage = new BinaryMessage(result);
            webSocketSession.sendMessage(binaryMessage);
            log.info("first binaryMessage send success");
            if(webSocketSession.isOpen()){
                BheartDanceService bheartDanceService = new BheartDanceService();
                bheartDanceService.startHeartDance(webSocketSession);
            }
        }catch (Exception e) {

            e.printStackTrace();
        }
    }
}
