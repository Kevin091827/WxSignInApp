package com.example.signin.service.impl;

import com.example.signin.entity.SignIn;
import com.example.signin.entity.Student;
import com.example.signin.entity.TeacherCourse;
import com.example.signin.entity.UserSignIn;
import com.example.signin.mapper.*;
import com.example.signin.service.SignInService;
import com.example.signin.util.AjaxResult;
import com.example.signin.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SignInServiceImpl
 * @Description: TODO
 */
@Service
@Transactional
public class SignInServiceImpl implements SignInService {

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private StuSignInMapper stuSignInMapper;

    @Autowired
    private TeaSignInMapper teaSignInMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public AjaxResult inputSignCourse(String teaId, String courseAddr, String courseName, String courseTime, String courseStanza) {
        TeacherCourse teacherCourse = teacherCourseMapper.selectCourseBySignInfo(teaId,courseAddr,courseName,courseTime,courseStanza);
        if(teacherCourse == null){
            return new AjaxResult().error("没有该课程");
        }
        return new AjaxResult().ok(teacherCourse);
    }


    @Override
    public AjaxResult requestSignIn(long id, String signNum,String openId) {
        if(redisTemplate.opsForValue().get(signNum) != null ){
            return new AjaxResult().error("签到码重复，请重新输入");
        }
        List<SignIn> signIn = signInMapper.selectSignInBySignNum(signNum);
        if(signIn.size() > 0){
            return new AjaxResult().error("签到码重复，请重新输入");
        }
        redisTemplate.opsForValue().set(signNum,id,15, TimeUnit.MINUTES);
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setGmtCreate(new Date());
        userSignIn.setGmtModify(new Date());
        userSignIn.setSignInNum(signNum);
        userSignIn.setOpenId(openId);
        int i = teaSignInMapper.insertSignInRecord(userSignIn);
        if(i < 1){
            return new AjaxResult().error("签到记录新增失败");
        }
        return new AjaxResult().ok("成功发出签到请求");
    }

    @Override
    public AjaxResult responseSignIn(String stuId, String signNum, String gpsInfo,String openId) {
        if(redisTemplate.opsForValue().get(openId) != null){
            return new AjaxResult().error("无需重复签到");
        }
        if(redisTemplate.opsForValue().get(signNum) == null){
            return new AjaxResult().error("签到以过期，旷课处理");
        }
        long id = (long)redisTemplate.opsForValue().get(signNum);
        //查出课程信息
        TeacherCourse teacherCourse = teacherCourseMapper.selectTeaCourseById(id);
        if(teacherCourse == null){
            return new AjaxResult().error("没有该课程");
        }
        SignIn signIn = new SignIn();
        signIn.setCourseAddr(teacherCourse.getCourseAddr());
        signIn.setCourseId(teacherCourse.getCourseId());
        signIn.setCourseName(teacherCourse.getCourseName());
        signIn.setCourseStanza(teacherCourse.getCourseStanza());
        signIn.setCourseWeek(teacherCourse.getCourseWeek());
        signIn.setGmtCreate(new Date());
        signIn.setGmtModify(new Date());
        signIn.setIsSign("Y");
        signIn.setSignAddr(gpsInfo);
        signIn.setTeaId(teacherCourse.getTeaId());
        signIn.setTeaName(teacherCourse.getTeaName());
        signIn.setStuId(stuId);
        signIn.setSignNum(signNum);
        //签到信息写入数据库表
        int i = signInMapper.insertSignInfo(signIn);
        if(i < 1){
            return new AjaxResult().error("签到失败");
        }
        UserSignIn userSignIn = new UserSignIn();
        userSignIn.setGmtCreate(new Date());
        userSignIn.setGmtModify(new Date());
        userSignIn.setSignInNum(signNum);
        userSignIn.setOpenId(openId);
        int j = stuSignInMapper.insertSignInRecord(userSignIn);
        if(j < 1){
            return new AjaxResult().error("签到记录新增失败");
        }
        redisTemplate.opsForValue().set(openId,signNum,15,TimeUnit.MINUTES);
        Map map = new HashMap();
        map.put("courseName",teacherCourse.getCourseName());
        map.put("msg","签到成功");
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult selectSignRecord(String type, String openid) {
        if("teacher".equals(type)){
            List<String> userSignInList = teaSignInMapper.selectSignInInfoByOpenId(openid);
            List<List<SignIn>> result = new ArrayList<>();
            for(String signInNum : userSignInList){
                List<SignIn> signIn = signInMapper.selectSignInBySignNum(signInNum);
                if(signIn.size() != 0) {
                    result.add(signIn);
                }
            }
            return new AjaxResult().ok(result);
        }else if("student".equals(type)){
            List<String> userSignInList = stuSignInMapper.selectSignInInfoByOpenId(openid);
            List<List<SignIn>> result = new ArrayList<>();
            for(String signInNum : userSignInList){
                List<SignIn> signIn = signInMapper.selectSignInBySignNum(signInNum);
                if(signIn.size() != 0) {
                    result.add(signIn);
                }
            }
            return new AjaxResult().ok(result);
        }else{
            return new AjaxResult().error("type类型出错");
        }
    }

    @Override
    public AjaxResult signInNow(String signNum,long id) {
        //查出课程信息
        TeacherCourse teacherCourse = teacherCourseMapper.selectTeaCourseById(id);
        //查出该课程所有学生
        String stuClass = teacherCourse.getStuClass();
        String stuGrade = teacherCourse.getStuGrade();
        String stuMajor = teacherCourse.getStuMajor();
        List<Student> studentList = studentMapper.selectStuByCourseInfo(stuClass,stuGrade,stuMajor);
        if(teacherCourse == null){
            return new AjaxResult().error("没有该课程");
        }
        List<SignIn> signIn =  signInMapper.selectSignInBySignNum(signNum);
        //未签到
        List<Student> noSignInList = new ArrayList<>();
        //已签到
        List<Student> hadSignInList = new ArrayList<>();
        List<String> hadSignInStuIdList = new ArrayList<>();
        for(SignIn s : signIn){
            if("Y".equals(s.getIsSign())){
                String stuId = s.getStuId();
                for(Student student : studentList){
                    if(student.getAccountId().equals(stuId)){
                        hadSignInList.add(student);
                        hadSignInStuIdList.add(student.getAccountId());
                    }
                }
            }
        }
        System.out.println("--------------" + studentList.size());
        for(Student student : studentList){
            System.out.println(student.getGrade() + " " + student.getMajor() + " " + student.getMajor());
            if(!hadSignInStuIdList.contains(student.getAccountId())){
                noSignInList.add(student);
            }
        }
        Map map = new HashMap<>();
        map.put("hadSignIn",hadSignInList);
        map.put("noSignIn",noSignInList);
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult deleteRecordById(long id) {
        String signInNum  = teaSignInMapper.selectSignInById(id);
        int j = signInMapper.deleteSignInfoBynNum(signInNum);
        int i = teaSignInMapper.deleteRecordById(id);
        if(i > 0 && j > 0){
            return new AjaxResult().ok("删除成功");
        }
        return new AjaxResult().error("删除失败");
    }

    @Override
    public AjaxResult exportExcel(HttpServletResponse response,String teaId) {
        List<SignIn> list = signInMapper.selectSignInByTeaId(teaId);
        ExcelUtils.exportExcel(list,"签到表","签到表", SignIn.class,"签到表.xls",response);
        return new AjaxResult().ok();
    }
}
