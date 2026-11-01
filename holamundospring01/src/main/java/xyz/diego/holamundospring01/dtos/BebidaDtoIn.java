package xyz.diego.holamundospring01.dtos;

import jakarta.validation.constraints.*;

public record BebidaDtoIn(
        @NotBlank @Size(max = 64) String nombre,
        @NotBlank @Size(max = 256) String descripcion,
        @PositiveOrZero double precio) {
}
