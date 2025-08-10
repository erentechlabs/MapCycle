package com.mapcycle.mapcycle.achievement;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AchievementMapper {
    AchievementDto toDto(Achievement achievement);
    Achievement toEntity(AchievementDto dto);
}
