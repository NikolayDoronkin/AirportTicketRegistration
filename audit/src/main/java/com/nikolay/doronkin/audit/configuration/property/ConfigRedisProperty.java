package com.nikolay.doronkin.audit.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConfigRedisProperty {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;
}
