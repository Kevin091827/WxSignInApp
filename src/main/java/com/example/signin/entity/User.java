package com.example.signin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:User
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String openId;
    private Date gmtCreate;
    private Date gmtModify;
    private String type;
}
