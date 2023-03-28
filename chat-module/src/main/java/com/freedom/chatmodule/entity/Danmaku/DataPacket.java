package com.freedom.chatmodule.entity.Danmaku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author kede·W  on  2023/3/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPacket {
    private int packetLength;
    private int headerLength;
    private int protocolVersion;
    private int operationType;
    private int sequenceId;
    private byte[] content;
}
