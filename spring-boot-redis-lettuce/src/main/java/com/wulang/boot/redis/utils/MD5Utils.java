package com.wulang.boot.redis.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
    private static ThreadLocal<MessageDigest> messageDigestHolder = new ThreadLocal();
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public MD5Utils() {
    }


    public static String getMD5Format(String data) {
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data.getBytes());
            byte[] b = message.digest();
            StringBuilder digestHexStr = new StringBuilder();

            for(int i = 0; i < 16; ++i) {
                digestHexStr.append(byteHEX(b[i]));
            }

            return digestHexStr.toString();
        } catch (Exception var5) {
            throw new RuntimeException("MD5格式化时发生异常[{}]: {}", var5);
        }
    }

    public static String getMD5Format(byte[] data) {
        try {
            MessageDigest message = messageDigestHolder.get();
            if (message == null) {
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }

            message.update(data);
            byte[] b = message.digest();
            StringBuilder digestHexStr = new StringBuilder();

            for(int i = 0; i < 16; ++i) {
                digestHexStr.append(byteHEX(b[i]));
            }

            return digestHexStr.toString();
        } catch (Exception var5) {
            return null;
        }
    }

    private static String byteHEX(byte ib) {
        char[] ob = new char[]{HEX_DIGITS[ib >>> 4 & 15], HEX_DIGITS[ib & 15]};
        return new String(ob);
    }

    static {
        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            messageDigestHolder.set(message);
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException("初始化java.security.MessageDigest失败:" , var1);
        }
    }
}
