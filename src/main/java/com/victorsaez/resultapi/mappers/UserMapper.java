package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.UserDTO;
import com.victorsaez.resultapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    User userDTOtoUser(UserDTO userDto);
}