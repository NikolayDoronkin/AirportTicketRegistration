package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.dto.request.AirportRequest;
import com.nikolay.doronkin.businessengine.mapper.AirportDtoToAirportEntityMapper;
import com.nikolay.doronkin.businessengine.model.Airport;
import com.nikolay.doronkin.businessengine.repository.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @Spy
    private AirportDtoToAirportEntityMapper airportMapper = Mappers.getMapper(AirportDtoToAirportEntityMapper.class);

    @InjectMocks
    private AirportService airportService;

    private Airport airport;
    private AirportDto expectedAirportDto;
    private AirportRequest airportRequest;
    private List<Airport> airports;
    private List<AirportDto> expectedAirportsDto;

    private static final Long ID = 1L;
    private static final String NAME = "TEST_NAME";
    private static final String CITY = "TEST_CITY";


    @BeforeEach
    void setUp() {
        airport = Airport.builder()
                .name(NAME)
                .city(CITY)
                .incomingFlights(new ArrayList<>())
                .outcomingFlights(new ArrayList<>())
                .build();
        airport.setId(ID);

        expectedAirportDto = new AirportDto(ID, NAME, CITY);
        airports = List.of(airport);

        expectedAirportsDto = List.of(expectedAirportDto);

        airportRequest = new AirportRequest(NAME, CITY);
    }

    @Test
    void testFindByIdSuccess() {
        Mockito.when(airportRepository.findById(ID)).thenReturn(Optional.of(airport));

        AirportDto actualAirportDto = airportService.findById(ID);

        Assertions.assertEquals(expectedAirportDto, actualAirportDto);
    }

    @Test
    void testFindByIdFailure(){
            Assertions.assertThrows(EntityNotFoundException.class, () -> {
                airportService.findById(ID);
            });
    }

    @Test
    void testFindAll() {
        Mockito.when(airportRepository.findAll()).thenReturn(airports);

        List<AirportDto> actualAirportsDto = airportService.findAll();

        Assertions.assertEquals(expectedAirportsDto, actualAirportsDto);
    }

    @Test
    void testDeleteByIdSuccess() {
        Mockito.doReturn(true).when(airportRepository).existsById(ID);

        airportService.deleteById(ID);

        Mockito.verify(airportRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                airportService.deleteById(ID)
        );
    }

    @Test
    void testCreateSuccess() {
        AirportDto actualAirportDto = airportService.create(airportRequest);

        Assertions.assertEquals(expectedAirportDto.getName(), actualAirportDto.getName());
        Assertions.assertEquals(expectedAirportDto.getCity(), actualAirportDto.getCity());
    }

    @Test
    void testCreateFailure() {
        Mockito.when(airportRepository.existsAirportByName(airportRequest.getName())).thenReturn(true);

        Assertions.assertThrows(EntityExistsException.class, () -> {
           airportService.create(airportRequest);
        });
    }
}