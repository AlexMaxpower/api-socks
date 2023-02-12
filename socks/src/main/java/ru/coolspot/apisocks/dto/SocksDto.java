package ru.coolspot.apisocks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SocksDto {

    @Size(max = 255)
    private String color;

    @Min(0)
    @Max(value = 100, message = "Процент хлопка не может быть больше 100!")
    @JsonProperty("cottonPart")
    private Byte cotton;

    @Min(1)
    private Integer quantity;

}