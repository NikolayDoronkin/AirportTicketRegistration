package com.nikolay.doronkin.businessengine.configuration;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigJmsProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.ConnectionFactory;

@Getter
@EnableJms
@Configuration
@AllArgsConstructor
public class JmsConfig {

    private final ConfigJmsProperties configJmsProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(configJmsProperties.getBrokerUrl());
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}
