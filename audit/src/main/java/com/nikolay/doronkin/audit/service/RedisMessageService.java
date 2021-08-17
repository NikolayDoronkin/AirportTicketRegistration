package com.nikolay.doronkin.audit.service;

import com.nikolay.doronkin.audit.repository.AuditMessageRepository;
import com.nikolay.doronkin.starter.AuditMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMessageService {

    private final AuditMessageRepository auditMessageRepository;

    public void saveMessage(AuditMessage auditMessage) {
        auditMessageRepository.save(auditMessage);
    }
}
