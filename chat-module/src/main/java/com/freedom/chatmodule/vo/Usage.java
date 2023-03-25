package com.freedom.chatmodule.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

//    public Usage(int prompt_tokens, int completion_tokens, int total_tokens) {
//        this.prompt_tokens = prompt_tokens;
//        this.completion_tokens = completion_tokens;
//        this.total_tokens = total_tokens;
//    }

}
