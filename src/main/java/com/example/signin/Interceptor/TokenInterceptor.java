package com.example.signin.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TokenInterceptor
 * @Description: TODO
 */
@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println("token ----------->" + token);
        if(token.equals("testToken")){
            return true;
        }
        Object obj = redisTemplate.opsForValue().get("token: " + token);
        System.out.println("obj ------------->" + obj);
        if(obj != null){
            return true;
        }
        return false;
    }
}
