package com.example.signin.service.impl;

import com.example.signin.entity.*;
import com.example.signin.mapper.*;
import com.example.signin.service.CourseService;
import com.example.signin.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:CourseServiceImpl
 * @Description: TODO
 */
@Service
@Slf4j
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private SignInMapper signInMapper;

    @Autowired
    private TeaSignInMapper teaSignInMapper;

    @Override
    public AjaxResult importStuCourse(String openId) {
        Student student = studentMapper.selectStuByOpenId(openId);
        if(student == null){
            return new AjaxResult().error("没有该学生");
        }
        String stuClass = student.getStuClass();
        String stuMajor = student.getMajor();
        String stuGrade = student.getGrade();
        List<Course> list = courseMapper.selectCourse(stuClass,stuGrade,stuMajor);
        if(list.size() == 0){
            return new AjaxResult().error("没有课程，无需导入");
        }
        for(Course course : list){
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStuId(student.getAccountId());
            studentCourse.setStuName(student.getName());
            studentCourse.setCourseAddr(course.getCourseAddr());
            studentCourse.setCourseId(course.getCourseId());
            studentCourse.setCourseName(course.getCourseName());
            studentCourse.setCourseStanza(course.getCourseStanza());
            studentCourse.setCourseTime(course.getCourseTime());
            studentCourse.setCourseWeek(course.getCourseWeek());
            studentCourse.setGmtCreate(new Date());
            studentCourse.setGmtModify(new Date());
            studentCourse.setStuClass(course.getStuClass());
            studentCourse.setStuGrade(course.getStuGrade());
            studentCourse.setStuMajor(course.getStuMajor());
            studentCourse.setTeaId(course.getTeaId());
            studentCourse.setTeaName(course.getTeaName());
            int i = studentCourseMapper.insertStuCourse(studentCourse);
            if(i < 1){
                return new AjaxResult().error("导入失败");
            }
        }
        return new AjaxResult().ok("导入成功");
    }

    @Override
    public AjaxResult importTeaCourse(String openId) {
        Teacher teacher = teacherMapper.selectTeaByOpenId(openId);
        if(teacher == null){
            return new AjaxResult().error("没有该教师");
        }
        String accountId = teacher.getAccountId();
        List<Course> list = courseMapper.selectCourseByAccId(accountId);
        if(list.size() == 0){
            return new AjaxResult().error("该教师没有课程，无需导入");
        }
        for(Course course : list){
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setCourseAddr(course.getCourseAddr());
            teacherCourse.setCourseId(course.getCourseId());
            teacherCourse.setCourseName(course.getCourseName());
            teacherCourse.setCourseStanza(course.getCourseStanza());
            teacherCourse.setCourseTime(course.getCourseTime());
            teacherCourse.setCourseWeek(course.getCourseWeek());
            teacherCourse.setGmtCreate(new Date());
            teacherCourse.setGmtModify(new Date());
            teacherCourse.setStuClass(course.getStuClass());
            teacherCourse.setStuGrade(course.getStuGrade());
            teacherCourse.setStuMajor(course.getStuMajor());
            teacherCourse.setTeaId(course.getTeaId());
            teacherCourse.setTeaName(course.getTeaName());
            int i = teacherCourseMapper.insertTeaCourse(teacherCourse);
            if(i < 1){
                return new AjaxResult().error("导入失败");
            }
        }
        return new AjaxResult().ok("导入成功");
    }

    @Override
    public AjaxResult addTeaCourse(String openId, String courseName, String courseId,
                                   String courseAddr, String courseWeek, String courseTime,
                                   String courseStanza, String stuGrade, String stuClass, String stuMajor) {
        Teacher teacher = teacherMapper.selectTeaByOpenId(openId);
        if(teacher == null){
            return new AjaxResult().error("没有该教师");
        }
        String teaId = teacher.getAccountId();
        String teaName = teacher.getName();
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCourseAddr(courseAddr);
        teacherCourse.setCourseName(courseName);
        teacherCourse.setCourseId(courseId);
        teacherCourse.setCourseStanza(courseStanza);
        teacherCourse.setCourseTime(courseTime);
        teacherCourse.setCourseWeek(courseWeek);
        teacherCourse.setGmtCreate(new Date());
        teacherCourse.setGmtModify(new Date());
        teacherCourse.setStuClass(stuClass);
        teacherCourse.setStuGrade(stuGrade);
        teacherCourse.setStuMajor(stuMajor);
        teacherCourse.setTeaId(teaId);
        teacherCourse.setTeaName(teaName);
        int i = teacherCourseMapper.insertTeaCourse(teacherCourse);
        if(i < 1){
            return new AjaxResult().error("添加失败");
        }
        //将新增的课程同步到该课的所有学生
        List<Student> list = studentMapper.selectStuByCourseInfo(stuClass,stuGrade,stuMajor);
        for(Student student : list){
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setTeaName(teaName);
            studentCourse.setTeaId(teaId);
            studentCourse.setStuMajor(stuMajor);
            studentCourse.setStuGrade(stuGrade);
            studentCourse.setStuClass(stuClass);
            studentCourse.setGmtModify(new Date());
            studentCourse.setGmtCreate(new Date());
            studentCourse.setCourseWeek(courseWeek);
            studentCourse.setCourseTime(courseTime);
            studentCourse.setCourseStanza(courseStanza);
            studentCourse.setCourseName(courseName);
            studentCourse.setCourseId(courseId);
            studentCourse.setCourseAddr(courseAddr);
            studentCourse.setStuName(student.getName());
            studentCourse.setStuId(student.getAccountId());
            studentCourseMapper.insertStuCourse(studentCourse);
        }
        return new AjaxResult().ok("添加完成");
    }

    @Override
    public AjaxResult selectStuCourse(String openId) {
        Student student = studentMapper.selectStuByOpenId(openId);
        if(student == null){
            return new AjaxResult().error("没有该学生");
        }
        String stuId = student.getAccountId();
        List<StudentCourse> list = studentCourseMapper.selectStuCourse(stuId);
        if(list.size() == 0){
            return new AjaxResult().error("该学生没有课程");
        }
        Map map = new HashMap<>();
        int currentWeek = configMapper.selectCurrentWeek();
        map.put("list",list);
        map.put("currentWeek",currentWeek);
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult selectTeaCourse(String openId) {
        Teacher teacher = teacherMapper.selectTeaByOpenId(openId);
        if(teacher == null){
            return new AjaxResult().error("没有该教师");
        }
        String teaId = teacher.getAccountId();
        List<TeacherCourse> list = teacherCourseMapper.selectTeaCourse(teaId);
        if(list.size() == 0){
            return new AjaxResult().error("该老师没有课程");
        }
        Map map = new HashMap<>();
        int currentWeek = configMapper.selectCurrentWeek();
        map.put("list",list);
        map.put("currentWeek",currentWeek);
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult updateCourseInfo(long id,
                                       String courseName,
                                       String courseTime,
                                       String courseAddr,
                                       String courseId,
                                       String courseWeek,
                                       String courseStanza) {
        TeacherCourse teacherCourse = teacherCourseMapper.selectTeaCourseById(id);
        int i = teacherCourseMapper.updateCourseInfo(id,courseName,courseTime,courseAddr,courseId,courseWeek,courseStanza);
        if(i < 1){
            return new AjaxResult().error("更新失败");
        }
        //同步到该课学生
        String stuClass = teacherCourse.getStuClass();
        String stuGrade = teacherCourse.getStuGrade();
        String stuMajor = teacherCourse.getStuMajor();
        String teaId = teacherCourse.getTeaId();
        int j = studentCourseMapper.updateCourseInfo(stuClass,stuGrade,stuMajor,courseName,courseTime,courseAddr,courseId,courseWeek,courseStanza,teaId);
        if(j < 1){
            return new AjaxResult().error("同步到该课学生失败");
        }
        return new AjaxResult().ok("更新同步完成");
    }

    @Override
    public AjaxResult searchTeaCourse(String selectType, String selectParam) {
        //关键字可以是课程的名称、课程的代码、上课的时间以及地点
        List<TeacherCourse> list = new ArrayList<>();
        switch (selectType){
            case "name" : list = teacherCourseMapper.selectTeaCourseByName(selectParam);break;
            case "courseId" : list = teacherCourseMapper.selectTeaCourseByCourseId(selectParam);break;
            case "time" : list = teacherCourseMapper.selectTeaCourseByTime(selectParam);break;
            case "addr" : list = teacherCourseMapper.selectTeaCourseByAddr(selectParam);break;
        }
        Map map = new HashMap<>();
        int currentWeek = configMapper.selectCurrentWeek();
        map.put("list",list);
        map.put("currentWeek",currentWeek);
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult searchStuCourse(String selectType, String selectParam) {
        //课程的名称、课程的代码、授课教师姓名、上课的时间以及上课地点
        List<StudentCourse> list = new ArrayList<>();
        switch (selectType){
            case "name" : list = studentCourseMapper.selectStuCourseByName(selectParam);break;
            case "courseId" : list = studentCourseMapper.selectStuCourseByCourseId(selectParam);break;
            case "time" : list = studentCourseMapper.selectStuCourseByTime(selectParam);break;
            case "addr" : list = studentCourseMapper.selectStuCourseByAddr(selectParam);break;
            case "teaName" : list = studentCourseMapper.selectStuCourseByTeaName(selectParam);break;
        }
        Map map = new HashMap<>();
        int currentWeek = configMapper.selectCurrentWeek();
        map.put("list",list);
        map.put("currentWeek",currentWeek);
        return new AjaxResult().ok(map);
    }

    @Override
    public AjaxResult deleteStuCourse(long id) {
        int j = studentCourseMapper.deleteCourseById(id);
        if(j < 1){
            return new AjaxResult().error("删除失败");
        }
        return new AjaxResult().ok("删除成功");
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public AjaxResult deleteTeaCourse(long id) {
        //先查出课程信息，根据课程信息找出签到信息，再删除
        TeacherCourse teacherCourse = teacherCourseMapper.selectTeaCourseById(id);
        if(teacherCourse == null){
            return new AjaxResult().error("没有该课程");
        }
        //删除签到记录
        UserSignIn signIn = signInMapper.selectSignInBySignInfo(teacherCourse.getTeaId(),
                teacherCourse.getCourseAddr(),
                teacherCourse.getCourseName(),
                teacherCourse.getCourseTime(),
                teacherCourse.getCourseStanza());
        if(signIn != null){
            signInMapper.deleteSignInfoBynNum(signIn.getSignInNum());
            teaSignInMapper.deleteRecordByNum(signIn.getSignInNum());
        }
        int i = teacherCourseMapper.deleteCourseById(id);
        if(i < 1){
            return new AjaxResult().error("删除失败");
        }
        return new AjaxResult().ok("删除成功");
    }

    @Override
    public AjaxResult teaSelectStuList(long id) {
        TeacherCourse teacherCourse = teacherCourseMapper.selectTeaCourseById(id);
        String stuGrade = teacherCourse.getStuGrade();
        String stuClass = teacherCourse.getStuClass();
        String stuMajor = teacherCourse.getStuMajor();
        List<Student> list = studentMapper.selectStuByCourseInfo(stuClass,stuGrade,stuMajor);
        return new AjaxResult().ok(list);
    }

    @Override
    public AjaxResult stuSelectStuList(long id) {
        StudentCourse studentCourse = studentCourseMapper.selectStuCourseById(id);
        String stuGrade = studentCourse.getStuGrade();
        String stuClass = studentCourse.getStuClass();
        String stuMajor = studentCourse.getStuMajor();
        List<Student> list = studentMapper.selectStuByCourseInfo(stuClass,stuGrade,stuMajor);
        return new AjaxResult().ok(list);
    }

    @Override
    public AjaxResult deleteCourseById(long id) {
        int i = teacherCourseMapper.deleteCourseById(id);
        if(i > 0){
            return new AjaxResult().ok("删除成功");
        }else{
            return new AjaxResult().error("删除失败");
        }
    }
}
