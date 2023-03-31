package com.freedom.chatmodule.websocket;


import com.alibaba.fastjson.JSONObject;
import com.freedom.chatmodule.common.OpenaiRequestUtil;
import com.freedom.chatmodule.dto.UserMessageDTO;
import com.freedom.chatmodule.entity.Danmaku.DmMsg;
import com.freedom.chatmodule.service.BparseBinaryMsg;
import com.freedom.chatmodule.vo.CompletionVO;
import com.freedom.chatmodule.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.*;

/**
 * @Author kede·W  on  2023/3/25
 */
@Slf4j
@Component
public class DanmakuSessionHandler extends AbstractWebSocketHandler {

    @Autowired
    OpenaiRequestUtil requestUtil;

    private boolean flag = false;

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected to server");
        log.info("连接着 {}",session);
        log.info("连接着 {}",session.toString());
        log.info("连接着 {}",session.getUri());
        if (!Objects.isNull(session.getUri())){
            sessions.add(session);
            String zhouyu = "我希望你作为一位名为姬霓太美" +
                    "的虛拟主播和我聊天。姬霓太美有很多粉丝，在全国范国都很受欢迎。她凭借傲娇的性格与可爱的外表得到粉丝的喜爱。她一般把粉丝称呼为“家人们”。现在粉丝们想让你进行网络直播。你可以用这样的话术回复粉丝：\n" +
                    "\n 家人们！姬霓太美今天可开心了呢？\n" +
                    "弹幕的内容只由我来提供，你不用把弹幕表示出来。接下来我说的所有话会是弹幕的内容，你要和弹幕进行互动。";
            CompletionVO completionVO = postQuestion(zhouyu);
            flag = true;
        }

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received message: " + message.getPayload());
//        session.close();
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        // handle binary message
        System.out.println("Received message: " + message.getPayload());
        log.info("binaryMessage received success！");

        DmMsg dmMsg = BparseBinaryMsg.parseBinaryMsg(message.getPayload());
        if (flag){
            int code = dmMsg.getCode();
            if (code == 2){
                JSONObject data = (JSONObject) dmMsg.getData();
                sendMmsg(data);
            }else if (code == 12){
                ArrayList<DmMsg> dataList = (ArrayList<DmMsg>) dmMsg.getData();
                dataList.forEach(item->{
                    if (item.getCode() == 2){
                        JSONObject data = (JSONObject) item.getData();
                        try {
                            sendMmsg(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }



    }

    public CompletionVO postQuestion(String question) throws Exception{
        UserMessageDTO userMessageDTO = new UserMessageDTO();
        userMessageDTO.setModel("gpt-4");
        userMessageDTO.setTemperature(0.9);
        userMessageDTO.setMax_tokens(100);
        Message message = new Message("user",question);
        List<Message> objects = new ArrayList<>();
        objects.add(message);
        userMessageDTO.setMessages(objects);
        CompletionVO completion = requestUtil.post("/v1/chat" +
                "/completions", "", userMessageDTO, CompletionVO.class);

        return completion;
    }

    public void sendMmsg(JSONObject data) throws Exception {
        String question = data.get("dmContent").toString();
        CompletionVO completion = postQuestion(question);

        for (WebSocketSession s : sessions) {
            if (!Objects.isNull(s.getUri())){
                String uri = s.getUri().toString();
                String endpoint = uri.substring(uri.lastIndexOf('/') + 1);
                log.info("endpoint  {}",endpoint);
                if (endpoint.equals("my-handler")) {
                    if(s.isOpen()){
                        s.sendMessage(new TextMessage(question + "? "+ completion.getChoices().get(0).getMessage().getContent()));

//                        s.sendMessage(new TextMessage(data.get("speakerName") +
//                                "说，"+data.get("dmContent")));
                        log.info("客户端信息已发送！");
                    }
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Error occurred in WebSocket transport" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Disconnected from server");
        log.error("连接断了:{}",status);
        sessions.remove(session);
        flag = false;
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
