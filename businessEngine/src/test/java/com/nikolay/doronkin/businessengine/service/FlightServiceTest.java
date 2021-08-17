package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.dto.FlightDto;
import com.nikolay.doronkin.businessengine.dto.request.FlightRequest;
import com.nikolay.doronkin.businessengine.mapper.AirportDtoToAirportEntityMapper;
import com.nikolay.doronkin.businessengine.mapper.FlightDtoToFlightEntityMapper;
import com.nikolay.doronkin.businessengine.model.Airport;
import com.nikolay.doronkin.businessengine.model.Flight;
import com.nikolay.doronkin.businessengine.repository.AirportRepository;
import com.nikolay.doronkin.businessengine.repository.FlightRepository;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Mock
    private AirportService airportService;

    @Spy
    private FlightDtoToFlightEntityMapper flightMapper = Mappers.getMapper(FlightDtoToFlightEntityMapper.class);

    @Spy
    private AirportDtoToAirportEntityMapper airportMapper = Mappers.getMapper(AirportDtoToAirportEntityMapper.class);

    private Flight flight;
    private FlightDto expectedFlightDto;
    private FlightRequest flightRequest;
    private List<Flight> flights;
    private List<FlightDto> expectedFlightsDto;

    private AirportDto firstAirportDto;
    private Airport firstAirport;

    private AirportDto secondAirportDto;
    private Airport secondAirport;

    private static final Long ID = 0L;
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String NAME_1 = "TEST_NAME_1";
    private static final String NAME_2 = "TEST_NAME_2";

    private static final String CITY_1 = "TEST_CITY_1";
    private static final String CITY_2 = "TEST_CITY_2";

    @BeforeEach
    void setUp() {
        firstAirport = Airport.builder()
                .name(NAME_1)
                .city(CITY_1)
                .incomingFlights(new ArrayList<>())
                .outcomingFlights(new ArrayList<>())
                .build();
        firstAirport.setId(ID_1);

        secondAirport = Airport.builder()
                .name(NAME_2)
                .city(CITY_2)
                .incomingFlights(new ArrayList<>())
                .outcomingFlights(new ArrayList<>())
                .build();
        secondAirport.setId(ID_2);

        flight = Flight.builder()
                .departureId(ID_1)
                .arrivalId(ID_2)
                .departureAirport(firstAirport)
                .arrivalAirport(secondAirport)
                .build();
        flight.setId(ID);

        firstAirportDto = new AirportDto(ID_1, NAME_1, CITY_1);
        secondAirportDto = new AirportDto(ID_2, NAME_2, CITY_2);

        expectedFlightDto = new FlightDto();
        expectedFlightDto.setDepartureId(ID_1);
        expectedFlightDto.setArrivalId(ID_2);

        flights = List.of(flight);
        expectedFlightsDto = List.of(expectedFlightDto);

        flightRequest = FlightRequest.builder()
                .departureId(ID_1)
                .arrivalId(ID_2)
                .build();
    }

    @Test
    void testFindByIdSuccess() {
        Mockito.when(flightRepository.findById(ID)).thenReturn(Optional.of(flight));

        FlightDto actualFlightDto = flightService.findById(ID);

        Assertions.assertEquals(expectedFlightDto, actualFlightDto);
    }

    @Test
    void testFindByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            flightService.findById(ID);
        });
    }

    @Test
    void testFindAll() {
        Mockito.when(flightRepository.findAll()).thenReturn(flights);

        List<FlightDto> actualFlightsDto = flightService.findAll();

        Assertions.assertEquals(expectedFlightsDto, actualFlightsDto);
    }

    @Test
    void testDeleteByIdSuccess() {
        Mockito.doReturn(true).when(flightRepository).existsById(ID);

        flightService.deleteById(ID);

        Mockito.verify(flightRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                flightService.deleteById(ID)
        );
    }

    @Test
    void testCreateSuccess() {
        Mockito.when(airportService.findById(ID_1)).thenReturn(firstAirportDto);
        Mockito.when(airportService.findById(ID_2)).thenReturn(secondAirportDto);

        FlightDto actualFlightDto = flightService.create(flightRequest);

        Assertions.assertEquals(expectedFlightDto, actualFlightDto);
    }

    @Test
    void testCreateFailure() {
        Mockito.when(airportService.findById(ID_1)).thenThrow(new EntityNotFoundException());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            flightService.create(flightRequest);
        });
    }
}