package com.springboot.demo.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.springboot.demo.properties.RootProperties;
import com.springboot.demo.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @author zihui
 * @version 1.0
 * @title: SwaggerConfiguration
 * @description: TODO
 * @date 2021-01-14 11:29:49
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String DEFAULT_EXCLUDE_PATH = "/error";
    private static final String BASE_PATH = "/**";

    @Autowired
    private RootProperties properties;

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     * 在构建文档的时候过滤哪些API接口
     * @return
     */
    @Bean //作为bean纳入spring容器
    public Docket api() {
        SwaggerProperties swaggerProperties = properties.getSwagger();
        // base-path处理
        if (CollectionUtils.isEmpty(swaggerProperties.getBasePath())) {
            swaggerProperties.getBasePath().add(BASE_PATH);
        }
        List<Predicate<String>> basePath = Lists.newArrayList();
        swaggerProperties.getBasePath().forEach(path -> basePath.add(PathSelectors.ant(path)));

        // exclude-path处理
        if (CollectionUtils.isEmpty(swaggerProperties.getExcludePath())) {
            swaggerProperties.getExcludePath().add(DEFAULT_EXCLUDE_PATH);
        }
        List<Predicate<String>> excludePath = Lists.newArrayList();
        swaggerProperties.getExcludePath().forEach(path -> excludePath.add(PathSelectors.ant(path)));
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))// 只显示添加了@Api注解的类
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))//  只显示指定包下的API
                .paths(PathSelectors.any())//   所有路径下都可以扫描
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))//哪些路径需要过滤，哪些路径可以扫描
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
                .pathMapping("/")
                ;
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     * @return
     */
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties){
        return  new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    /**
     * 用来解释授权详情
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    /**
     * 我们需要为我们的示例API定义一个安全上下文
     * @return
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
