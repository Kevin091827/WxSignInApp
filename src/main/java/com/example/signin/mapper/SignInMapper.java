package com.example.signin.mapper;

import com.example.signin.entity.SignIn;
import com.example.signin.entity.UserSignIn;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:SignInMapper
 * @Description: TODO
 */
@Mapper
public interface SignInMapper {

    @Insert("insert into tb_signin(tea_name,tea_id,course_name,course_addr,course_week," +
            "course_stanza,course_id,is_sign,stu_id,gmt_create,gmt_modify,sign_addr,sign_num)" +
            "values (#{signIn.teaName} ," +
            "#{signIn.teaId} ," +
            "#{signIn.courseName} ," +
            "#{signIn.courseAddr} ," +
            "#{signIn.courseWeek} ," +
            "#{signIn.courseStanza} ," +
            "#{signIn.courseId} ," +
            "#{signIn.isSign} ," +
            "#{signIn.stuId} ," +
            "#{signIn.gmtCreate} ," +
            "#{signIn.gmtModify}," +
            "#{signIn.signAddr} ," +
            "#{signIn.signNum}  )")
    int insertSignInfo(@Param("signIn") SignIn signIn);

    @Select("select * from tb_signin where sign_num = #{signNum} ")
    @Results(id = "signInResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "teaName", column = "tea_name"),
            @Result(property = "teaId", column = "tea_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseAddr", column = "course_addr"),
            @Result(property = "courseWeek", column = "course_week"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModify", column = "gmt_modify"),
            @Result(property = "courseStanza", column = "course_stanza"),
            @Result(property = "isSign", column = "is_sign"),
            @Result(property = "stuId", column = "stu_id"),
            @Result(property = "signAddr",column = "sign_addr"),
            @Result(property = "signNum",column = "sign_num")})
    List<SignIn> selectSignInBySignNum(String signNum);


    @Delete("delete from tb_signin where sign_num = #{signInNum} ")
    int deleteSignInfoBynNum(String signInNum);

    @ResultMap("signInResultMap")
    @Select("select * from tb_signin where tea_id = #{teaId} " +
            "and course_addr = #{courseAddr} " +
            "and course_name = #{courseName} " +
            "and course_time = #{courseTime} " +
            "and course_stanza = #{courseStanza} ")
    UserSignIn selectSignInBySignInfo(@Param("teaId") String teaId,
                                      @Param("courseAddr")String courseAddr,
                                      @Param("courseName") String courseName,
                                      @Param("courseTime")String courseTime,
                                      @Param("courseStanza")String courseStanza);

    @Select("SELECT * from tb_signin where  tea_id = #{teaId}")
    List<SignIn> selectSignInByTeaId(String teaId);
}
