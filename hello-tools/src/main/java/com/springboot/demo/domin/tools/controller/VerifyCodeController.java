package com.springboot.demo.domin.tools.controller;

import com.springboot.demo.annotation.sysLog.aspect.SysLog;
import com.springboot.demo.domin.tools.service.VerifyCodeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/verifyCode")
@Api(value = "VerifyCodeController", tags = "验证码", description = "验证码工具")
public class VerifyCodeController {

    @Autowired
    VerifyCodeService verifyCodeService;

    @SysLog("获取图形验证码")
    @GetMapping(value = "")
    public void getVerifyCode(){
        verifyCodeService.getVerifyCode();
    }

}
