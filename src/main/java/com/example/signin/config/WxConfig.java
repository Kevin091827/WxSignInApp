package com.example.signin.config;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:WxConfig
 * @Description: TODO
 */
public interface WxConfig {

    String WXLOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    String APPID = "wx461cd161bd35aaf7";

    String SECRET = "a830b548146cbd62eb0b73a2897c39b8";

    String GRANT_TYPE = "authorization_code";
}
