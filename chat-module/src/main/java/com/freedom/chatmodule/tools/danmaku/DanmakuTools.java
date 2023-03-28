package com.freedom.chatmodule.tools.danmaku;

import com.freedom.chatmodule.entity.Danmaku.HostServer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author kede·W  on  2023/3/25
 */
@Slf4j
public class DanmakuTools {

    public static String GetWssUrl(List<HostServer> hostServers) {
        final StringBuilder stringBuilder = new StringBuilder();
        String wssUrl= null;
        if(hostServers.size()>0) {
            HostServer hostServer = hostServers.get((int)Math.random()*hostServers.size());
            stringBuilder.append("wss://");
            stringBuilder.append(hostServer.getHost());
//			stringBuilder.append(":");
//			stringBuilder.append(hostServer.getWss_port());
            stringBuilder.append("/sub");
            wssUrl = stringBuilder.toString();
            log.info("获取破站websocket地址：{}", wssUrl);
        }
        return wssUrl;
    }

}
