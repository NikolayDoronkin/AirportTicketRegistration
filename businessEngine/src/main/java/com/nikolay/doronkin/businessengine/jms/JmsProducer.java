package com.nikolay.doronkin.businessengine.jms;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigJmsProperties;
import com.nikolay.doronkin.starter.AuditMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsProducer {

    private final JmsTemplate jmsTemplate;
    private final ConfigJmsProperties configJmsProperties;

    public void sendMessage(AuditMessage message){
        try {
            log.info("Attempting Send message to Topic: " + configJmsProperties.getTopic());
            jmsTemplate.convertAndSend(configJmsProperties.getTopic(), message);
        } catch (Exception e) {
            log.error("Received Exception during send Message: ", e);
        }
    }

}
