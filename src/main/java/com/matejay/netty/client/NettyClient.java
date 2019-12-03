package com.matejay.netty.client;

import com.matejay.netty.client.handler.ClientHandler;
import com.matejay.netty.client.handler.FirstClientHandler;
import com.matejay.netty.client.handler.LoginResponseHandler;
import com.matejay.netty.client.handler.MessageResponseHandler;
import com.matejay.netty.codec.PacketDecoder;
import com.matejay.netty.codec.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @author mate_j
 */
public class NettyClient {

    // 最大重连次数
    final static int MAX_RETRY = 3;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        // 解码
                        ch.pipeline().addLast(new PacketDecoder());
                        // 登录并获得登录结果响应
                        ch.pipeline().addLast(new LoginResponseHandler());
                        // 获取服务器返回信息
                        ch.pipeline().addLast(new MessageResponseHandler());
                        // 编码
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });

        // 4.建立连接
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    public static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry <= 0) {
                System.out.println("重连次数已经用完");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
