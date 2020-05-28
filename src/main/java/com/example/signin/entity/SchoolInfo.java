package com.example.signin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SchoolInfo
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolInfo {

    private long id;
    private String type;
    private String name;
    private String school;
    private String accountId;
    private String password;
    private Date gmtCreate;
    private Date gmtModify;
}
