package com.example.signin.exception;

import lombok.Data;

/**
 * @Auther: Kevin
 * @Date:
 * @EnumName:ErrorCode
 * @Description: TODO
 */
public enum ErrorCode {
    OK(200,"OK"),
    FAIL(500,"ERROR");

    private int code;
    private String errorMsg;
    ErrorCode(int code,String errorMsg){
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getCode(){
        return code;
    }
}
