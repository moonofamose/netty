package com.amose.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] result = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(result);
            System.out.println(new String(result));

            ByteBuf buffer = ctx.alloc().buffer(1024);
            ctx.writeAndFlush(buffer.writeBytes("hello server".getBytes()));
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
