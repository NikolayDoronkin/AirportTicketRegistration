package com.nikolay.doronkin.businessengine.repository;

import com.nikolay.doronkin.businessengine.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {}
