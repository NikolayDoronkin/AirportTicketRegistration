package com.nikolay.doronkin.audit.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolay.doronkin.audit.http.RestClient;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import redis.clients.jedis.Jedis;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/flight")
public class FlightController {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final Jedis jedis;

    private static final String TOKEN = "Token";
    private static final String REQUEST_BODY = "";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String BUSINESS_URL = "http://localhost:8081";
    private static final String KEY = "AuditMessage:nicolaydm@gmail.com";

    @GetMapping("/all")
    public Map<String, String> getAllFlights() throws URISyntaxException {
        HttpServletRequest httpServletRequest = (
                (ServletRequestAttributes) Objects
                        .requireNonNull(
                                RequestContextHolder.getRequestAttributes()))
                .getRequest();
        URI uri = restClient.createURI(BUSINESS_URL, httpServletRequest);
        HttpHeaders httpHeaders = restClient.createHeader(CONTENT_TYPE_HEADER, httpServletRequest, AUTHORIZATION, TOKEN);
        HttpEntity<String> httpEntity = new HttpEntity<>(REQUEST_BODY, httpHeaders);
        ResponseEntity<List<Object>> allFlights
                = restClient
                .getBusinessEngineResponse(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<>() {}
                );
        objectMapper.convertValue(
                allFlights.getBody(),
                new TypeReference<>() {});
        return jedis.hgetAll(KEY);
    }
}
