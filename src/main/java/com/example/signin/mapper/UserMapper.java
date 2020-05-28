package com.example.signin.mapper;

import com.example.signin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:UserMapper
 * @Description: TODO
 */
@Mapper
public interface UserMapper {

    @Insert("insert into tb_user(openid,gmt_modify,gmt_create) values (#{user.openId}," +
            "#{user.gmtModify} ,#{user.gmtCreate} )")
    int insertUser(@Param("user") User user);
}
