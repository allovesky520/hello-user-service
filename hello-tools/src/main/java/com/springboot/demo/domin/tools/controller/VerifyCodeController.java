package com.springboot.demo.domin.tools.controller;

import com.springboot.demo.annotation.sysLog.aspect.SysLog;
import com.springboot.demo.constant.Result;
import com.springboot.demo.domin.tools.service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/verifyCode")
@Api(value = "VerifyCodeController", tags = "验证码", description = "验证码工具")
public class VerifyCodeController {

    @Autowired
    VerifyCodeService verifyCodeService;

    @SysLog("获取图形验证码")
    @ApiOperation(value = "获取图形验证码")
    @GetMapping(value = "")
    public void getVerifyCode(@RequestParam(name = "phone", value = "phone") String phone){
        verifyCodeService.getVerifyCode(phone);
    }

//    @ApiOperation(value = "校验随机验证码")
//    @PostMapping(value = "/check")
//    public Result checkVerifyCode(@RequestBody VerifyCode input) {
//
//    }
}
