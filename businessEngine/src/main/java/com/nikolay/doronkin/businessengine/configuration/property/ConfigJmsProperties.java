package com.nikolay.doronkin.businessengine.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConfigJmsProperties {

    @Value("${spring.activemq.topic}")
    private String topic;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
}
