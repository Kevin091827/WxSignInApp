package com.example.signin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.signin.config.WxConfig;
import com.example.signin.entity.User;
import com.example.signin.mapper.UserMapper;
import com.example.signin.service.WxService;
import com.example.signin.util.AES;
import com.example.signin.util.AjaxResult;
import com.example.signin.util.HttpClientUtis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:WxServiceImpl
 * @Description: TODO
 */
@Service
@Transactional
@Slf4j
public class WxServiceImpl implements WxService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public AjaxResult wxLogin(String code, String encryptedData, String iv) {
        //封装请求参数
        Map<String,String> params = new HashMap<>();
        params.put("appid", WxConfig.APPID);
        params.put("secret",WxConfig.SECRET);
        params.put("js_code",code);
        params.put("grant_type",WxConfig.GRANT_TYPE);
        //请求微信登录授权
        String jsonString = HttpClientUtis.doGet(params,WxConfig.WXLOGIN_URL);
        log.info("--------------->>>" + jsonString);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String openId = jsonObject.getString("openid");
        User user = new User();
        user.setGmtCreate(new Date());
        user.setGmtModify(new Date());
        user.setOpenId(openId);
        int i = userMapper.insertUser(user);
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("token: " + token,openId);
        Map map = new HashMap();
        map.put("token",token);
        map.put("openId",openId);
        return new AjaxResult().ok(map);
    }
}
