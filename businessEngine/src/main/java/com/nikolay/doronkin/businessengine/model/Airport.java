package com.nikolay.doronkin.businessengine.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "airport")
@EqualsAndHashCode(callSuper = true)

public class Airport extends Model{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "departureAirport")
    private List<Flight> outcomingFlights;

    @OneToMany(mappedBy = "arrivalAirport")
    private List<Flight> incomingFlights;
}
