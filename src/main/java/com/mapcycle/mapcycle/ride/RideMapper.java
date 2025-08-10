package com.mapcycle.mapcycle.ride;

import com.mapcycle.mapcycle.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RideMapper {

    // Entity -> DTO
    @Mapping(source = "user.id", target = "userId")
    RideDto toDto(Ride ride);

    // DTO -> Entity (user setlenmeyecek, servis set eder)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Ride toEntity(RideDto dto);

    // DTO + User -> Entity (kullanıcıyı da set eden yardımcı metod)
    @Mapping(target = "id", ignore = true) // id genelde save'de DB tarafından atanır (update özel iş akışıyla setlenir)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "user", target = "user")
    Ride toEntityWithUser(RideDto dto, User user);
}
