package io.vitor.fintrack.database.mappers;

import io.vitor.fintrack.database.models.User;
import io.vitor.fintrack.database.models.dtos.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);

}
