package com.yyzh.user.api.feign.fallback;

import com.yyzh.user.api.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RemoteUserServiceFallback implements RemoteUserService{

    private Throwable cause;

    public RemoteUserServiceFallback(Throwable throwable) {
        this.cause = throwable;
    }

    @Override
    public String hello(String input) {
        log.warn("RemoteUserService调用hello失败：{},cause:{}", input, cause);
        return null;
    }
}
