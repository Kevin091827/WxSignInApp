package com.example.signin.service;

import com.example.signin.util.AjaxResult;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:InfoService
 * @Description: TODO
 */
public interface InfoService {

    AjaxResult improveTeaInfo(String type,
                              String school,
                              String name,
                              String collage,
                              String accountId,
                              String phone,
                              String email,
                              String openId);

    AjaxResult improveStuInfo(String type,
                              String school,
                              String name,
                              String collage,
                              String accountId,
                              String phone,
                              String email,
                              String grade,
                              String stuClass,
                              String openId,
                              String major);

    AjaxResult selectTeaInfo(String accountId, String school);

    AjaxResult selectStuInfo(String accountId, String school);

    AjaxResult isNeedImprove(String openId,String type);
}
