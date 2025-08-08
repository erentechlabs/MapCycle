package com.mapcycle.mapcycle.route;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteDto toDto(Route route);
    Route toEntity(RouteDto dto);
}
