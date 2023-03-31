package com.freedom.chatmodule.common.enums.Danmaku;

/**
 * @Author kede·W  on  2023/3/27
 */
public enum DMMessageType {
    COMBO_SEND(1,"COMBO_SEND","投喂"),
    DANMU_MSG(2,"DANMU_MSG","弹幕信息"),
    LIKE_INFO_V3_CLICK(3,"LIKE_INFO_V3_CLICK","投喂");

    private final int code;
    private final String des;
    private final String cmd;

    DMMessageType(int code,String cmd, String des) {
        this.code = code;
        this.cmd = cmd;
        this.des = des;
    }

    public String getCmd() {
        return cmd;
    }
    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
