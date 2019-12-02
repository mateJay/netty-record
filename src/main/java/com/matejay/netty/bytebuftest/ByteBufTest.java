package com.matejay.netty.bytebuftest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author mate_j
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("初始化（9， 100）", byteBuf);

        // 写入四个byte
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", byteBuf);

        // 写入一个int类型，占4个字节
        byteBuf.writeInt(12);
        print("writeInt(12)", byteBuf);

        // 写入一个byte，总共9个字节
        byteBuf.writeByte(5);
        print("writeByte(5)", byteBuf);

        // 再写入一个byte，自动扩容
        byteBuf.writeByte(6);
        print("writeByte(6)", byteBuf);

        // get方法不影响读指针
        System.out.println("getByte(7) return: " + byteBuf.getByte(7));
        // 一个short占2个字节，每个字节占8为，取0，从第0（下标）个开始取，取两个字节，前16位，这样就是 0000000100000010，计算出来256 + 2 = 258
        System.out.println("getShort(0) return: " + byteBuf.getShort(0));
        // 从1（下标）开始取，取两个字节，取第17到24位， 0000001000000011， 512 + 2 + 1 = 515
        System.out.println("getShort(1) return: " + byteBuf.getShort(1));
        System.out.println("getShort(2) return: " + byteBuf.getShort(2));
        System.out.println("getInt(4) return: " + byteBuf.getInt(4));

        print("get()", byteBuf);

        // set不会影响读写指针
        byteBuf.setBytes(7, new byte[]{21});
        print("set()", byteBuf);
        System.out.println("getByte(7) return: " + byteBuf.getByte(7));

        // read会影响读指针
        byte[] dst = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(dst);
        print("readBytes(" + dst.length + ")", byteBuf);


    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
