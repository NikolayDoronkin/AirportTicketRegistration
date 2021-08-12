package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.TicketDto;
import com.nikolay.doronkin.businessengine.exception.ExceptionMessages;
import com.nikolay.doronkin.businessengine.jwt.JwtUser;
import com.nikolay.doronkin.businessengine.mapper.TicketDtoToTicketEntityMapper;
import com.nikolay.doronkin.businessengine.model.Flight;
import com.nikolay.doronkin.businessengine.model.Ticket;
import com.nikolay.doronkin.businessengine.model.User;
import com.nikolay.doronkin.businessengine.repository.FlightRepository;
import com.nikolay.doronkin.businessengine.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    private final TicketDtoToTicketEntityMapper ticketMapper;

    public TicketDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.NO_SUCH_ELEMENT_BY_ID)
        );
        return ticketMapper.ticketEntityToTicketDto(ticket);
    }

    public List<TicketDto> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(ticketMapper::ticketEntityToTicketDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if(!ticketRepository.existsById(id)){
            throw new EntityNotFoundException(ExceptionMessages.TICKET_NOT_FOUND_WITH_ID + id);
        }
        ticketRepository.deleteById(id);
    }

    @Transactional
    public TicketDto create(JwtUser jwtUser, Long flightId) {
        User userEntity = jwtUser.getUser();
        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.NO_SUCH_ELEMENT_BY_ID)
        );
        Ticket ticket = Ticket.builder()
                .flightId(flightId)
                .userId(userEntity.getId())
                .user(userEntity)
                .flight(flight)
                .build();
        ticketRepository.save(ticket);
        return ticketMapper.ticketEntityToTicketDto(ticket);
    }
}
