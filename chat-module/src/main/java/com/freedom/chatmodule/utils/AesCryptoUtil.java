package com.freedom.chatmodule.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author kede·W  on  2023/3/23
 */

@Component
public class AesCryptoUtil {

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    private static final String CHARSET = "UTF-8";

    /**
     * AES解密操作
     *
     * @param content  待解密内容（Base64编码）
     * @param password 解密密码   对应前端 private CryptoSecret = '__CRYPTO_SECRET__'
     * @param iv       偏移量
     * @return 解密后的字符串
     */
    @Value("${AesCrypto.iv}")
    private String IV;
    @Value("${AesCrypto.mode}")
    String MODE;
    @Value("${AesCrypto.padding}")
    String PADDING;
    @Value("${AesCrypto.password}")
    String password;

    public String decrypt(String content) throws Exception {



        // 将加密后的字符串转换为字节数组
        byte[] encryptedBytes = Base64.decodeBase64(content);
        // 构造密钥
        SecretKeySpec key = new SecretKeySpec(password.getBytes(CHARSET), "AES");
        // 构造偏移量
//        Random rand = new SecureRandom();
//        byte[] bytes = new byte[16];
//        rand.nextBytes(bytes);
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(bytes);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(CHARSET));
        // 获取 Cipher 对象
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        // 初始化 Cipher 对象
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        // 执行解密操作
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        // 将解密后的字节数组转换为字符串并返回
        return new String(decryptedBytes, CHARSET).trim();
    }


    public String encrypt(String content) throws Exception{
        try {
            //"算法/模式/补码方式"NoPadding PkcsPadding
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = content.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
