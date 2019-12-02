package com.matejay.netty.serialize;

/**
 * 序列化需要做的事情
 */
public interface Serializer {
    /**
     * json 序列化
     */
    byte JSON_SERIALIZER = 1;

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 默认序列化方法
     */
    Serializer DEFAULT = new JsonSerializer();
}
