package com.mapcycle.mapcycle.models.mappers;

import com.mapcycle.mapcycle.models.dtos.RouteDto;
import com.mapcycle.mapcycle.models.entities.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RouteMapper {

    @Mapping(target = "creatingUserId", source = "creatingUser.id")
    RouteDto toDto(Route route);

    @Mapping(target = "creatingUser", ignore = true)
    Route toEntity(RouteDto dto);

    @Mapping(target = "creatingUser", ignore = true)
    void updateFromDto(RouteDto dto, @org.mapstruct.MappingTarget Route entity);
}