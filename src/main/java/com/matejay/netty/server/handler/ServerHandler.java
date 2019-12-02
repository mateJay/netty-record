package com.matejay.netty.server.handler;

import com.matejay.netty.packet.LoginRequestPacket;
import com.matejay.netty.packet.LoginResponsePacket;
import com.matejay.netty.packet.Packet;
import com.matejay.netty.packet.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            loginResponsePacket.setUserId(loginRequestPacket.getUserId());
            loginResponsePacket.setUserName(loginRequestPacket.getUsername());

            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                System.out.println("账号密码校验成功");
                loginResponsePacket.setSuccess(true);
            } else {
                // 校验失败
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
            }

            // 编码
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);

            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

}
