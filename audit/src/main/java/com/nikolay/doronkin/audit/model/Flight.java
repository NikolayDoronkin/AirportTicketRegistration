package com.nikolay.doronkin.audit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
public class Flight {

    @Id
    private Long id;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Airport departureAirport;
    private Airport arrivalAirport;
}
