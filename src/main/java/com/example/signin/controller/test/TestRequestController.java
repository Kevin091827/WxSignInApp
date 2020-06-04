package com.example.signin.controller.test;

import com.example.signin.exception.AppException;
import com.example.signin.exception.ErrorCode;
import com.example.signin.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TestRequestController
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/request")
public class TestRequestController {

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/request")
    public AjaxResult request(){
        log.info(String.valueOf("--------> " + request == null));
        throw new AppException(ErrorCode.FAIL);
    }
}
