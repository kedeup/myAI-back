package com.freedom.chatmodule.dto;

import com.freedom.chatmodule.vo.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author kede·W  on  2023/3/21
 */
@Data
@AllArgsConstructor
public class UserMessageDTO {
    private String model;
    private List<Message> messages;

    private Integer max_tokens;
    private Double temperature;

}
