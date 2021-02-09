package com.springboot.demo.domin.tools.service.impl;

import com.springboot.demo.constant.PasswordConstant;
import com.springboot.demo.domin.tools.service.VerifyCodeService;
import com.springboot.demo.util.Base64Utils;
import com.springboot.demo.util.Md5Util;
import com.springboot.demo.util.ValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    @Qualifier(value = "ssoRedisTemplate")
    private RedisTemplate redisTemplate;

    @Value(value = "#{T(java.lang.Integer).parseInt('${sso.forgot_verify_code_effect_mins:5}')}")
    private Integer verifyCodeEffectMins;

    private final String prefix = "verify_code_";

    @Override
    public void getVerifyCode(String phone) {
//        LineCaptcha lineCaptcha = new LineCaptcha(200, 100, 4, 150);
//        ValidateCodeUtil.buildImageRes(lineCaptcha.getImageBytes());

        ValidateCodeUtil validateCode = new ValidateCodeUtil(80,26,40,4);
        String encode = Md5Util.encryptMD5(Base64Utils.encode(validateCode.getCode().toUpperCase()));
        //  获取请求头中内容，比如浏览器信息
        HttpServletRequest request = null;
        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //  可以考虑加盐增加复杂度防破解
        String uuidCode = Md5Util.encryptMD5(Base64Utils.encode(request.getHeader("User-Agent").toUpperCase()));
        //  拼接key
        String key = getVerifyKey(phone, uuidCode, PasswordConstant.VERIFY_CODE_TYPE_REGISTER);

        //将生成的随机码存储到Redis中
        redisTemplate.opsForValue().set(key, encode, verifyCodeEffectMins, TimeUnit.MINUTES);
        log.info("redis验证码{}",redisTemplate.opsForValue().get(key));
        ValidateCodeUtil.buildImageRes(validateCode.getBuffImgBytes());

    }

    /**
     *
     * @param phone
     * @param userAgent
     * @param verifyType
     * @return
     */
    private String getVerifyKey(String phone, String userAgent, String verifyType) {
        final StringBuffer buffer = new StringBuffer(this.prefix);
        buffer.append(phone)
                .append("_")
                .append(userAgent)
                .append("_")
                .append(verifyType);
        return buffer.toString();
    }
}
