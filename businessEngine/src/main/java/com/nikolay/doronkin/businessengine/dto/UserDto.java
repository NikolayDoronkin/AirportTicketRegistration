package com.nikolay.doronkin.businessengine.dto;

import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String surName;
    private String lastName;
    private String email;
    private String password;
    private Long phoneNumber;
    private UserRole roles;
    private UserStatus status;
    private List<TicketDto> tickets = new ArrayList<>();
}
