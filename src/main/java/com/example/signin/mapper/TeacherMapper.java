package com.example.signin.mapper;

import com.example.signin.entity.Student;
import com.example.signin.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TeacherMapper
 * @Description: TODO
 */
@Mapper
public interface TeacherMapper {

    @Select("select * from tb_teacher where tea_id = #{accountId} ")
    @ResultMap("teaResultMap")
    Teacher selectTeaByAccountId(@Param("accountId") String accountId);

    @Update("update tb_teacher set tea_collage = #{teacher.collage}" +
            " ,tea_email = #{teacher.email}" +
            " ,tea_phone = #{teacher.email}" +
            " ,gmt_modify = #{teacher.gmtModify}" +
            " ,tea_name = #{teacher.name}  " +
            "where tea_id = #{teacher.accountId}  ")
    int updateTeaInfo(@Param("teacher") Teacher teacher);

    @Insert("insert ignore  into tb_teacher (tea_name,tea_id,tea_school,tea_collage,tea_phone,tea_email,gmt_create,gmt_modify,tea_openid)" +
            "values (#{teacher.name}," +
            "#{teacher.accountId} ," +
            "#{teacher.school}," +
            "#{teacher.collage} ," +
            "#{teacher.phone} ," +
            "#{teacher.email} ," +
            "#{teacher.gmtCreate} ," +
            "#{teacher.gmtModify} ," +
            "#{teacher.openId} )")
    int insertInfo(@Param("teacher") Teacher tea);

    @Select("select * from tb_teacher where tea_openid = #{openId} ")
    @ResultMap("teaResultMap")
    Teacher selectTeaByOpenId(String openId);

    @Select("select * from tb_teacher where tea_id = #{accountId} and tea_school = #{school} ")
    @Results(id = "teaResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "tea_name"),
            @Result(property = "accountId", column = "tea_id"),
            @Result(property = "collage", column = "tea_collage"),
            @Result(property = "school", column = "tea_school"),
            @Result(property = "phone", column = "tea_phone"),
            @Result(property = "email", column = "tea_email"),
            @Result(property = "openId", column = "tea_openid"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModify", column = "gmt_modify")})
    Teacher selectTeaInfo(@Param("accountId")String accountId,
                          @Param("school")String school);

    @Select("select tea_email from tb_teacher where tea_openid = #{openId}")
    String selectEmailByOpenId(String openId);
}
