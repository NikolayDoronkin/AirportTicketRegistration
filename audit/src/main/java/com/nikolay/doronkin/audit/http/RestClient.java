package com.nikolay.doronkin.audit.http;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> getBusinessEngineResponse(@RequestBody(required = false)
                                                           final URI uri,
                                                           final HttpMethod httpMethod,
                                                           final HttpEntity<String> httpEntity,
                                                           ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(uri, httpMethod, httpEntity, responseType);
    }
}
