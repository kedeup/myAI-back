package com.freedom.chatmodule.entity.QQ;


import com.freedom.chatmodule.service.QQ.CommEventListener;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author kede·W  on  2023/4/1
 */
public class QQBot {

    private Bot bot;


    public QQBot(long qq, String password, BotConfiguration botCommonConfig){
        this.bot = BotFactory.INSTANCE.newBot(qq, password, botCommonConfig);
    }

    public void start() {

        bot.login();
        bot.getEventChannel().registerListenerHost(new CommEventListener(bot));
    }
}
