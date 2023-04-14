package com.freedom.chatmodule.service.QQ;

import com.freedom.chatmodule.common.OpenaiRequestUtil;
import com.freedom.chatmodule.config.qqbot.BotConfigMap;
import com.freedom.chatmodule.config.qqbot.BotListProperties;
import com.freedom.chatmodule.dto.UserMessageDTO;
import com.freedom.chatmodule.utils.BeanUtil;
import com.freedom.chatmodule.utils.SpringUtils;
import com.freedom.chatmodule.vo.CompletionVO;
import com.freedom.chatmodule.vo.Message;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author kede·W  on  2023/4/2
 */
@Slf4j
public class CommEventListener extends SimpleListenerHost {
    private  Bot bot;
    private  BotListProperties.BotConfig botConfig ;

    private OpenaiRequestUtil openaiRequestUtil;


    public CommEventListener(Bot bot) {
        this.bot = bot;
        Map<Long, BotListProperties.BotConfig> botConfigMap =
                SpringUtils.getBean("BOTCONFIGMap");
        this.botConfig = botConfigMap.get(bot.getId());

        this.openaiRequestUtil = SpringUtils.getBean(OpenaiRequestUtil.class);
    }

//    @EventHandler
    public void handlerGroupMessage(GroupMessageEvent event) {
        // 处理群聊消息
        String message = event.getMessage().contentToString();
        long groupId = event.getGroup().getId();
        long senderId = event.getSender().getId();

        // TODO: 处理消息逻辑
    }


    @EventHandler
    public void handlerStrangerMessage(StrangerMessageEvent event) throws Exception {
        // 处理陌生人信息
        long adminId = botConfig.getAdmin_id();
        long senderId = event.getSender().getId();
        log.info(String.valueOf(event));


        if(adminId != senderId){
            onStrangerMessage(event,senderId);
            return;
        }
    }

    @EventHandler
    public void handlerFriendMessage(FriendMessageEvent event) throws Exception {
        // 处理群聊消息
        long adminId = botConfig.getAdmin_id();
        long senderId = event.getSender().getId();
        log.info(String.valueOf(event));


        if(adminId != senderId){
            onFriendMessage(event,senderId);
            return;
        }
        onAdminMessage(event,adminId);
    }


    public void onAdminMessage(FriendMessageEvent event, long adminId) throws Exception {
        String message = event.getMessage().contentToString().trim();
        CompletionVO completionVO = postQuestion(message);

        String content = completionVO.getChoices().get(0).getMessage().getContent();
        Friend admin = bot.getFriend(adminId);
        if (Objects.isNull(admin)){
            return;
        }
        admin.sendMessage(content);
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
        CompletionVO completion = openaiRequestUtil.post("/v1/chat" +
                "/completions", "", userMessageDTO, CompletionVO.class);
        return completion;
    }

    public void onFriendMessage(FriendMessageEvent event, long friendId) {
        // 处理非管理员的好友事件
        Objects.requireNonNull(bot.getFriend(friendId)).sendMessage("对不起，您不是我的主人！");

        // TODO: 处理消息逻辑
    }

    public void onStrangerMessage(StrangerMessageEvent event, long StrangerId) {
        // 处理非管理员的好友事件
        Objects.requireNonNull(bot.getFriend(StrangerId)).sendMessage(
                "对不起，您不是我的主人！wo暂时还无法和您说话");

        // TODO: 处理消息逻辑
    }
//    被动收到消息：MessageEvent
//群消息：GroupMessageEvent
//好友消息：FriendMessageEvent
//群临时会话消息：GroupTempMessageEvent
//陌生人消息：StrangerMessageEvent
//其他客户端消息：OtherClientMessageEvent
}
