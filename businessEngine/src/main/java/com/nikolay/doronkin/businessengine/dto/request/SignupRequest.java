package com.nikolay.doronkin.businessengine.dto.request;

import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Username cannot be empty")
    private final String userName;

    @NotBlank(message = "Surname cannot be empty")
    private final String surName;

    @NotBlank(message = "Lastname cannot be empty")
    private final String lastName;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private final String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 16, message = "The password must be equal or greater than 6 characters and less than 16 characters")
    private final String password;

    @NotBlank(message = "Phone number cannot be empty")
    @Size(min = 11, max = 11, message = "The phone number must be equal or greater than 9 characters and less than 9 characters")
    private final String phoneNumber;

    @NotNull(message = "User's role cannot be empty")
    private final UserRole userRole;

    @NotNull(message = "User's status cannot be empty")
    private final UserStatus userStatus;


}
