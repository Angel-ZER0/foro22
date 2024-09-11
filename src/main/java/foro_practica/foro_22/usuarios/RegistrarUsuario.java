package foro_practica.foro_22.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrarUsuario(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9_-]+$") String nombreUsuario, @NotBlank String contrasena, @NotBlank @Email String correo) {

}