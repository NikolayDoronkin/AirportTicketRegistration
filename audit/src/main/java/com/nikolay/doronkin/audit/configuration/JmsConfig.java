package com.nikolay.doronkin.audit.configuration;

import com.nikolay.doronkin.audit.configuration.property.ConfigJmsProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Getter
@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    private final ConfigJmsProperty configJmsProperty;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(configJmsProperty.getBrokerUrl());
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory
                = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory());
        defaultJmsListenerContainerFactory.setPubSubDomain(true);
        return defaultJmsListenerContainerFactory;
    }
}
