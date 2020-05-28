package com.example.signin.controller;

import com.example.signin.service.AuthService;
import com.example.signin.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AuthController
 * @Description: TODO
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/auth")
    public AjaxResult authentication(@RequestParam("school")String school,
                                     @RequestParam("type")String type,
                                     @RequestParam("accountId")String accountId,
                                     @RequestParam("pwd")String pwd,
                                     @RequestParam("openId")String openId){
        if("teacher".equals(type) || "student".equals(type)) {
            return authService.auth(school, type, accountId, pwd,openId);
        }else{
            return new AjaxResult().error("type类型出错");
        }
    }

    @RequestMapping("/isFirstLogin")
    public AjaxResult isFirstLogin(@RequestParam("openId")String openId){
        return authService.isFirstLogin(openId);
    }

}
