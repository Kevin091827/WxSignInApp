package com.example.signin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AppConfig
 * @Description: TODO
 */
@Data
public class AppConfig {

    private int id;
    private Date gmtCreate;
    private Date gmtModify;
    private int currentWeek;
}
