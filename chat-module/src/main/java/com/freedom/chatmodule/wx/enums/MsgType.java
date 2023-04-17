package com.freedom.chatmodule.wx.enums;

import lombok.Data;

/**
 * @Author kede·W  on  2023/4/16
 */
public enum MsgType {

    event("event", "关注或者订阅事件"),

    image("image", "图片消息"),
    link("link", "链接消息"),
    location("location", "地理位置消息"),
    music("music", "音乐消息"),
    news("news", "图文消息"),
    shortvideo("shortvideo", "小视频消息"),
    text("text", "文本消息"),
    video("video", "视频消息"),
    voice("voice", "语音消息");

    MsgType(String msgType, String msgTypeDesc) {
        this.msgType = msgType;
        this.msgTypeDesc = msgTypeDesc;
    }

    private final String msgType;
    private final String msgTypeDesc;

    public String getmsgType() {
        return msgType;
    }
    public String getTypeDesc() {
        return msgTypeDesc;
    }
    /**
     * 获取对应的消息类型
     * @param msgType
     * @return
     */
    public static MsgType getMsgType(String msgType) {
        switch (msgType) {
            case "text":
                return text;
            case "image":
                return image;
            case "voice":
                return voice;
            case "video":
                return video;
            case "shortvideo":
                return shortvideo;
            case "location":
                return location;
            case "link":
                return link;
            case "music":
                return music;
            case "news":
                return news;
            case "event":
                return event;
            default:
                return null;
        }
    }
}
