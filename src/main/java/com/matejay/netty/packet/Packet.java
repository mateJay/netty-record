package com.matejay.netty.packet;

import lombok.Data;

/**
 * java对象抽象类
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
   public abstract Byte getCommand();
}
