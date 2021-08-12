package com.nikolay.doronkin.businessengine.repository;

import com.nikolay.doronkin.businessengine.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {}
