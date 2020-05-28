package com.example.signin.mapper;

import com.example.signin.entity.Course;
import com.example.signin.entity.StudentCourse;
import com.example.signin.entity.TeacherCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:StudentCourseMapper
 * @Description: TODO
 */
@Mapper
public interface StudentCourseMapper {

    @Insert("insert into tb_stu_course (course_id," +
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
            "course_time," +
            "stu_id," +
            "stu_name) values (#{studentCourse.courseId} ,#{studentCourse.teaName} ,#{studentCourse.teaId}," +
            "#{studentCourse.courseName} ,#{studentCourse.courseAddr} ,#{studentCourse.gmtCreate} ," +
            "#{studentCourse.gmtModify} , #{studentCourse.courseWeek} ,#{studentCourse.courseStanza} ," +
            "#{studentCourse.stuClass} ,#{studentCourse.stuGrade} ,#{studentCourse.stuMajor} ,#{studentCourse.courseTime}," +
            "#{studentCourse.stuId} ,#{studentCourse.stuName}  )")
    int insertStuCourse(@Param("studentCourse") StudentCourse studentCourse);

    @Select("select * from tb_stu_course where stu_id = #{stuId} ")
    @Results(id = "stuCourseResultMap", value = {
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
            @Result(property = "stuMajor",column = "stu_major"),
            @Result(property = "stuId",column = "stu_id"),
            @Result(property = "stuName",column = "stu_name")})
    List<StudentCourse> selectStuCourse(String stuId);

    @Update("update tb_stu_course set course_name = #{courseName} ," +
            "course_time = #{courseTime}," +
            "course_addr = #{courseAddr} ," +
            "course_id = #{courseId} ," +
            "course_week = #{courseWeek} ," +
            "course_stanza = #{courseStanza} " +
            "where stu_class = #{stuClass} and stu_grade = #{stuGrade} and stu_major = #{stuGrade}  and tea_id = #{teaId} ")
    int updateCourseInfo(@Param("stuClass") String stuClass,
                         @Param("stuGrade") String stuGrade,
                         @Param("stuMajor") String stuMajor,
                         @Param("courseName") String courseName, @Param("courseTime") String courseTime,
                         @Param("courseAddr") String courseAddr, @Param("courseId") String courseId,
                         @Param("courseWeek") String courseWeek, @Param("courseStanza") String courseStanza,
                         @Param("teaId")String teaId);

    @Delete("delete from tb_stu_course where id = #{id} ")
    int deleteCourseById(@Param("id") long id);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where course_name  like '%${selectParam}%'")
    List<StudentCourse> selectStuCourseByName(String selectParam);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where course_id  like '%${selectParam}%' ")
    List<StudentCourse> selectStuCourseByCourseId(String selectParam);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where course_time  like '%${selectParam}%' ")
    List<StudentCourse> selectStuCourseByTime(String selectParam);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where course_addr  like '%${selectParam}%' ")
    List<StudentCourse> selectStuCourseByAddr(String selectParam);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where tea_name  like '%${selectParam}%' ")
    List<StudentCourse> selectStuCourseByTeaName(String selectParam);

    @ResultMap("stuCourseResultMap")
    @Select("select * from tb_stu_course where id = #{id} ")
    StudentCourse selectStuCourseById(long id);
}
