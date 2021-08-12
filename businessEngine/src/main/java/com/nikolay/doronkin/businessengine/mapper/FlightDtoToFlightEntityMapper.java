package com.nikolay.doronkin.businessengine.mapper;

import com.nikolay.doronkin.businessengine.dto.FlightDto;
import com.nikolay.doronkin.businessengine.model.Flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightDtoToFlightEntityMapper {
    FlightDto flightEntityToFlightDto(Flight flight);
}
