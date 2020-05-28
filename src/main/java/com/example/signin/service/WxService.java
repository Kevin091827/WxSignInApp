package com.example.signin.service;

import com.example.signin.util.AjaxResult;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:WxService
 * @Description: TODO
 */
public interface WxService {

    AjaxResult wxLogin(String code, String encryptedData, String iv);
}
