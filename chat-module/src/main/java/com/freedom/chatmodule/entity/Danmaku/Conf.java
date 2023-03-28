package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author kede·W  on  2023/3/25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conf {
    private String group;
    private Short business_id;
    private Short refresh_row_factor;
    private Integer refresh_rate;
    private Integer max_delay;
    private String token;
    private List<HostServer> host_list;
}
