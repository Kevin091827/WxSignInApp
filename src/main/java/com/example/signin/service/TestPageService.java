package com.example.signin.service;

import com.example.signin.util.AjaxResult;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:TestPageService
 * @Description: TODO
 */
public interface TestPageService {

    AjaxResult page(int pageNo, int pageSize);
}
