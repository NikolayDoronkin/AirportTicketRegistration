package com.nikolay.doronkin.audit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.doronkin.audit.AbstractIntegrationTest;
import com.nikolay.doronkin.audit.http.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

@Testcontainers
@WebMvcTest(FlightController.class)
class FlightControllerTest extends AbstractIntegrationTest {

    @MockBean private FlightController flightController;
    @MockBean private RestClient restClient;
    @Mock private RestTemplate restTemplate;
    @Mock private ObjectMapper objectMapper;
    @Mock private Jedis jedis;

    private static final String KEY = "AuditMessage:nicolaydm@gmail.com";
    private static final String ENDPOINT_NAME = "http://localhost:8081/api/flight/all";
    private static final String USER_EMAIL = "nicolaydm@gmail.com";
    private static final String CLASS = "com.nikolay.doronkin.starter.AuditMessage";
    private static final String STATUS_CODE = "200";
    private static final String TIMES_TAMP = "2021-08-17T15:36:46.421954388";

    private Map<String, String> expectedMap;


    @BeforeEach
    void setUpRedisClient() {
        restTemplate = new RestTemplate();
        restClient = new RestClient(restTemplate);
        objectMapper = new ObjectMapper();
        jedis = new Jedis();
        flightController = new FlightController(restClient, objectMapper, jedis);
        expectedMap = new HashMap<>();
        expectedMap.put("endpointName", ENDPOINT_NAME);
        expectedMap.put("userEmail", USER_EMAIL);
        expectedMap.put("_class", CLASS);
        expectedMap.put("statusCode", STATUS_CODE);
        expectedMap.put("timestamp", TIMES_TAMP);
    }

    @Test
    void testGetAllFlightsSuccess() {
        Map<String, String> actualMap = jedis.hgetAll(KEY);

        Assertions.assertEquals(expectedMap, actualMap);
    }
}
