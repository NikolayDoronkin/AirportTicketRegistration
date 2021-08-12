package com.nikolay.doronkin.audit.repository;

import com.nikolay.doronkin.starter.AuditMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditMessageRepository extends CrudRepository<AuditMessage, String> {}
