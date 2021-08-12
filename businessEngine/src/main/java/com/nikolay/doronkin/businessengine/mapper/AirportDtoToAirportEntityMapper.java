package com.nikolay.doronkin.businessengine.mapper;

import com.nikolay.doronkin.businessengine.dto.AirportDto;
import com.nikolay.doronkin.businessengine.model.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AirportDtoToAirportEntityMapper {
    AirportDto airportEntityToAirportDto(Airport airport);

    @Mappings({
            @Mapping(target = "outcomingFlights", ignore = true),
            @Mapping(target = "incomingFlights", ignore = true)
    })
    Airport airportDtoToAirportEntity(AirportDto airportDto);
}
