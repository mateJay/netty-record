package com.matejay.netty.server.handler;

import com.matejay.netty.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.nio.charset.Charset;
import java.util.Date;

public class OutBoundHandlerC extends ChannelOutboundHandlerAdapter {
    /**
     * 在服务端写出数据到客户端前调用
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(TimeUtil.format(new Date()) + "outC:" + byteBuf.toString(Charset.forName("utf-8")));
        super.write(ctx, msg, promise);
    }
}
