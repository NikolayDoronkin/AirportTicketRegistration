package com.nikolay.doronkin.businessengine.controller;

import com.nikolay.doronkin.businessengine.configuration.property.ConfigSecurityProperties;
import com.nikolay.doronkin.businessengine.dto.UserDto;
import com.nikolay.doronkin.businessengine.service.UserService;
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
@RequestMapping("/api/user")
@SecurityRequirement(name = "AirportTicketRegistration")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize(ConfigSecurityProperties.ADMIN_AND_USER)
    public ResponseEntity<UserDto> getUser(@Valid @PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ConfigSecurityProperties.ADMIN)
    @ApiOperation(value = "User's removal", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
