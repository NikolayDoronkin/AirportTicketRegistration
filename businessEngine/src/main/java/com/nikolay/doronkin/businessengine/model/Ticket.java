package com.nikolay.doronkin.businessengine.model;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
@EqualsAndHashCode(callSuper = true)
public class Ticket extends Model{

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, insertable = false, updatable = false)
    private Flight flight;

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        this.flightId = flight != null ? flight.getId() : null;
    }
}
