package com.example.signin.controller;

import com.example.signin.service.CourseService;
import com.example.signin.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:CourseController
 * @Description: TODO
 */
@Controller
@RequestMapping("/course")
@CrossOrigin
@ResponseBody
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/import")
    public AjaxResult importCourse(@RequestParam("type") String type,
                                   @RequestParam("openId")String openId){
        if("student".equals(type)){
            return courseService.importStuCourse(openId);
        }else if("teacher".equals(type)){
            return courseService.importTeaCourse(openId);
        }else{
            return new AjaxResult().error("type类型错误");
        }
    }

    @RequestMapping("/add")
    public AjaxResult addCourse(@RequestParam("type") String type,
                                @RequestParam("courseName")String courseName,
                                @RequestParam("courseId")String courseId,
                                @RequestParam("courseAddr")String courseAddr,
                                @RequestParam("courseWeek")String courseWeek,
                                @RequestParam("courseTime")String courseTime,
                                @RequestParam("courseStanza")String courseStanza,
                                @RequestParam("openId")String openId,
                                @RequestParam("stuGrade")String stuGrade,
                                @RequestParam("stuClass")String stuClass,
                                @RequestParam("stuMajor")String stuMajor){
       if("teacher".equals(type)){
            return courseService.addTeaCourse(openId,courseName,courseId,
                    courseAddr,courseWeek,courseTime,
                    courseStanza,stuGrade,stuClass,stuMajor);
       }else{
            return new AjaxResult().error("type类型错误");
        }
    }

    @RequestMapping("/select")
    public AjaxResult selectCourse(@RequestParam("type") String type,
                                   @RequestParam("openId")String openId){
        if("student".equals(type)){
            return courseService.selectStuCourse(openId);
        }else if("teacher".equals(type)){
            return courseService.selectTeaCourse(openId);
        }else{
            return new AjaxResult().error("type类型错误");
        }
    }

    @RequestMapping("/update")
    public AjaxResult update( @RequestParam("id")long id,
                              @RequestParam("courseName")String courseName,
                              @RequestParam("courseTime")String courseTime,
                              @RequestParam("courseAddr")String courseAddr,
                              @RequestParam("courseId")String courseId,
                              @RequestParam("courseWeek")String courseWeek,
                              @RequestParam("courseStanza")String courseStanza){
        return courseService.updateCourseInfo(id,courseName,courseTime,courseAddr,courseId,courseWeek,courseStanza);
    }

    @RequestMapping("/search")
    public AjaxResult search(@RequestParam("selectType")String selectType,
                             @RequestParam("type")String type,
                             @RequestParam("selectParam")String selectParam){
        if("teacher".equals(type)){
            return courseService.searchTeaCourse(selectType,selectParam);
        }else if("student".equals(type)){
            return courseService.searchStuCourse(selectType,selectParam);
        }else{
            return new AjaxResult().error("type类型出错");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public AjaxResult search(@RequestParam("id")long id,
                             @RequestParam("type")String type){
        if("teacher".equals(type)){
            return courseService.deleteTeaCourse(id);
        }else if("student".equals(type)){
            return courseService.deleteStuCourse(id);
        }else{
            return new AjaxResult().error("type类型出错");
        }
    }

    @RequestMapping("/selectStuList")
    public AjaxResult selectStuList(@RequestParam("id")long id,
                                    @RequestParam("type")String type){
        if("teacher".equals(type)){
            return courseService.teaSelectStuList(id);
        }else if("student".equals(type)){
            return courseService.stuSelectStuList(id);
        }else{
            return new AjaxResult().error("type类型出错");
        }
    }


    @RequestMapping("/deleteCourseById")
    public AjaxResult deleteCourseById(@RequestParam("id")long id){
        return courseService.deleteCourseById(id);
    }
}
