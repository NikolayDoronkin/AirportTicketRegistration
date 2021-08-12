package com.nikolay.doronkin.businessengine.repository;

import com.nikolay.doronkin.businessengine.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {}
