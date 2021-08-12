package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.dto.FlightDto;
import com.nikolay.doronkin.businessengine.dto.request.FlightRequest;
import com.nikolay.doronkin.businessengine.exception.ExceptionMessages;
import com.nikolay.doronkin.businessengine.mapper.AirportDtoToAirportEntityMapper;
import com.nikolay.doronkin.businessengine.mapper.FlightDtoToFlightEntityMapper;
import com.nikolay.doronkin.businessengine.model.Flight;
import com.nikolay.doronkin.businessengine.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightService {

    private final AirportService airportService;
    private final FlightRepository flightRepository;
    private final FlightDtoToFlightEntityMapper flightMapper;
    private final AirportDtoToAirportEntityMapper airportMapper;

    public FlightDto findById(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.NO_SUCH_ELEMENT_BY_ID)
        );
        return flightMapper.flightEntityToFlightDto(flight);
    }

    public List<FlightDto> findAll() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(flightMapper::flightEntityToFlightDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessages.FLIGHT_NOT_FOUND_WITH_ID + id);
        }
        flightRepository.deleteById(id);
    }

    @Transactional
    public FlightDto create(FlightRequest flightRequest) {
        AirportDto departureAirport = airportService.findById(flightRequest.getDepartureId());
        AirportDto arrivalAirport = airportService.findById(flightRequest.getArrivalId());
        Flight flight = Flight.builder()
                .departureId(flightRequest.getDepartureId())
                .arrivalId(flightRequest.getArrivalId())
                .departureDate(flightRequest.getDepartureDate())
                .arrivalDate(flightRequest.getArrivalDate())
                .build();
        flight.setDepartureAirport(airportMapper.airportDtoToAirportEntity(departureAirport));
        flight.setArrivalAirport(airportMapper.airportDtoToAirportEntity(arrivalAirport));
        flightRepository.save(flight);
        return flightMapper.flightEntityToFlightDto(flight);
    }
}
