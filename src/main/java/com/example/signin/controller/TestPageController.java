package com.example.signin.controller;

import com.example.signin.service.TestPageService;
import com.example.signin.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TestPageController
 * @Description: TODO
 */
@RequestMapping("/page")
@RestController
@CrossOrigin
public class TestPageController {

    @Autowired
    private TestPageService testPageService;

    @RequestMapping("/page")
    public AjaxResult selectByPage(@RequestParam("pageNo") int pageNo,
                                   @RequestParam("pageSize") int pageSize){

        return testPageService.page(pageNo,pageSize);
    }

}
