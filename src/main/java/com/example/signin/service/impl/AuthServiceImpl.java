package com.example.signin.service.impl;

import com.example.signin.entity.SchoolInfo;
import com.example.signin.entity.Student;
import com.example.signin.entity.Teacher;
import com.example.signin.mapper.AuthMapper;
import com.example.signin.mapper.StudentMapper;
import com.example.signin.mapper.TeacherMapper;
import com.example.signin.service.AuthService;
import com.example.signin.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AuthServiceImpl
 * @Description: TODO
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public AjaxResult auth(String school, String type, String accountId, String pwd,String openId) {
        SchoolInfo schoolInfo = authMapper.selectInfo(school,type,accountId,pwd);
        if(schoolInfo == null){
            System.out.println("-----------------------------------------------a");
            return new AjaxResult().error("认证失败");
        }
        if("teacher".equals(type)){
            Teacher teacher = new Teacher();
            teacher.setOpenId(openId);
            teacher.setSchool(school);
            teacher.setGmtCreate(new Date());
            teacher.setGmtModify(new Date());
            teacher.setAccountId(accountId);
            int i = teacherMapper.insertInfo(teacher);
            if(i == 1){
                return new AjaxResult().ok("认证成功");
            }
        }else if("student".equals(type)){
            Student student = new Student();
            student.setOpenId(openId);
            student.setSchool(school);
            student.setGmtCreate(new Date());
            student.setGmtModify(new Date());
            student.setAccountId(accountId);
            System.out.println("-----------------------------------------------aaaa");
            int i = studentMapper.insertInfo(student);
            if(i == 1){
                return new AjaxResult().ok("认证成功");
            }
        }
        System.out.println("-----------------------------------------------aaaaaaaaaaaa");
        return new AjaxResult().error("认证失败");
    }

    @Override
    public AjaxResult isFirstLogin(String openId) {
        Student student = studentMapper.selectStuByOpenId(openId);
        if(student != null){
            Map map = new HashMap();
            map.put("msg","false");
            map.put("type","student");
            map.put("student",student);
            return new AjaxResult().ok(map);
        }else{
            Teacher teacher = teacherMapper.selectTeaByOpenId(openId);
            if(teacher != null){
                Map map = new HashMap();
                map.put("msg","false");
                map.put("type","teacher");
                map.put("teacher",teacher);
                return new AjaxResult().ok(map);
            }else{
                return new AjaxResult().ok("该用户为第一次登录，需认证");
            }
        }
    }
}
