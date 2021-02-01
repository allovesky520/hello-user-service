package com.springboot.demo.annotation.sysLog.aspect;

import java.lang.annotation.*;

/**
 * 接口日志注解专用
 * @author zihui
 * @date 2021-01-10 16:04:02
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
