package com.freedom.chatmodule.config.qqbot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author kede·W  on  2023/4/3
 */
@Data
@Configuration
@ConfigurationProperties(prefix="qqbotlist")
@EnableConfigurationProperties(BotListProperties.class)
public class BotListProperties {

    private List<BotConfig> config;

    @Data
    public static class BotConfig {
        private long id;
        private String password;
        private long admin_id;
    }
}
