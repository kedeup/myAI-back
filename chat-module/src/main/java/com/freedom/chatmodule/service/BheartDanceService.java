package com.freedom.chatmodule.service;

import com.freedom.chatmodule.tools.danmaku.TimeTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.xml.bind.DatatypeConverter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author kede·W  on  2023/3/26
 */
@Slf4j
public class BheartDanceService {

    public static final String heartInfo = "0000001f0010000100000002000000015b6f626a656374204f626a6563745d";

    public void startHeartDance(WebSocketSession session){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                byte[] bytes1 = DatatypeConverter.parseHexBinary(heartInfo);
                BinaryMessage binaryMessage = new BinaryMessage(bytes1);
                session.sendMessage(binaryMessage);
                log.info("heart dance time is {}", TimeTools.getCurrentTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 30, 30, TimeUnit.SECONDS);
    }
}
