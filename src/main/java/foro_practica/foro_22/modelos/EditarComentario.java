package foro_practica.foro_22.modelos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarComentario(@NotNull Long id, @NotBlank String respuesta) {

}
