package com.freedom.chatmodule.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author kede·W  on  2023/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletionVO {
    private String id;
    private String object;
    private long created;
    private List<Choice> choices;
    private Usage usage;

}
