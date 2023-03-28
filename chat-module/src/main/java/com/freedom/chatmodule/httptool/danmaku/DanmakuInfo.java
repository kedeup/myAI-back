package com.freedom.chatmodule.httptool.danmaku;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.freedom.chatmodule.common.OpenaiRequestUtil;
import com.freedom.chatmodule.entity.Danmaku.Conf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author kede·W  on  2023/3/25
 */
@Slf4j
@Service
public class DanmakuInfo {

    @Autowired
    private OpenaiRequestUtil requestUtil;

    public Conf httpGetConf(long roomid){
//        String urlString  = "https://api.live.bilibili" +
//                ".com/room/v1/Danmu/getConf?room_id="+roomid+"&platform=pc" +
//                "&player=web";
        String urlString  ="https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?type=0&id="+roomid;
        String confStr = requestUtil.get(urlString);
        JSONObject jsonObject = JSON.parseObject(confStr);
        Conf conf = jsonObject.getObject("data", Conf.class);
        log.info("配合=====>{}",conf.toString());
        return conf;
    }
}
