package com.springboot.demo.domin.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyzh
 * @version 1.0
 * @title: BaseController
 * @description: TODO
 * @date 2021-02-18 17:39:12
 */
@RestController
@RequestMapping("/lang")
@Api(value = "系统基础服务接口", tags = "系统基础服务接口")
public class BaseController {

    /**
     * 语言切换
     * <ui>
     *     <li>en_US</li>
     *     <li>zh_CN</li>
     *     <li>zh_TW</li>
     * <ui/>
     * @return
     */
    @PutMapping("/{lang}")
    @ApiOperation(value = "多语言切换", notes = "<ui>" +
            "<li>英文：en_US</li>" +
            "<li>简体中文：zh_CN</li>" +
            "<li>繁体中文：zh_TW</li>" +
            "<ui/>")
    public String lang(@PathVariable(name = "lang") String lang) {
        //        Locale l1 = LocaleContextHolder.getLocale();
        //        LocaleContextHolder.setLocale(Locale.US);
        return "OK";
    }


}
