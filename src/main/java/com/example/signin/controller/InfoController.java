package com.example.signin.controller;

import com.example.signin.service.InfoService;
import com.example.signin.util.AjaxResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:InfoController
 * @Description: TODO
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private InfoService infoService;

    @PostMapping("/improveInfo")
    public AjaxResult improveInfo(@RequestParam("type")String type,
                                  @RequestParam("school")String school,
                                  @RequestParam("name")String name,
                                  @RequestParam("collage")String collage,
                                  @RequestParam("accountId")String accountId,
                                  @RequestParam("phone")String phone,
                                  @RequestParam("email")String email,
                                  @RequestParam("openId")String openId,
                                  @RequestParam(value = "grade",required = false)String grade,
                                  @RequestParam(value = "stuClass",required = false)String stuClass,
                                  @RequestParam(value = "major",required = false)String major){
        if ("teacher".equals(type)) {
            return infoService.improveTeaInfo(type,school,name,collage,accountId,phone,email,openId);
        }else if("student".equals(type)){
            return infoService.improveStuInfo(type,school,name,collage,accountId,phone,email,grade,stuClass,openId,major);
        }else {
            return new AjaxResult().error("用户类型出错");
        }
    }

    @RequestMapping("/selectInfo")
    public AjaxResult selectInfo(@RequestParam("accountId")String accountId,
                                 @RequestParam("type")String type,
                                 @RequestParam("school")String school){
        if ("teacher".equals(type)) {
            return infoService.selectTeaInfo(accountId,school);
        }else if("student".equals(type)){
            return infoService.selectStuInfo(accountId,school);
        }else {
            return new AjaxResult().error("用户类型出错");
        }
    }

    @RequestMapping("/isNeedImprove")
    public AjaxResult isNeedImprove(@RequestParam("openId")String openId,
                                    @RequestParam("type")String type){
        return infoService.isNeedImprove(openId,type);
    }

}
