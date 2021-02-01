package com.springboot.demo.annotation.sysLog.aspect;

import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * 系统切面，统一接口日志处理
 * @author zihui
 * @date 2021-01-10 16:01:11
 **/
@Aspect
@Component
@Data
public class SysLogAspect {

    cn.hutool.log.Log log = LogFactory.get();

    @Pointcut("@annotation(com.springboot.demo.annotation.sysLog.aspect.SysLog)")
    public void showLogs() {

    }

    private static final String notFoundMessage="No message found under #message# code for locale ";
    @Around("showLogs()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //  请求id，成对出现，用于标识同一请求的开始与结束
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        this.log.info("=================请求开始=================");
        this.log.info("请求id: {}",uuid);
        //  请求开始时间
        long beginTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);

        //  接口描述信息
        String desc = sysLog.value();
        //  请求连接
        this.log.info("请求连接: {}", request.getRequestURL().toString());
        //  接口描述信息
        this.log.info("接口描述信息: {}", desc);
        //  请求类型
        this.log.info("请求类型: {}", request.getMethod());
        //  请求方法
        this.log.info("请求方法: {}.{}", signature.getDeclaringTypeName(), signature.getName());
        //  请求IP
        this.log.info("请求IP: {}", request.getRemoteAddr());
        //  请求入参
//        this.log.info("前端请求入参: {}", JSONObject.toJSONString(point.getArgs()));
        this.log.info("请求入参: {}", Arrays.toString(point.getArgs()));

        this.log.info("请求头多语言和平台编码信息: {},{}", request.getHeader("Accept-Language"),request.getHeader("Platform-Code"));

        //执行方法
        Object result = point.proceed();
        //请求结束时间
        long endTime = System.currentTimeMillis();
        //请求耗时
        this.log.info("请求耗时: {}ms", endTime - beginTime);
        //请求返回
        this.log.info("请求返回: {}", JSONObject.toJSONString(result));
        this.log.info("请求id: {}",uuid);
        this.log.info("=================请求结束=================");

        return result;
    }



}
