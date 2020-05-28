package com.example.signin.mapper;

import com.example.signin.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:StudentMapper
 * @Description: TODO
 */
@Mapper
public interface StudentMapper {

    @Select("select * from tb_stu where stu_id = #{accountId} ")
    @ResultMap("stuResultMap")
    Student selectStuByAccountId(String accountId);

    @Update("update tb_stu set stu_name = #{stu.name} ," +
            "stu_collage = #{stu.collage}, " +
            "stu_school = #{stu.school} ," +
            "stu_phone = #{stu.phone} ," +
            "stu_major = #{stu.major}, " +
            "stu_grade = #{stu.grade}, " +
            "stu_class = #{stu.stuClass}, " +
            "stu_email = #{stu.email} ," +
            "gmt_create = #{stu.gmtCreate} ," +
            "gmt_modify = #{stu.gmtModify}" +
            "where stu_id = #{stu.accountId}  ")
    int updateStuInfo(@Param("stu") Student student);

    @Insert("insert ignore into tb_stu (stu_id,stu_collage," +
            "stu_school,stu_phone,stu_major," +
            "stu_openid,stu_grade,stu_class," +
            "stu_email,gmt_create,gmt_modify,stu_name) values (" +
            "#{stu.accountId} ," +
            "#{stu.collage} ," +
            "#{stu.school}," +
            "#{stu.phone}," +
            "#{stu.major}," +
            "#{stu.openId}," +
            "#{stu.grade}," +
            "#{stu.stuClass}," +
            "#{stu.email}, " +
            "#{stu.gmtCreate}," +
            "#{stu.gmtModify}," +
            "#{stu.name} )")
    int insertInfo(@Param("stu")Student stu);

    @Select("select * from tb_stu where stu_openid = #{openId} ")
    @ResultMap("stuResultMap")
    Student selectStuByOpenId(String openId);

    @Select(value = {"select * from tb_stu where stu_id = #{accountId} and stu_school = #{school} "})
    @Results(id = "stuResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "stu_name"),
            @Result(property = "accountId", column = "stu_id"),
            @Result(property = "collage", column = "stu_collage"),
            @Result(property = "school", column = "stu_school"),
            @Result(property = "phone", column = "stu_phone"),
            @Result(property = "email", column = "stu_email"),
            @Result(property = "openId", column = "stu_openid"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModify", column = "gmt_modify"),
            @Result(property = "major", column = "stu_major"),
            @Result(property = "grade", column = "stu_grade"),
            @Result(property = "stuClass", column = "stu_class")})
    Student selectStuInfo(@Param("accountId") String accountId,
                          @Param("school") String school);

    @Select("select stu_email from tb_stu where stu_openid = #{openId}")
    String selectEmailByOpenId(String openId);

    @Select("select * from tb_stu where stu_class = #{stuClass} and stu_grade = #{stuGrade} and stu_major = #{stuMajor} ")
    @ResultMap("stuResultMap")
    List<Student> selectStuByCourseInfo(@Param("stuClass") String stuClass,
                                        @Param("stuGrade") String stuGrade,
                                        @Param("stuMajor") String stuMajor);
}
