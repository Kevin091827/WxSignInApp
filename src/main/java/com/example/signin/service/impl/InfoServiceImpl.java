package com.example.signin.service.impl;

import com.example.signin.entity.Student;
import com.example.signin.entity.Teacher;
import com.example.signin.mapper.StudentMapper;
import com.example.signin.mapper.TeacherMapper;
import com.example.signin.service.InfoService;
import com.example.signin.util.AjaxResult;
import lombok.Data;
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
 * @ClassName:InfoServiceImpl
 * @Description: TODO
 */
@Service
@Slf4j
@Transactional
public class InfoServiceImpl implements InfoService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public AjaxResult improveTeaInfo(String type,
                                     String school,
                                     String name,
                                     String collage,
                                     String accountId,
                                     String phone,
                                     String email,
                                     String openId) {
        Teacher teacher = teacherMapper.selectTeaByAccountId(accountId);
        if(teacher != null){
            teacher.setEmail(email);
            teacher.setPhone(phone);
            teacher.setCollage(collage);
            teacher.setGmtModify(new Date());
            teacher.setName(name);
            int i = teacherMapper.updateTeaInfo(teacher);
            if(i < 1){
                return new AjaxResult().error("修改失败，请重试");
            }else{
                Map map = new HashMap<>();
                map.put("msg","修改成功");
                map.put("teacher",teacher);
                return new AjaxResult().ok(map);
            }
        }else {
            Teacher tea = new Teacher();
            tea.setGmtModify(new Date());
            tea.setGmtCreate(new Date());
            tea.setCollage(collage);
            tea.setPhone(phone);
            tea.setEmail(email);
            tea.setSchool(school);
            tea.setName(name);
            tea.setOpenId(openId);
            tea.setAccountId(accountId);
            int i = teacherMapper.insertInfo(tea);
            if (i < 1) {
                return new AjaxResult().error("完善信息失败");
            }
            Map map = new HashMap();
            map.put("teacher",tea);
            map.put("msg","完善成功");
            return new AjaxResult().ok(map);
        }
    }

    @Override
    public AjaxResult improveStuInfo(String type,
                                     String school,
                                     String name,
                                     String collage,
                                     String accountId,
                                     String phone,
                                     String email,
                                     String grade,
                                     String stuClass,
                                     String openId,
                                     String major) {
        Student student = studentMapper.selectStuByAccountId(accountId);
        if(student != null){
            student.setGmtModify(new Date());
            student.setCollage(collage);//
            student.setPhone(phone);//
            student.setEmail(email);//
            student.setName(name);//
            log.info("---------------------" + name);
            student.setGrade(grade);//
            student.setMajor(major);//
            student.setStuClass(stuClass);//
            int i = studentMapper.updateStuInfo(student);
            if(i < 1){
                return new AjaxResult().error("修改失败，请重试");
            }else{
                Map map = new HashMap<>();
                map.put("msg","修改成功");
                map.put("student",student);
                return new AjaxResult().ok(map);
            }
        }else {
            Student stu = new Student();
            stu.setGmtModify(new Date());
            stu.setGmtCreate(new Date());
            stu.setCollage(collage);//
            stu.setPhone(phone);//
            stu.setEmail(email);//
            stu.setSchool(school);//
            stu.setName(name);//
            log.info("---------------------" + name);
            stu.setOpenId(openId);//
            stu.setAccountId(accountId);//
            stu.setGrade(grade);//
            stu.setMajor(major);//
            stu.setStuClass(stuClass);//
            int i = studentMapper.insertInfo(stu);
            if (i < 1) {
                return new AjaxResult().error("完善信息失败");
            }
            Map map = new HashMap();
            map.put("student",stu);
            map.put("msg","完善成功");
            return new AjaxResult().ok(map);
        }
    }

    @Override
    public AjaxResult selectTeaInfo(String accountId, String school) {
        Teacher teacher = teacherMapper.selectTeaInfo(accountId,school);
        if(teacher != null){
            return new AjaxResult().ok(teacher);
        }
        return new AjaxResult().error("没有该教师信息");
    }

    @Override
    public AjaxResult selectStuInfo(String accountId, String school) {
        Student student = studentMapper.selectStuInfo(accountId,school);
        if(student != null){
            return new AjaxResult().ok(student);
        }
        return new AjaxResult().error("没有该学生信息");
    }

    @Override
    public AjaxResult isNeedImprove(String openId,String type) {
        if("student".equals(type)) {
            String student = studentMapper.selectEmailByOpenId(openId);
            if (student == null) {
                return new AjaxResult().error("需要完善信息");
            }
            return new AjaxResult().ok("无需完善信息");
        }else if("teacher".equals(type)){
            String teacher = teacherMapper.selectEmailByOpenId(openId);
            if (teacher == null) {
                return new AjaxResult().error("需要完善信息");
            }
            return new AjaxResult().ok("无需完善信息");
        }else{
            return new AjaxResult().error("type类型出错，应为teacher或者student");
        }
    }
}
