package com.amose.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerDemo1 {

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        // 1.线程池
        // 2. boss线程接受tcp或udp请求，并注册对应的事件
        // 3. 接受tcp或udp请求，进行业务包拆分（业务层协议）
        // 4. 进行业务处理
        // 5. 进行业务结果返回
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class) // boosGroup使用的channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception { // workGroup 使用childHandler进行处理
                            // parentGroup在执行完成连接建立后，会将channel注册到childGroup中，这时会对channel进行初始化
                            socketChannel.pipeline().addFirst(new ClientMessageDecoder(),new ClientMessageHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture bindFuture = bootstrap.bind(port).sync();
            bindFuture.channel().closeFuture().sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
