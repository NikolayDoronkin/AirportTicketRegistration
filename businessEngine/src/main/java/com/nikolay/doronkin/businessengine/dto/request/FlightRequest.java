package com.nikolay.doronkin.businessengine.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class FlightRequest {

    @NotNull(message = "Departure Id cannot be empty")
    private final Long departureId;

    @NotNull(message = "Arrival id cannot be empty")
    private final Long arrivalId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Departure date cannot be empty")
    private final LocalDateTime departureDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Arrival date cannot be empty")
    private final LocalDateTime arrivalDate;
}
