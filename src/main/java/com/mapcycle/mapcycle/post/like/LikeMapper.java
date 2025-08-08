package com.mapcycle.mapcycle.post.like;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    LikeDto toDto(Like like);
    Like toEntity(LikeDto dto);
}
