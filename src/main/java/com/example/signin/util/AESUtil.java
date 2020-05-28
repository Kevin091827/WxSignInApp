package com.example.signin.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @Auther: ARong
 * @Date: 19-5-11 下午2:00
 * @Description:
 **/
public class AESUtil {
    /**
     * 默认密钥，可以自定义
     */
    private static final String KEY = "asdfghjkl";
    /**
     * 向量密钥，可以自定义，必须128位(16字节)
     */
    private static final String PARAM_KEY = "1234561234567890";
    /**
     * 转型形式
     */
    private static final String CIPHER_KEY = "AES/CBC/PKCS5Padding";
    /**
     * 编码
     */
    private static final String CHARSET = "utf-8";
    /**
     * 算法名
     */
    public static final String MODE_AES = "AES";

    /**
     * 加密
     *
     * @param sSrc 内容
     * @return
     */
    public static String encrypt(String sSrc) {
        return encrypt(sSrc, KEY);
    }

    /**
     * 加密
     *
     * @param sSrc 内容
     * @param sKey 密钥
     * @return
     */
    public static String encrypt(String sSrc, String sKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);//创建加密Cipher类实例
            KeyGenerator kgen = KeyGenerator.getInstance(MODE_AES);//AES加密密钥生成器
            //linux环境下需要指定以下两行
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(sKey.getBytes());
            kgen.init(128, secureRandom);//生成密钥128位(16字节)
            SecretKey secretKey = kgen.generateKey();//生成密钥
            IvParameterSpec iv = new IvParameterSpec(PARAM_KEY.getBytes(CHARSET));//向量iv
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);//加密初始化
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARSET));//完成加密操作
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param sSrc 内容
     * @return
     */
    public static String decrypt(String sSrc) {
        return decrypt(sSrc, KEY);
    }

    /**
     * 解密
     *
     * @param sSrc 内容
     * @param sKey 密钥
     * @return
     */
    public static String decrypt(String sSrc, String sKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_KEY);//创建加密Cipher类实例
            KeyGenerator kgen = KeyGenerator.getInstance(MODE_AES);//AES加密密钥生成器
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(sKey.getBytes());
            kgen.init(128, secureRandom);//生成密钥128位(16字节)
            SecretKey secretKey = kgen.generateKey();//生成密钥
            IvParameterSpec iv = new IvParameterSpec(PARAM_KEY.getBytes(CHARSET));//向量iv
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);//解密初始化
            byte[] original = cipher.doFinal(Base64.decodeBase64(sSrc));//完成解密操作
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
