package com.freedom.chatmodule.config.qqbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author kede·W  on  2023/4/3
 */
@Configuration
public class BotConfigMap {


    private BotListProperties botListProperties;

    @Autowired
    public BotConfigMap(BotListProperties botListProperties){
        this.botListProperties = botListProperties;
    }



    @Bean(name = "BOTCONFIGMap")
    public Map<Long, BotListProperties.BotConfig> initBotConfigMap(){
        Map<Long, BotListProperties.BotConfig> map = new HashMap<>();
        for (BotListProperties.BotConfig config : botListProperties.getConfig()) {
            map.put(config.getId(), config);
        }
        return map;
    }
}
