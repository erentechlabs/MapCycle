package com.mapcycle.mapcycle.post.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    CommentDto toDto(Comment comment);

    // toEntity: ilişkisel alanları servis katmanında setleyeceğimiz için ignore ediyoruz
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto dto);
}
