package com.matejay.netty.serialize;

import com.alibaba.fastjson.JSON;
import com.matejay.netty.util.SerializerAlgorithm;

/**
 * 序列化集体实现方法
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
