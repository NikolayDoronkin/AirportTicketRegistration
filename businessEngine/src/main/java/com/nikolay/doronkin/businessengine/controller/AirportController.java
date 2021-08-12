package com.nikolay.doronkin.businessengine.controller;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigSecurityProperties;
import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.dto.request.AirportRequest;
import com.nikolay.doronkin.businessengine.service.AirportService;
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
@RequestMapping(value = "/api/airport")
@SecurityRequirement(name = "AirportTicketRegistration")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/{id}")
    public ResponseEntity<AirportDto> getAirport(@PathVariable Long id) {
        AirportDto airport = airportService.findById(id);
        if (airport == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(airport, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirportDto>> getAll(){
        List<AirportDto> airports = airportService.findAll();
        return ResponseEntity.ok(airports);
    }

    @PostMapping("/create")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    @ApiOperation(value = "Airport's creation", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<AirportDto> create(@Valid @RequestBody AirportRequest airportRequest) {
        AirportDto airportDto = airportService.create(airportRequest);
        return new ResponseEntity<>(airportDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    @ApiOperation(value = "Airport's removal", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        airportService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

