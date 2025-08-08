package com.mapcycle.mapcycle.ride;

import com.mapcycle.mapcycle.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RideMapper {

    // Ride entity to DTO
    @Mapping(source = "user.id", target = "userId")
    RideDto toDto(Ride ride);

    // Ride DTO to entity (user dışındaki her şey)
    @Mapping(target = "user", ignore = true)
    Ride toEntity(RideDto dto);

    // Ride DTO to entity + User parametresi
    @Mapping(source = "dto", target = ".")
    @Mapping(source = "user", target = "user")
    Ride toEntityWithUser(RideDto dto, User user);
}
