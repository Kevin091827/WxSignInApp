package com.example.signin.controller.test;

import com.alibaba.fastjson.JSONObject;
import com.example.signin.util.AjaxResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:TestRequestBodyController
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestRequestBodyController {

    @RequestMapping("/test")
    public AjaxResult testRequestBody(@RequestBody Test test){
        log.info("-------------");
        log.info(test.getId());
        log.info(test.getName());
        return new AjaxResult().ok(test);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Test{
        private String id;
        private String name;
    }

    public static void main(String[] args) {
        String s = JSONObject.toJSONString(new Test("123456","kevin"));
        log.info(s);
    }
}
