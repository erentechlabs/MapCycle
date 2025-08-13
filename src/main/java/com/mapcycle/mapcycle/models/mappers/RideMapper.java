package com.mapcycle.mapcycle.models.mappers;

import com.mapcycle.mapcycle.models.dtos.RideDto;
import com.mapcycle.mapcycle.models.entities.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RideMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "routeId", source = "route.id")
    RideDto toDto(Ride ride);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "route", ignore = true)
    Ride toEntity(RideDto dto);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "route", ignore = true)
    void updateFromDto(RideDto dto, @org.mapstruct.MappingTarget Ride entity);
}
