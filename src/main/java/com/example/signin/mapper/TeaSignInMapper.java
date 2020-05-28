package com.example.signin.mapper;

import com.example.signin.entity.UserSignIn;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:TeaSignInMapper
 * @Description: TODO
 */
@Mapper
public interface TeaSignInMapper {

    @Insert("insert into tb_tea_signin (tea_openid,gmt_create,gmt_modify,signin_num)" +
            " values (#{userSignIn.openId} ,#{userSignIn.gmtCreate} ,#{userSignIn.gmtModify} ,#{userSignIn.signInNum} )")
    int insertSignInRecord(@Param("userSignIn") UserSignIn userSignIn);

    @Select("select signin_num from tb_tea_signin where tea_openid = #{openid} ")
    List<String> selectSignInInfoByOpenId(String openid);

    @Select("select signin_num from tb_tea_signin where id = #{id} ")
    String selectSignInById(long id);

    @Delete("delete from tb_tea_signin where id = #{id} ")
    int deleteRecordById(long id);

    @Delete("delete from tb_tea_signin where signin_num = #{signInNum} ")
    int deleteRecordByNum(String signInNum);
}
