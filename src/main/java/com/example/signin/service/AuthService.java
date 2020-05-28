package com.example.signin.service;

import com.example.signin.util.AjaxResult;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AuthService
 * @Description: TODO
 */
public interface AuthService {
    
    AjaxResult auth(String school, String type, String accountId, String pwd,String openId);

    AjaxResult isFirstLogin(String openId);
}
