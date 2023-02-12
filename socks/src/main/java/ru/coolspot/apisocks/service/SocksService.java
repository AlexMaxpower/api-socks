package ru.coolspot.apisocks.service;

import org.springframework.stereotype.Service;
import ru.coolspot.apisocks.dto.Quantity;
import ru.coolspot.apisocks.dto.SocksDto;
import ru.coolspot.apisocks.other.Operation;

import java.util.Collection;

@Service
public interface SocksService {
    Collection<SocksDto> getAllSocks();

    SocksDto addSocks(SocksDto socksDto);

    SocksDto subSocks(SocksDto socksDto);

    Quantity getSocks(String color, Operation operation, Integer cottonPart);
}
