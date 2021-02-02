package com.springboot.demo.properties;

import com.google.common.collect.Lists;
import io.swagger.annotations.Authorization;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zihui
 * @version 1.0
 * @title: SwaggerProperties
 * @description: TODO
 * @date 2021-01-14 10:39:21
 */
@Data
public class SwaggerProperties {

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "com.springboot.demo.domin";
    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = Lists.newArrayList();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = Lists.newArrayList();

    /**
     * 标题
     **/
    private String title = "API接口文档";
    /**
     * 描述
     **/
    private String description = "API接口文档，及相关接口的说明";
    /**
     * 版本
     **/
    private String version = "1.0.0";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * host信息
     **/
    private String host = "";
    /**
     * 联系人信息
     */
    private Contact contact = new Contact();

    /**
     * 全局统一鉴权配置
     **/
    private Authorization authorization = new Authorization();


    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "zihui";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";

    }

    @Data
    @NoArgsConstructor
    public static class Authorization {

        private String cliendId="zihui_web";

        private String clientSecret="zihui_web";

        /**
         * 鉴权策略ID，需要和SecurityReferences ID保持一致
         */
        private String name = "";

        /**
         * 需要开启鉴权URL的正则
         */
        private String authRegex = "^.*$";

        /**
         * 作用域名称
         */
        private String scope = "server";

        /**
         * 作用域描述
         */
        private String scopeDesc = "测试单点登录服务";

        private String tokenUrl = "/oauth/token";
    }
}
