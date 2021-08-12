package com.nikolay.doronkin.businessengine.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
public class FlightDto {
    private Long departureId;
    private Long arrivalId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
}
