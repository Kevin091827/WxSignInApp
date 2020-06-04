package com.example.signin.util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import com.alibaba.fastjson.JSONObject;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Auther: ARong
 * @Date: 2019/3/2 13:28
 * @Description: wx获取手机号解密工具类
 */
public class AES {

    // 算法名
    public static final String KEY_NAME = "AES";
    // 加解密算法/模式/填充方式
    // ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个iv
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 初始化密钥
     */
    public static void init() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator.getInstance(KEY_NAME).init(128);
    }

    /**
     * 生成iv
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_NAME);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 生成解密
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
            throws Exception {
        Key key = new SecretKeySpec(keyBytes, KEY_NAME);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);

    }

    public static String decryptNew(String encryptedData, String sessionKey, String iv) {
        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
            //System.out.println(ipstr);
        }
        return ip;
    }

    public static void main(String[] args) {
        try {
            System.out.println(getV4IP());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
