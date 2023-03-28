package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {
    private long uid = 399231588;
    private long roomid;
    private int protover = 3;
    private String platform = "web";
    private int type = 2;
    private String key;


    public AuthData(long uid, long roomid, String key){
        this.uid = uid;
        this.roomid = roomid;
        this.key = key;
    }
}
