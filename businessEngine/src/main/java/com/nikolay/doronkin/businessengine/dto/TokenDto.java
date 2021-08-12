package com.nikolay.doronkin.businessengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String userName;
    private String token;
}
