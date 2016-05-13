package com.mpms.utils.encrypt;

import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncrypt {
    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc
     *            要加密的字符串
     * @param encName
     *            加密类型
     * @return
     */
    public static byte[] Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            return  md.digest(); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static byte[] Encrypt(byte[] bytes, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = bytes;
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            return  md.digest(); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    public static void main(String args[]) throws  Exception{
     byte[] s=StringEncrypt.Encrypt("汪业培", "");
     System.out.println(StringEncrypt.encode(s));
    }
}