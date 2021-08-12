package com.nikolay.doronkin.businessengine.mapper;

import com.nikolay.doronkin.businessengine.dto.TicketDto;
import com.nikolay.doronkin.businessengine.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketDtoToTicketEntityMapper {
    TicketDto ticketEntityToTicketDto(Ticket ticket);
}
