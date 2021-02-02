package com.springboot.demo.domin.tools.service.impl;

import cn.hutool.captcha.LineCaptcha;
import com.springboot.demo.domin.tools.service.VerifyCodeService;
import com.springboot.demo.util.ValidateCodeImpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Override
    public void getVerifyCode() {
//        LineCaptcha lineCaptcha = new LineCaptcha(200, 100, 4, 150);
//        ValidateCodeImpUtil.buildImageRes(lineCaptcha.getImageBytes());

        ValidateCodeImpUtil validatecode = new ValidateCodeImpUtil(80,26,40,4);
        System.out.println(validatecode.getCode());
        ValidateCodeImpUtil.buildImageRes(validatecode.getBuffImgBytes());

    }
}
