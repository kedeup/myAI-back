package com.freedom.chatmodule.tools.danmaku;

import com.freedom.chatmodule.entity.Danmaku.DataPacket;


import java.nio.ByteBuffer;

/**
 * @Author kede·W  on  2023/3/26
 */
public class BinaryTools {

    private final static int PACKET_LEN_OFFSET = 0;
    private final static int HEADER_LEN_OFFSET = 4;
    private final static int PROTOCOL_VERSION_OFFSET = 6;
    private final static int OPERATION_TYPE_OFFSET = 8;
    private final static int SEQUENCE_ID_OFFSET = 12;
    private final static int CONTENT_BODY_OFFSET = 16;

    private ByteBuffer buffer;

    public BinaryTools(ByteBuffer buffer){
        this.buffer = buffer;
    }

    public DataPacket getDataPacket(){
        DataPacket dataPacket = new DataPacket();
        dataPacket.setPacketLength(getPacketLength());
        dataPacket.setHeaderLength(getHeaderLength());
        dataPacket.setProtocolVersion(getProtocolVersion());
        dataPacket.setOperationType(getOperationType());
        dataPacket.setSequenceId(getSequenceId());
        dataPacket.setContent(getContentBody());
        return dataPacket;
    }

    public void setBufferPos(int offset){
        buffer.position(offset);
    }

    public int getPacketLength(){
        setBufferPos(PACKET_LEN_OFFSET);
        return buffer.getInt();
    }

    public int getHeaderLength (){
        setBufferPos(HEADER_LEN_OFFSET);
        return buffer.getShort();
    }

    public int getProtocolVersion(){
        setBufferPos(PROTOCOL_VERSION_OFFSET);
        return buffer.getShort();
    }

    public int getOperationType(){
        setBufferPos(OPERATION_TYPE_OFFSET);
        return buffer.getInt();
    }

    public int getSequenceId(){
        setBufferPos(SEQUENCE_ID_OFFSET);
        return buffer.getInt();
    }

    public byte[] getContentBody(){
        setBufferPos(CONTENT_BODY_OFFSET);
        int content_length = buffer.limit() - CONTENT_BODY_OFFSET;
//        int content_length = getPacketLength();
        byte[] content = new byte[content_length];
        buffer.get(content);
        return content;
    }

}
