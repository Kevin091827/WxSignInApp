package com.example.signin.service;

import com.example.signin.util.AjaxResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:SignInService
 * @Description: TODO
 */
public interface SignInService {

    AjaxResult inputSignCourse(String teaId,
                               String courseAddr,
                               String courseName,
                               String courseTime,
                               String courseStanza);

    AjaxResult requestSignIn(long id, String signNum,String openId);

    AjaxResult responseSignIn(String stuId, String signNum, String gpsInfo,String openId);

    AjaxResult selectSignRecord(String type, String openid);

    AjaxResult signInNow(String signNum,long id);

    AjaxResult deleteRecordById(long id);

    AjaxResult exportExcel(HttpServletResponse response,String teaId);
}
