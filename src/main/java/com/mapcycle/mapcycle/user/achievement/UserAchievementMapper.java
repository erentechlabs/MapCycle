package com.mapcycle.mapcycle.user.achievement;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {
    UserAchievementDto toDto(UserAchievement userAchievement);
    UserAchievement toEntity(UserAchievementDto dto);
}
