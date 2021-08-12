package com.nikolay.doronkin.businessengine.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "UserName cannot be empty")
    private final String userName;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 16, message = "The password must be equal or greater than 6 characters and less than 16 characters")
    private final String password;
}
