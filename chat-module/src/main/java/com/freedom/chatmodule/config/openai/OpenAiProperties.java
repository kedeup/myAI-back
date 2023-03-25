package com.freedom.chatmodule.config.openai;

/**
 * @Author kede·W  on  2023/3/21
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {
    String apikey;
    String baseUrl;
}
