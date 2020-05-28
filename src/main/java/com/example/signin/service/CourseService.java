package com.example.signin.service;

import com.example.signin.util.AjaxResult;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:CourseService
 * @Description: TODO
 */
public interface CourseService {
    
    AjaxResult importStuCourse(String openId);

    AjaxResult importTeaCourse(String openId);

    AjaxResult addTeaCourse(String openId, String courseName, String courseId,
                            String courseAddr, String courseWeek, String courseTime,
                            String courseStanza, String stuGrade, String stuClass, String stuMajor);

    AjaxResult selectStuCourse(String openId);

    AjaxResult selectTeaCourse(String openId);

    AjaxResult updateCourseInfo(long id, String courseName, String courseTime, String courseAddr, String courseId, String courseWeek, String courseStanza);

    AjaxResult searchTeaCourse(String selectType, String selectParam);

    AjaxResult searchStuCourse(String selectType, String selectParam);

    AjaxResult deleteStuCourse(long id);

    AjaxResult deleteTeaCourse(long id);

    AjaxResult teaSelectStuList(long id);

    AjaxResult stuSelectStuList(long id);

    AjaxResult deleteCourseById(long id);
}
