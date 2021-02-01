package com.yyzh.user.api.feign.fallback;

import com.yyzh.user.api.feign.RemoteUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable) {
        log.warn("RemoteUserService服务fallback:case:{}", throwable.getMessage());
        return new RemoteUserServiceFallback(throwable);
    }

}
