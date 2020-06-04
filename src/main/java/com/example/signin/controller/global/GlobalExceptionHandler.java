package com.example.signin.controller.global;

import com.alibaba.fastjson.JSON;
import com.example.signin.exception.AppException;
import com.example.signin.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:GlobalExceptionHandler
 * @Description: TODO
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public AjaxResult handle(HttpServletRequest request, AppException ex){
        String path = request.getServletPath();
        log.error("------->" + path + "exception msg : " + ex.getErrorMsg());
        return new AjaxResult(ex.getErrorCode(),ex.getErrorMsg());
    }
}
