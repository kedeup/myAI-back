package com.freedom.chatmodule.wx;

import com.freedom.chatmodule.tools.danmaku.TimeTools;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author kede·W  on  2023/4/16
 */
@Data
@Component
public class WxProperties {

    @Value("${wechat.dyh.appid}")
    private String appid;
    @Value("${wechat.dyh.appsecret}")
    private String appsecret;

    private String accesstoken;
    private int expireValue = 7200;
    private long expiredDDL = TimeTools.getCurrentTimestamp();
}
