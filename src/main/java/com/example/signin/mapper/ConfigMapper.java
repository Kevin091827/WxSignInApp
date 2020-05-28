package com.example.signin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:ConfigMapper
 * @Description: TODO
 */
@Mapper
public interface ConfigMapper {

    @Select("select current_week from tb_config")
    Integer selectCurrentWeek();
}
