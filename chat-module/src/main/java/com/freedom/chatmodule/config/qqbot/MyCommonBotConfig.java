package com.freedom.chatmodule.config.qqbot;

import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import net.mamoe.mirai.utils.LoginSolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @Author kede·W  on  2023/4/2
 */
@Configuration
public class MyCommonBotConfig{

    @Bean
    public BotConfiguration commonBotConfig(){
        BotConfiguration botCommonConfig = new BotConfiguration(){{
//            https://blog.ryoii.cn/mirai-devicejs-generator/
        }};
        botCommonConfig.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.REGISTER);
        botCommonConfig.setProtocol(BotConfiguration.MiraiProtocol.MACOS);
        botCommonConfig.setWorkingDir(new File("G:/qqbotyunxing/mirai"));
        botCommonConfig.setCacheDir(new File("G:/qqbotyunxing/cache"));
//        botCommonConfig.setDeviceInfo(bot -> new DeviceInfo());
        botCommonConfig.redirectNetworkLogToDirectory(new File("G:/qqbotyunxing/NetworkLog"));
        botCommonConfig.fileBasedDeviceInfo("G:/qqbotyunxing/mirai" +
                "/myDeviceInfo.json");

        botCommonConfig.enableContactCache();

        return botCommonConfig;
    }
}
