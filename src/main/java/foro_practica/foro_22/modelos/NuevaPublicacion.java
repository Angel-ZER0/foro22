package foro_practica.foro_22.modelos;

import jakarta.validation.constraints.NotBlank;

public record NuevaPublicacion(@NotBlank String titulo, @NotBlank String contenido) {

}
