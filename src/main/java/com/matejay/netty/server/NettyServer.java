package com.matejay.netty.server;

import com.matejay.netty.codec.PacketDecoder;
import com.matejay.netty.codec.PacketEncoder;
import com.matejay.netty.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author mate_j
 */
public class NettyServer {
    public static final Integer PORT = 8000;

    public static void main(String[] args) {
        // 接受新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 具体处理每一条连接的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 线程模型
                .group(bossGroup, workerGroup)
                // io模型
                .channel(NioServerSocketChannel.class)
                // io业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // 解码
                        ch.pipeline().addLast(new PacketDecoder());
                        // 判断登录
                        ch.pipeline().addLast(new LoginRequestHandler());
                        // 服务器接收消息并且回复
                        ch.pipeline().addLast(new MessageRequestHandler());
                        // 编码
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
