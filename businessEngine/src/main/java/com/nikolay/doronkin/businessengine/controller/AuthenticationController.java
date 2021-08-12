package com.nikolay.doronkin.businessengine.controller;

import com.nikolay.doronkin.businessengine.dto.TokenDto;
import com.nikolay.doronkin.businessengine.dto.request.AuthenticationRequest;
import com.nikolay.doronkin.businessengine.dto.request.SignupRequest;
import com.nikolay.doronkin.businessengine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@Valid @RequestBody AuthenticationRequest requestDto) {
        TokenDto tokenDto = userService.authorization(requestDto.getUserName(), requestDto.getPassword());
        if(tokenDto == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<TokenDto> signUp(@Valid @RequestBody SignupRequest requestDto) {
        userService.register(requestDto);
        TokenDto tokenDto = userService.authorization(requestDto.getUserName(), requestDto.getPassword());
        if(tokenDto == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tokenDto, HttpStatus.CREATED);
    }
}
