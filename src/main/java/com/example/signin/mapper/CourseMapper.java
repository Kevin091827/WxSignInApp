package com.example.signin.mapper;

import com.example.signin.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:CourseMapper
 * @Description: TODO
 */
@Mapper
public interface CourseMapper {

    @Results(id = "courseResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "teaName", column = "tea_name"),
            @Result(property = "teaId", column = "tea_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseTime", column = "course_time"),
            @Result(property = "courseAddr", column = "course_addr"),
            @Result(property = "courseWeek", column = "course_week"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModify", column = "gmt_modify"),
            @Result(property = "courseStanza", column = "course_stanza"),
            @Result(property = "stuClass", column = "stu_class"),
            @Result(property = "stuGrade", column = "stu_grade"),
            @Result(property = "stuMajor",column = "stu_major")})
    @Select("select * from tb_course where stu_class = #{stuClass} and stu_grade = #{stuGrade} and stu_major = #{stuMajor} ")
    List<Course> selectCourse(@Param("stuClass") String stuClass,
                              @Param("stuGrade")String stuGrade,
                              @Param("stuMajor")String stuMajor);

    @ResultMap("courseResultMap")
    @Select("select * from tb_course where tea_id = #{accountId}")
    List<Course> selectCourseByAccId(String accountId);
}
