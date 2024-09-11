package foro_practica.foro_22.modelos;

import jakarta.validation.constraints.NotBlank;

public record ComentarioPublicacion(@NotBlank String respuesta) {

}
