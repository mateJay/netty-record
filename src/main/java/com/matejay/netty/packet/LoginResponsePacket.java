package com.matejay.netty.packet;

import lombok.Data;

import static com.matejay.netty.util.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
