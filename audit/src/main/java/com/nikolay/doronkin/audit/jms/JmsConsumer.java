package com.nikolay.doronkin.audit.jms;

import com.nikolay.doronkin.audit.service.RedisMessageService;
import com.nikolay.doronkin.starter.AuditMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class JmsConsumer implements MessageListener {

    private final RedisMessageService redisMessageService;

    @Override
    @JmsListener(destination = "bridgingcode-queue")
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            AuditMessage auditMessage = (AuditMessage) objectMessage.getObject();
            redisMessageService.saveMessage(auditMessage);
            log.info("Received message: {}", auditMessage);
        } catch (JMSException e) {
            log.error("Received exception: {}", e.getMessage());
        }
    }
}
