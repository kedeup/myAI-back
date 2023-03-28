package com.freedom.chatmodule.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.Collections;
import java.util.List;

/**
 * @Author kede·W  on  2023/3/25
 */
@Configuration
public class StompClientConfig {

    @Autowired
    private WebSocketClient webSocketClient;

    @Bean
    public WebSocketStompClient stompClient() {
        Transport webSocketTransport = new WebSocketTransport(webSocketClient);
        List<Transport> transports = Collections.singletonList(webSocketTransport);
        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        // Other configurations...
        return stompClient;
    }

}


/*使用WebSocketStompClient可以方便地处理WebSocket消息，特别是使用STOMP协议时。
相对于纯粹使用WebSocket客户端API来说，WebSocketStompClient提供了更高级别的抽象，可以自动解析和序列化STOMP消息，并且支持订阅、取消订阅等常用操作。

此外，WebSocketStompClient还提供了一个StompSession接口，表示客户端与服务器之间建立的一个STOMP会话。
我们可以使用这个接口来发送STOMP消息、订阅主题、取消订阅等操作，从而更加方便地管理WebSocket连接。

总之，使用WebSocketStompClient能够使得代码更加清晰简洁，并且可以提高开发效率*/
