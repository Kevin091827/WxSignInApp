package com.example.signin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SchoolUser
 * @Description: TODO
 */
@Data
public class SchoolUser {

    protected long id;
    protected String name;
    protected String accountId;
    protected String collage;
    protected String school;
    protected String phone;
    protected String email;
    protected String openId;
    protected Date gmtCreate;
    protected Date gmtModify;

}
