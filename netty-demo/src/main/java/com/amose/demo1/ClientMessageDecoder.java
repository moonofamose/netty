package com.amose.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;

/**
 * 消息解码器
 */
public class ClientMessageDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readCount = byteBuf.readableBytes();
        if(readCount<1){
            return;
        }
        byte[] resultByte = new byte[readCount];
        byteBuf.readBytes(resultByte);
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.setCreateTime(new Date());
        clientMessage.setMessage(new String(resultByte));
        list.add(clientMessage);
    }
}
