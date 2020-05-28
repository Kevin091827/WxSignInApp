package com.example.signin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:Student
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student extends SchoolUser{

    private String major;
    private String grade;
    private String stuClass;
}
