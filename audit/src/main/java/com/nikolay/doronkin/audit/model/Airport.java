package com.nikolay.doronkin.audit.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data

public class Airport {

    @Id
    private Long id;
    private String name;
    private String city;
}
