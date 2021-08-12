package com.nikolay.doronkin.businessengine.controller;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigSecurityProperties;
import com.nikolay.doronkin.businessengine.dto.FlightDto;
import com.nikolay.doronkin.businessengine.dto.request.FlightRequest;
import com.nikolay.doronkin.businessengine.service.FlightService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/flight")
@SecurityRequirement(name = "AirportTicketRegistration")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlight(@Valid @PathVariable Long id) {
        FlightDto flightDto = flightService.findById(id);
        return new ResponseEntity<>(flightDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightDto>> getAll() {
        List<FlightDto> flights = flightService.findAll();
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/create")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    @ApiOperation(value = "Flight's removal", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<FlightDto> create(@Valid @RequestBody FlightRequest flightRequest) {
        FlightDto flightDto = flightService.create(flightRequest);
        return new ResponseEntity<>(flightDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    @ApiOperation(value = "Flight's removal", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        flightService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
