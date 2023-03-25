package com.freedom.chatmodule.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private int index;
    private Message message;
    private String finish_reason;
}
