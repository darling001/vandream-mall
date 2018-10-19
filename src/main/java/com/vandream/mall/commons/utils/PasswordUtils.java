package com.vandream.mall.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Li Jie
 */
public class PasswordUtils {
    public static final byte[] SALT_VANDREAM = "vandream".getBytes();

    /**
     * 获取十六进制字符串形式的MD5摘要
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5Hex(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bs = md5.digest(password.getBytes());
        return new String(new Hex().encode(bs));
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     *
     * @param password
     * @param saltBytes     盐值
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5Hex(String password, byte[] saltBytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] pwdBytes = md5.digest(password.getBytes());
        byte[] bs = md5.digest(ArrayUtils.addAll(pwdBytes, saltBytes));
        return new String(new Hex().encode(bs));
    }

    public static void main(String[] args) {
        try {
            String s = MD5Hex("a546314583", SALT_VANDREAM);
            System.out.println(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
