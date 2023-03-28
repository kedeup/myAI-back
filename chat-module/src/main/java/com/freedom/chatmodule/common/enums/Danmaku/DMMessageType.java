package com.freedom.chatmodule.common.enums.Danmaku;

/**
 * @Author kede·W  on  2023/3/27
 */
public enum DMMessageType {
    COMBO_SEND("COMBO_SEND","投喂"),
    DANMU_MSG("DANMU_MSG","弹幕信息"),
    LIKE_INFO_V3_CLICK("LIKE_INFO_V3_CLICK","投喂");


    private final String des;
    private final String cmd;

    DMMessageType(String cmd, String des) {
        this.cmd = cmd;
        this.des = des;
    }

    public String getCmd() {
        return cmd;
    }

    public String getDes() {
        return des;
    }
}
