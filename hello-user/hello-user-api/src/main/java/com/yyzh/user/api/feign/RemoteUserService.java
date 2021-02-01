package com.yyzh.user.api.feign;

import com.yyzh.user.api.feign.base.FeignConfigurationAdapter;
import com.yyzh.user.api.feign.fallback.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hello-user",//   注册到eureka服务上的名字
        url = "${feign.server.name.user.url:}",//   可以指定地址调用服务
        configuration = FeignConfigurationAdapter.class,
        fallbackFactory = RemoteUserServiceFallbackFactory.class)
@RequestMapping(path = "/v1/user")//    目标服务中拼接接口
public interface RemoteUserService {

    @GetMapping(value = "/hello")
    String hello(@RequestParam("str") String str);

}
