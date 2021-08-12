package com.nikolay.doronkin.starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("AuditMessage")
public class AuditMessage implements Serializable {

    @Id
    private String userEmail;
    private String endpointName;
    private LocalDateTime timestamp;
    private int statusCode;
}

