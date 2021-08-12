package com.nikolay.doronkin.businessengine.mapper;

import com.nikolay.doronkin.businessengine.dto.UserDto;
import com.nikolay.doronkin.businessengine.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoToUserEntityMapper {
    UserDto userEntityToUserDto(User user);
}
