package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.TicketDto;
import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import com.nikolay.doronkin.businessengine.jwt.JwtUser;
import com.nikolay.doronkin.businessengine.mapper.TicketDtoToTicketEntityMapper;
import com.nikolay.doronkin.businessengine.model.Airport;
import com.nikolay.doronkin.businessengine.model.Flight;
import com.nikolay.doronkin.businessengine.model.Ticket;
import com.nikolay.doronkin.businessengine.model.User;
import com.nikolay.doronkin.businessengine.repository.FlightRepository;
import com.nikolay.doronkin.businessengine.repository.TicketRepository;
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
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private TicketService ticketService;

    @Spy
    private TicketDtoToTicketEntityMapper ticketMapper = Mappers.getMapper(TicketDtoToTicketEntityMapper.class);

    private Ticket ticket;
    private TicketDto expectedTicketDto;
    private User user;
    private JwtUser jwtUser;
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private List<Ticket> tickets;
    private List<TicketDto> ticketsDto;

    private static final Long ID = 1L;
    private static final String CITY_1 = "TEST_CITY1";
    private static final String AIRPORT_1 = "TEST_AIRPORT1";
    private static final String CITY_2 = "TEST_CITY2";
    private static final String AIRPORT_2 = "TEST_AIRPORT2";
    private static final Long ID_AIRPORT_1 = 1L;
    private static final Long ID_AIRPORT_2 = 2L;

    private static final Long PHONE_NUMBER = 80298209912L;
    private static final String TEST_STRING = "test";
    private static final String TEST_EMAIL = "test@gmail.com";

    @BeforeEach
    void setUp() {
        departureAirport = Airport.builder()
                .name(AIRPORT_1)
                .city(CITY_1)
                .build();
        arrivalAirport = Airport.builder()
                .name(AIRPORT_2)
                .city(CITY_2)
                .build();
        departureAirport.setId(ID_AIRPORT_1);
        arrivalAirport.setId(ID_AIRPORT_2);

        user = User.builder()
                .userName(TEST_STRING)
                .surName(TEST_STRING)
                .lastName(TEST_STRING)
                .email(TEST_EMAIL)
                .password(TEST_STRING)
                .phoneNumber(PHONE_NUMBER)
                .roles(UserRole.ROLE_ADMIN)
                .status(UserStatus.ACTIVE)
                .tickets(new ArrayList<>())
                .build();
        user.setId(ID);

        flight = Flight.builder()
                .departureId(ID_AIRPORT_1)
                .arrivalId(ID_AIRPORT_2)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .build();
        flight.setId(ID);

        ticket = Ticket.builder()
                .flightId(ID)
                .userId(ID)
                .user(user)
                .flight(flight)
                .build();
        ticket.setId(ID);
        tickets = List.of(ticket);
        jwtUser = JwtUser.builder()
                .user(user).build();

        expectedTicketDto = new TicketDto();
        expectedTicketDto.setUserId(ID);
        expectedTicketDto.setFlightId(ID);

        ticketsDto = List.of(expectedTicketDto);
    }

    @Test
    void testFindByIdSuccess() {
        Mockito.when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));

        TicketDto actualTicketDto = ticketService.findById(ID);

        Assertions.assertEquals(expectedTicketDto, actualTicketDto);
    }

    @Test
    void testFindByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                ticketService.findById(ID)
        );
    }

    @Test
    void testFindAllSuccess() {
        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        List<TicketDto> actualTickets = ticketService.findAll();

        Assertions.assertEquals(ticketsDto, actualTickets);
    }

    @Test
    void testCreateSuccess() {
        Mockito.when(flightRepository.findById(ID)).thenReturn(Optional.of(flight));

        TicketDto actualTicket = ticketService.create(jwtUser, ID);

        Assertions.assertEquals(expectedTicketDto, actualTicket);
    }

    @Test
    void testCreateFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                ticketService.create(jwtUser, ID)
        );
    }

    @Test
    void testDeleteByIdSuccess() {
        Mockito.doReturn(true).when(ticketRepository).existsById(ID);

        ticketService.deleteById(ID);

        Mockito.verify(ticketRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                ticketService.deleteById(ID)
        );
    }
}