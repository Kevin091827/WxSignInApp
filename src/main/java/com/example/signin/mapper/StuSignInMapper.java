package com.example.signin.mapper;

import com.example.signin.entity.UserSignIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:StuSignInMapper
 * @Description: TODO
 */
@Mapper
public interface StuSignInMapper {

    @Insert("insert into tb_stu_signin (stu_openid,gmt_create,gmt_modify,signin_num)" +
            " values (#{userSignIn.openId} ,#{userSignIn.gmtCreate} ,#{userSignIn.gmtModify} ,#{userSignIn.signInNum} )")
    int insertSignInRecord(@Param("userSignIn") UserSignIn userSignIn);

    @Select("select signin_num from tb_stu_signin where stu_openid = #{openid} ")
    List<String> selectSignInInfoByOpenId(String openid);
}
