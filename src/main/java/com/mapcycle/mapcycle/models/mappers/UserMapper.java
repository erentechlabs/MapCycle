package com.mapcycle.mapcycle.models.mappers;

import com.mapcycle.mapcycle.models.dtos.UserRegistrationDto;
import com.mapcycle.mapcycle.models.dtos.UserResponseDto;
import com.mapcycle.mapcycle.models.dtos.UserUpdateDto;
import com.mapcycle.mapcycle.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponseDto toResponseDto(User user);

    User toEntity(UserRegistrationDto registrationDto);

    void updateUserFromDto(UserUpdateDto updateDto, @MappingTarget User user);
}