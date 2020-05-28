package com.example.signin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:StuSignIn
 * @Description: TODO
 */
@Data
public class UserSignIn {

    private long id;
    private String openId;
    private String signInNum;
    private Date gmtCreate;
    private Date gmtModify;
}
