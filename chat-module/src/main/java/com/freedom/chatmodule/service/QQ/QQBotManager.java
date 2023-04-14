package com.freedom.chatmodule.service.QQ;

import com.freedom.chatmodule.config.qqbot.BotListProperties;
import com.freedom.chatmodule.entity.QQ.QQBot;
import com.freedom.chatmodule.entity.QQ.QQBotFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kede·W  on  2023/4/3
 */

//
@Service
public class QQBotManager {

    private List<QQBot> qqBotList = new ArrayList<>();

    @Autowired
    private BotListProperties botListProperties;
    @Autowired
    private QQBotFactory qqBotFactory;

    @PostConstruct
    public void init(){
        botListProperties.getConfig().forEach(botConfig -> {
            QQBot qqBot = qqBotFactory.createQQBot(botConfig.getId(),
                    botConfig.getPassword());
            qqBotList.add(qqBot);
        });
    }

    public void startAllBots() {
        // 启动所有机器人
        for (QQBot bot : qqBotList) {
            bot.start();
        }
    }
}
