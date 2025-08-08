package com.mapcycle.mapcycle.user.social;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowerMapper {
    FollowerDto toDto(Follower follower);
    Follower toEntity(FollowerDto dto);
}
