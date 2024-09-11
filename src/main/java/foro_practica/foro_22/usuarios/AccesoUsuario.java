package foro_practica.foro_22.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccesoUsuario(@NotBlank String nombreUsuario, @NotBlank String contrasena) {

}