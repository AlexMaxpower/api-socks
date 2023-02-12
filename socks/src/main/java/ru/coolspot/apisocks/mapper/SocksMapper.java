package ru.coolspot.apisocks.mapper;

import org.mapstruct.Mapper;
import ru.coolspot.apisocks.dto.SocksDto;
import ru.coolspot.apisocks.entity.Socks;

@Mapper(componentModel = "spring")
public interface SocksMapper {
    Socks socksDtoToSocks(SocksDto socksDto);

    SocksDto socksToSocksDto(Socks socks);

}