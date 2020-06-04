package com.example.signin.exception;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:AppException
 * @Description: TODO
 */
@Data
public class AppException extends RuntimeException {

    private ErrorCode errorCode;
    private String errorMsg;

    public AppException(ErrorCode errorCode){
        this.errorCode = errorCode;
        if(!StringUtils.isBlank(errorCode.getErrorMsg())){
            this.errorMsg = errorCode.getErrorMsg();
        }
    }

    public AppException(ErrorCode code,String errorMsg){
        this.errorMsg = errorMsg;
        this.errorCode = code;
    }
}
