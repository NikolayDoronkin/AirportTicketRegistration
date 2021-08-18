package com.nikolay.doronkin.audit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class AbstractIntegrationTest {

    private static final GenericContainer<?> redis;
    private static final String IMAGE = "redis:6.0.15";
    private static final Integer PORT = 6379;

    static{
        redis = new GenericContainer<>(DockerImageName.parse(IMAGE))
                .withExposedPorts(PORT)
                .withImagePullPolicy(PullPolicy.alwaysPull());
        redis.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", redis::getContainerIpAddress);
        registry.add("spring.redis.port",  () -> redis.getMappedPort(PORT));
    }

    @AfterAll
    static void afterAll() {
        redis.stop();
    }
}
