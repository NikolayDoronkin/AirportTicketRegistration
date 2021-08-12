package com.nikolay.doronkin.businessengine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AirportRequest {

    @NotBlank(message = "Name cannot be empty")
    private final String name;

    @NotBlank(message = "Name cannot be empty")
    private final String city;
}
