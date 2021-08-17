package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.dto.request.AirportRequest;
import com.nikolay.doronkin.businessengine.exception.ExceptionMessages;
import com.nikolay.doronkin.businessengine.mapper.AirportDtoToAirportEntityMapper;
import com.nikolay.doronkin.businessengine.model.Airport;
import com.nikolay.doronkin.businessengine.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportDtoToAirportEntityMapper airportMapper;

    public AirportDto findById(Long id) {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.NO_SUCH_ELEMENT_BY_ID)
        );
        return airportMapper.airportEntityToAirportDto(airport);
    }

    public List<AirportDto> findAll() {
        List<Airport> airports = airportRepository.findAll();
        return airports.stream()
                .map(airportMapper::airportEntityToAirportDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessages.AIRPORT_NOT_FOUND_WITH_ID + id);
        }
        airportRepository.deleteById(id);
    }

    @Transactional
    public AirportDto create(AirportRequest airportRequest) {
        if(airportRepository.existsAirportByName(airportRequest.getName())){
            throw new EntityExistsException(ExceptionMessages.ENTITY_EXISTS_BY_NAME + airportRequest.getName());
        }
        Airport createdAirport = Airport.builder()
                .name(airportRequest.getName())
                .city(airportRequest.getCity())
                .build();

        airportRepository.save(createdAirport);
        return airportMapper.airportEntityToAirportDto(createdAirport);
    }
}
