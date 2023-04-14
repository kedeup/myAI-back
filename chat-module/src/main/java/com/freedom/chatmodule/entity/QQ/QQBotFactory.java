package com.freedom.chatmodule.entity.QQ;

import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author kede·W  on  2023/4/3
 */

@Component
public class QQBotFactory {

    @Autowired
    private BotConfiguration botCommonConfig;

    public QQBot createQQBot(long qq, String password) {
        return new QQBot(qq, password, botCommonConfig);
    }
}