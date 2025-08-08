package com.mapcycle.mapcycle.route;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.username", target = "createdByUsername")
    RouteDto toDto(Route route);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Route toEntity(RouteDto dto);
}
