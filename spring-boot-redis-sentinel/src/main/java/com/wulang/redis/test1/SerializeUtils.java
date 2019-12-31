package com.wulang.redis.test1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wulang
 * @create 2019/11/27/20:40
 */
public class SerializeUtils {
    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    /**
     * 序列化
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static byte[] serialize(Object object) throws Exception {
        if(object == null) return null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error("序列化"+e);
            throw e;
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object unSerialize(byte[] bytes) throws Exception {
        if(bytes == null) return null;
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("反序列化"+e);
            throw e;
        }
    }
}
