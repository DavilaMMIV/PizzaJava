package xyz.diego.holamundospring01.dtos;

import jakarta.validation.constraints.*;

public record ClienteDtoIn(
        @NotBlank @Size(max = 64) String nombre,
        @NotBlank @Size(max = 20) String telefono,
        @NotBlank @Email @Size(max = 128) String correo,
        @NotBlank @Size(max = 256) String direccion) {
}
