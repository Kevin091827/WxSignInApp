package com.example.signin.controller;

import com.example.signin.entity.UserSignIn;
import com.example.signin.service.SignInService;
import com.example.signin.util.AjaxResult;
import com.example.signin.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SignInController
 * @Description: TODO
 */
@RequestMapping("/signIn")
@Controller
@ResponseBody
@CrossOrigin
public class SignInController {

    @Autowired
    private SignInService signInService;

    @RequestMapping("/input")
    public AjaxResult inputSignCourse(@RequestParam("teaId")String teaId,
                                      @RequestParam("courseName")String courseName,
                                      @RequestParam("courseAddr")String courseAddr,
                                      @RequestParam("courseTime")String courseTime,
                                      @RequestParam("courseStanza")String courseStanza){

        return signInService.inputSignCourse(teaId,courseAddr,courseName,courseTime,courseStanza);
    }

    @RequestMapping("/requestSignIn")
    public AjaxResult requestSignIn(@RequestParam("id")long id,
                                    @RequestParam("signNum")String signNum,
                                    @RequestParam("openId")String openId){
        return signInService.requestSignIn(id,signNum,openId);
    }

    @RequestMapping("/responseSignIn")
    public AjaxResult responseSignIn(@RequestParam("stuId")String stuId,
                                     @RequestParam("signNum")String signNum,
                                     @RequestParam("gpsInfo")String gpsInfo,
                                     @RequestParam("openId")String openId){
        return signInService.responseSignIn(stuId,signNum,gpsInfo,openId);
    }

    @RequestMapping("/signInNow")
    public AjaxResult signInNow(@RequestParam("signNum")String signNum,@RequestParam("id")long id){
        return signInService.signInNow(signNum,id);
    }

    @RequestMapping("/select")
    public AjaxResult selectSignRecord(@RequestParam("type")String type,
                                       @RequestParam("openId")String openid){
        return signInService.selectSignRecord(type,openid);
    }

    @RequestMapping("/delete")
    public AjaxResult deleteRecordById(@RequestParam("id")long id){
        return signInService.deleteRecordById(id);
    }

    @RequestMapping("/export")
    public AjaxResult exportExcel(HttpServletResponse response,@RequestParam("teaId")String teaId){
        return signInService.exportExcel(response,teaId);
    }
}
