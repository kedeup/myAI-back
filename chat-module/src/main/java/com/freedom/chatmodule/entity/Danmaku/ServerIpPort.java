package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author kede·W  on  2023/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerIpPort {
    private String Host;
    private Integer port;
}
