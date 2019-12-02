package com.matejay.netty.util;

/**
 * 指令
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte LOGOUT_RESPONSE = 6;

}
