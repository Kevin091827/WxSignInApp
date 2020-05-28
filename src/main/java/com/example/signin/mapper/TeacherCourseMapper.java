package com.example.signin.mapper;

import com.example.signin.entity.Course;
import com.example.signin.entity.TeacherCourse;
import com.example.signin.util.AjaxResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:TeacherCourseMapper
 * @Description: TODO
 */
@Mapper
public interface TeacherCourseMapper {

    @Insert("insert into tb_tea_course (course_id," +
            "tea_name," +
            "tea_id," +
            "course_name," +
            "course_addr," +
            "gmt_create," +
            "gmt_modify," +
            "course_week," +
            "course_stanza," +
            "stu_class," +
            "stu_grade," +
            "stu_major," +
            "course_time" +
            ") values (#{teacherCourse.courseId} ,#{teacherCourse.teaName} ,#{teacherCourse.teaId}," +
            "#{teacherCourse.courseName} ,#{teacherCourse.courseAddr} ,#{teacherCourse.gmtCreate} ," +
            "#{teacherCourse.gmtModify} , #{teacherCourse.courseWeek} ,#{teacherCourse.courseStanza} ," +
            "#{teacherCourse.stuClass} ,#{teacherCourse.stuGrade} ,#{teacherCourse.stuMajor} ,#{teacherCourse.courseTime} )")
    int insertTeaCourse(@Param("teacherCourse") TeacherCourse teacherCourse);

    @Select("select * from tb_tea_course where tea_id = #{teaId} ")
    @Results(id = "teaCourseResultMap", value = {
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
    List<TeacherCourse> selectTeaCourse(String teaId);

    @Update("update tb_tea_course set course_name = #{courseName} ," +
            "course_time = #{courseTime} ," +
            "course_addr = #{courseAddr} ," +
            "course_id = #{courseId}," +
            "course_week = #{courseWeek} ," +
            "course_stanza = #{courseStanza} where id = #{id} ")
    int updateCourseInfo(@Param("id") long id,
                         @Param("courseName") String courseName,
                         @Param("courseTime") String courseTime,
                         @Param("courseAddr") String courseAddr,
                         @Param("courseId") String courseId,
                         @Param("courseWeek")String courseWeek,
                         @Param("courseStanza")String courseStanza);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where id = #{id} ")
    TeacherCourse selectTeaCourseById(long id);

    @Delete("delete from tb_tea_course where id = #{id} ")
    int deleteCourseById(long id);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where course_name like '%${selectParam}%' ")
    List<TeacherCourse> selectTeaCourseByName(String selectParam);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where course_id like '%${selectParam}%' ")
    List<TeacherCourse> selectTeaCourseByCourseId(String selectParam);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where course_time like '%${selectParam}%' ")
    List<TeacherCourse> selectTeaCourseByTime(String selectParam);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where course_addr like '%${selectParam}%' ")
    List<TeacherCourse> selectTeaCourseByAddr(String selectParam);

    @ResultMap("teaCourseResultMap")
    @Select("select * from tb_tea_course where tea_id = #{teaId} and course_addr = #{courseAddr} and " +
            "course_name = #{courseName} and course_time = #{courseTime} and course_stanza = #{courseStanza} ")
    TeacherCourse selectCourseBySignInfo(@Param("teaId") String teaId,
                                         @Param("courseAddr") String courseAddr,
                                         @Param("courseName") String courseName,
                                         @Param("courseTime") String courseTime,
                                         @Param("courseStanza") String courseStanza);
}
