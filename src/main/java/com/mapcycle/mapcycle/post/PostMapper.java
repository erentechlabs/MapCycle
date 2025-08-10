package com.mapcycle.mapcycle.post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "ride.id", target = "rideId")
    PostDto toDto(Post post);

    // toEntity: user & ride ilişkileri servis tarafından setlenecek
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ride", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post toEntity(PostDto dto);
}
