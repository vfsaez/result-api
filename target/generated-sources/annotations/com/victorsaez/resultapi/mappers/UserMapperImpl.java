package com.victorsaez.resultapi.mappers;

import com.victorsaez.resultapi.dto.UserDTO;
import com.victorsaez.resultapi.entities.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-01T14:35:45-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.22 (Ubuntu)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setName( user.getName() );
        userDTO.setRoles( user.getRoles() );

        return userDTO;
    }

    @Override
    public User userDTOtoUser(UserDTO userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setName( userDto.getName() );
        user.setRoles( userDto.getRoles() );

        return user;
    }
}
