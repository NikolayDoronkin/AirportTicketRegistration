package com.nikolay.doronkin.businessengine.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight")
@EqualsAndHashCode(callSuper = true)

public class Flight extends Model{

    @Column(nullable = false)
    private Long departureId;

    @Column(nullable = false)
    private Long arrivalId;

    @Column(nullable = false)
    private LocalDateTime departureDate;

    @Column(nullable = false)
    private LocalDateTime arrivalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    private Airport arrivalAirport;

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
        this.departureId = departureAirport != null ? departureAirport.getId() : null;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        this.arrivalId = arrivalAirport != null ? arrivalAirport.getId() : null;
    }
}
