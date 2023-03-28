package com.freedom.chatmodule.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author kede·W  on  2023/3/25
 */
@Component
public class WSMyHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
/*fterConnectionEstablished()方法用于在连接建立时发送验证信息，并开启一个定时任务每30秒发送一次心跳包。
handleTextMessage()方法用于处理从服务器接收到的消息。*/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 发送验证信息
//        JsonObject authMsg = new JsonObject();
//        authMsg.addProperty("uid", 0);
//        authMsg.addProperty("roomid", 1234567);
//        String authMsgStr = objectMapper.writeValueAsString(authMsg);
//        session.sendMessage(new TextMessage(authMsgStr));

        // 开始心跳
//        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
////            try {
////                JsonObject heartbeatMsg = new JsonObject();
////                heartbeatMsg.addProperty("type", "heartbeat");
////                String heartbeatMsgStr = objectMapper.writeValueAsString(heartbeatMsg);
////                session.sendMessage(new TextMessage(heartbeatMsgStr));
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }, 30, 30, TimeUnit.SECONDS);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msgStr = message.getPayload();
        // 处理消息
        System.out.println("Received message: " + msgStr);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        System.err.println("WebSocket transport error");
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Disconnected from WebSocket server");
    }


}
