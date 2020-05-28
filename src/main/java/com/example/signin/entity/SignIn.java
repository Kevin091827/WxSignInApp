package com.example.signin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SignIn
 * @Description: TODO
 */
@Data
public class SignIn {

    @ExcelIgnore
    private long id;
    @Excel(name = "teaName")
    private String teaName;
    @Excel(name = "teaId")
    private String teaId;
    @Excel(name = "courseName")
    private String courseName;
    @Excel(name = "courseAddr")
    private String courseAddr;
    @Excel(name = "courseWeek")
    private String courseWeek;
    @Excel(name = "courseStanza")
    private String courseStanza;
    @Excel(name = "courseId")
    private String courseId;
    @Excel(name = "isSign")
    private String isSign;
    @Excel(name = "gmtCreate")
    private Date gmtCreate;
    @Excel(name = "gmtModify")
    private Date gmtModify;
    @Excel(name = "signAddr")
    private String signAddr;
    @Excel(name = "stuId")
    private String stuId;
    @Excel(name = "signNum")
    private String signNum;
}
