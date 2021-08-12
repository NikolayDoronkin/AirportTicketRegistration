package com.nikolay.doronkin.businessengine.controller;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigSecurityProperties;
import com.nikolay.doronkin.businessengine.dto.TicketDto;
import com.nikolay.doronkin.businessengine.jwt.JwtUser;
import com.nikolay.doronkin.businessengine.service.TicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/ticket")
@PreAuthorize(ConfigSecurityProperties.ADMIN_AND_USER)
@SecurityRequirement(name = "AirportTicketRegistration")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long id) {
        TicketDto ticketDto = ticketService.findById(id);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation(value = "All tickets", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<List<TicketDto>> getAll() {
        List<TicketDto> flights = ticketService.findAll();
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Ticket's creation", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<TicketDto> create(@AuthenticationPrincipal JwtUser user,
                                                 @Valid @RequestParam Long flightId) {
        TicketDto ticketDto = ticketService.create(user, flightId);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Ticket's removal", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        ticketService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}