package ru.coolspot.apisocks.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.coolspot.apisocks.dto.Quantity;
import ru.coolspot.apisocks.dto.SocksDto;
import ru.coolspot.apisocks.other.Operation;
import ru.coolspot.apisocks.service.SocksService;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping(path = "/api/socks")
public class SocksController {

    private final SocksService socksService;

    @Autowired
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/income")
    public SocksDto addSocks(@Valid @RequestBody SocksDto socksDto, HttpServletRequest request) {
        log.info("{}: Запрос к эндпоинту '{}' на добавление на склад Socks ={}",
                request.getRemoteAddr(),
                request.getRequestURI(),
                socksDto);
        return socksService.addSocks(socksDto);
    }

    @PostMapping("/outcome")
    public SocksDto subSocks(@Valid @RequestBody SocksDto socksDto, HttpServletRequest request) {
        log.info("{}: Запрос к эндпоинту '{}' на выдачу со склада Socks ={}",
                request.getRemoteAddr(),
                request.getRequestURI(),
                socksDto);
        return socksService.subSocks(socksDto);
    }

    @GetMapping
    public Quantity getSocks(@RequestParam(required = false) String color,
                              @RequestParam(defaultValue = "equal") Operation operation,
                              @Valid @PositiveOrZero @RequestParam(required = false) Integer cottonPart,
                              HttpServletRequest request) {
        log.info("{}: Запрос к эндпоинту '{}' на получение количества Socks",
                request.getRemoteAddr(), request.getRequestURI());

        return socksService.getSocks(color, operation, cottonPart);
    }

    @GetMapping("/list")
    public Collection<SocksDto> getAllSocks(@Valid @RequestBody SocksDto socksDto, HttpServletRequest request) {
        log.info("{}: Запрос к эндпоинту '{}' на получение всех Socks на складе",
                request.getRemoteAddr(),
                request.getRequestURI());
        return socksService.getAllSocks();
    }
}