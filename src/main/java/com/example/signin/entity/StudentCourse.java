package com.example.signin.entity;

import lombok.Data;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:StudentCourse
 * @Description: TODO
 */
@Data
public class StudentCourse extends Course {
    private String stuId;
    private String stuName;
}
