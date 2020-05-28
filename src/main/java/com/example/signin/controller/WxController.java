package com.example.signin.controller;

import com.example.signin.service.WxService;
import com.example.signin.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:WxLogin
 * @Description: TODO
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private WxService wxService;

    @GetMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestParam("code") String code,
                              @RequestParam("encryptedData") String encryptedData,
                              @RequestParam("iv") String iv) {

        return wxService.wxLogin(code,encryptedData, iv);
    }

}
