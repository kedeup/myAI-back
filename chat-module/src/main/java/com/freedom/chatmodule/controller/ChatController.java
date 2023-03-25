package com.freedom.chatmodule.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.freedom.chatmodule.common.HttpService;
import com.freedom.chatmodule.common.OpenaiRequestUtil;
import com.freedom.chatmodule.dto.UserMessageDTO;
import com.freedom.chatmodule.vo.CompletionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @Author kede·W  on  2023/3/21
 */
@RestController
public class ChatController {
    @Autowired
    OpenaiRequestUtil openaiRequestUtil;

    @Autowired
    HttpService httpService;

//    @RequestMapping(value = "/completions", method = RequestMethod.POST)
//    public CompletionVO postCompletion(@RequestBody UserMessageDTO userMessage) throws IOException {
//        System.out.println(userMessage);
//        //ResponseBody response = httpService.post("/v1/chat/completions",userMessage);
//        CompletionVO completion = openaiRequestUtil.post("/v1/chat/completions", userMessage, CompletionVO.class);
//        System.out.println(completion);
//        return completion;
//    }

    @RequestMapping(value = "/completions", method = RequestMethod.POST)
    public CompletionVO postCompletion(@RequestBody Map<String, Object> requestBody ) throws Exception {
        System.out.println(requestBody);
        String apikey = (String) requestBody.get("api");
        Object userMessage = requestBody.get("params");
        System.out.println("解密前");
        System.out.println(apikey);
        //ResponseBody response = httpService.post("/v1/chat/completions",userMessage);
        CompletionVO completion = openaiRequestUtil.post("/v1/chat" +
                "/completions", apikey, userMessage, CompletionVO.class);
        System.out.println(completion);
        return completion;
    }
}
