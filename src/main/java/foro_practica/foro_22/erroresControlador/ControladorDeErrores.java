package foro_practica.foro_22.erroresControlador;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControladorDeErrores {

	@ExceptionHandler (EntityNotFoundException.class)
	public ResponseEntity errores404 () {
		
		return ResponseEntity.notFound().build();
		
	}
	
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity errores400(MethodArgumentNotValidException e) {
		
		var errores = e.getFieldErrors().stream().map(ErroresDeValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
		
	}
	
	private record ErroresDeValidacion (String campo, String errores ) {
		
		public ErroresDeValidacion(FieldError error) {
			
			this(error.getField(), error.getDefaultMessage());
			
		}
		
	}
	
}
