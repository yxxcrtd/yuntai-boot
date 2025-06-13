package com.joyintech.yuntai.module.system.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import cn.hutool.crypto.SmUtil;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.SYS_ERROR;

public class PasswordUtil {

    /** 加密算法 **/
    // PBEWithMD5AndDES PBEWithMD5AndTripleDES PBEWithSHAAndDESEDE PBEWithSHA1AndRC2_40 PBKDF2WithHMACSHA1
    private static final String ALGORITHM = "PBEWithMD5AndDES";

    /** 迭代次数 **/
    private static final int ITERATION_COUNT = 1000;

    /**
     * 加密明文字符串
     *
     * @param type 加密方式
     * @param account 用户账号
     * @param password 生成密钥时所使用的密码
     * @param salt 盐值
     * @return 加密后的密文字符串
     */
    public static String encrypt(String type, String account, String password, String salt) {
        if ("SHA-256".equalsIgnoreCase(type)) {
            return encryptSha256(account, password, salt);
        } else if ("SM3".equalsIgnoreCase(type)) {
            return encryptSm3(account, password, salt);
        } else {
            return encryptDefault(account, password, salt);
        }
    }


    public static String encryptSm3(String account, String password, String salt) {
        return SmUtil.sm3(account + ':' + password + ':' + salt);
    }

    public static String encryptSha256(String account, String password, String salt) {
        return sha256Digest(account + ':' + password + ':' + salt);
    }

    private static String sha256Digest(String contents) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(contents.getBytes(StandardCharsets.UTF_8));
            return HexTools.toString(messageDigest.digest()).toLowerCase();
        } catch (Exception e) {
            throw exception(SYS_ERROR);
        }
    }

    public static String encryptDefault(String account, String password, String salt) {
        Key key = getPBEKey(password);
        byte[] encipheredData ;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATION_COUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            encipheredData = cipher.doFinal(account.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw exception(SYS_ERROR);
        }
        return HexTools.toString(encipheredData).toLowerCase();
    }

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password 生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * */
    private static Key getPBEKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            throw exception(SYS_ERROR);
        }
        return secretKey;
    }
}