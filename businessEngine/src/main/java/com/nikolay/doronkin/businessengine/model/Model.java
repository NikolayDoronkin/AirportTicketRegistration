package com.nikolay.doronkin.businessengine.model;

import lombok.Data;
import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}
