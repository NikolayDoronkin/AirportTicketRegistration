package com.nikolay.doronkin.businessengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportDto {
    private Long id;
    private String name;
    private String city;
}
