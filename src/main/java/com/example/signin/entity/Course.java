package com.example.signin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:Course
 * @Description: TODO
 */
@Data
public class Course {
    private long id;
    private String teaName;
    private String teaId;
    private String courseName;
    private String courseId;
    private String courseTime;
    private String courseAddr;
    private Date gmtCreate;
    private Date gmtModify;
    private String courseWeek;
    private String courseStanza;
    private String stuClass;
    private String stuGrade;
    private String stuMajor;
}
