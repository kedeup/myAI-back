package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DmMsg<T> {
    int code;
    private T data;
}
