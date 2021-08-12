package com.nikolay.doronkin.businessengine.model;

import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@EqualsAndHashCode(callSuper = true)
public class User extends Model{

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String surName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole roles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;
}
