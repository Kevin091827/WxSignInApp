package com.example.signin.mapper;

import com.example.signin.entity.SchoolInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:AuthMapper
 * @Description: TODO
 */
@Mapper
public interface AuthMapper {

    @Select("select * from tb_school_info where school = #{school} " +
            "and type = #{type} " +
            "and accountId = #{accountId}" +
            " and password = #{pwd} ")
    @Results(id = "infoResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "type", column = "type"),
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "school", column = "school"),
            @Result(property = "password", column = "password"),
            @Result(property = "gmtCreate", column = "gmt_create"),
            @Result(property = "gmtModify", column = "gmt_modify")})
    SchoolInfo selectInfo(@Param("school") String school,
                          @Param("type") String type,
                          @Param("accountId") String accountId,
                          @Param("pwd") String pwd);
}
