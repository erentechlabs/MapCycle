package com.mapcycle.mapcycle.challenge;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    ChallengeDto toDto(Challenge challenge);
    Challenge toEntity(ChallengeDto dto);
}
