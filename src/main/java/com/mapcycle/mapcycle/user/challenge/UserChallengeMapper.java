package com.mapcycle.mapcycle.user.challenge;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserChallengeMapper {
    UserChallengeDto toDto(UserChallenge userChallenge);
    UserChallenge toEntity(UserChallengeDto dto);
}
