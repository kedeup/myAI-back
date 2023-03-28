package com.freedom.chatmodule.websocket;


import com.freedom.chatmodule.entity.Danmaku.DataPacket;
import com.freedom.chatmodule.service.BparseBinaryMsg;
import com.freedom.chatmodule.tools.danmaku.BinaryTools;
import com.freedom.chatmodule.tools.danmaku.ByteTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.*;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.xml.bind.DatatypeConverter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author kede·W  on  2023/3/25
 */
@Slf4j
@Component
public class DanmakuSessionHandler extends AbstractWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to server");

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
//        session.close();
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        // handle binary message
        System.out.println("Received message: " + message.getPayload());
        log.info("binaryMessage received success！");

        BparseBinaryMsg.parseBinaryMsg(message.getPayload());
//        try {
//            DataPacket dataPacket = binaryTools.getDataPacket();
//            String content =
//                    ByteTools.byteArrayToString(dataPacket.getContent());
//            log.info("获取到的数据包：{}", dataPacket.toString());
//            log.info("解析后的数据包：{}", content);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Error occurred in WebSocket transport" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Disconnected from server");
        log.error("连接断了:{}",status);
        // 连接关闭时尝试重新连接
//        while (true) {
//            try {
//                session = new StandardWebSocketClient().doHandshake(this, "ws://localhost:8080/myendpoint").get();
//                this.session = session;
//                break; // 重连成功则退出循环
//            } catch (Exception e) {
//                Thread.sleep(5000); // 重连失败则等待一段时间后再次尝试
//            }
//        }
    }
}
