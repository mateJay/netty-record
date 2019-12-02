package com.matejay.netty.packet;

import com.matejay.netty.util.Command;
import lombok.Data;


@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
