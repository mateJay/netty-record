package com.matejay.netty.server.handler;

import com.matejay.netty.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(TimeUtil.format(new Date()) + "inA读客户端数据:" + byteBuf.toString(Charset.forName("utf-8")));
        super.channelRead(ctx, msg);
    }

    // 客户端连接的时候写数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(TimeUtil.format(new Date()) + "客户端连接inA的时候服务端发送信息");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
        super.channelActive(ctx);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "A".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
