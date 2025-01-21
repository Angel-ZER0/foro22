package foro_practica.foro_22;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import foro_practica.foro_22.configuraciones.ServicioEncriptarContrasena;
import foro_practica.foro_22.modelos.EstadoPublicacion;
import foro_practica.foro_22.modelos.InicioPrincipal;
import foro_practica.foro_22.modelos.InsercionPubliciones;
import foro_practica.foro_22.modelos.InsercionRespuestas;
import foro_practica.foro_22.modelos.RespuestasPublicaciones;
import foro_practica.foro_22.repositorios.RepositorioInicioPrincipal;
import foro_practica.foro_22.repositorios.RepositorioRespuestas;
import foro_practica.foro_22.usuarios.RegistrarUsuario;
import foro_practica.foro_22.usuarios.RepositorioUsuarios;
import foro_practica.foro_22.usuarios.Roles;
import foro_practica.foro_22.usuarios.Usuarios;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class Foro22Application implements CommandLineRunner {
	
	private final RepositorioUsuarios repoUsuarios;
	private final RepositorioInicioPrincipal repoPublicacion;
	private final RepositorioRespuestas repoRespuestas;
	private final ServicioEncriptarContrasena servicioEncriptarContrasena;
	DateTimeFormatter formato = new DateTimeFormatterBuilder().append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toFormatter();
	
	
	List <RegistrarUsuario> usuariosACrear = List.of(
			
			new RegistrarUsuario("ShadowHunter91", "password123", "ShadowHunter91@example.com"),
			new RegistrarUsuario("EaterFanatic", "fnatic012", "EaterFanatic@example.com"),
			new RegistrarUsuario("DarkRogue12", "drkrog9876", "DarkRogue12@example.com"),
			new RegistrarUsuario("CrimsonTracer", "crmns4561", "CrimsonTracer@example.com"),
			new RegistrarUsuario("QuantumSoulX", "quntum6579", "QuantumSoulX@example.com"),
			new RegistrarUsuario("NightfallHero", "nght3255", "NightfallHero@example.com"),
			new RegistrarUsuario("BladeWhisper", "bldw9346", "BladeWhisper@example.com"),
			new RegistrarUsuario("EternalShade", "trnl5646", "EternalShade@example.com"),
			new RegistrarUsuario("RomanceRebel", "rmncsd5731", "RomanceRebel@example.com"),
			new RegistrarUsuario("NoirVisionary", "nrvis856'", "NoirVisionary@example.com"),
			new RegistrarUsuario("SkyBreaker77", "354ret9", "SkyBreaker77@example.com"),
			new RegistrarUsuario("SilentDagger", "slntbr6973", "SilentDagger@example.com"),
			new RegistrarUsuario("FlameChronicle", "flmcr8933", "FlameChronicle@example.com"),
			new RegistrarUsuario("VelvetAvenger", "vvravn6430", "VelvetAvenger@example.com"),
			new RegistrarUsuario("NeonFugitive", "nnfg83.7", "NeonFugitive@example.com")
			
	);
	
	List <Usuarios> usuarios = new ArrayList();
	
	void crearUsuarios(RegistrarUsuario registrarUsuario) {
		
		String contrasenaCifrada = servicioEncriptarContrasena.encriptacionContrasena(registrarUsuario.contrasena());
		Usuarios usuario = new Usuarios(registrarUsuario, contrasenaCifrada, Roles.USER);
		usuarios.add(usuario);
		
	}
	
	List <InsercionPubliciones> publicaciones = List.of(
			
			new InsercionPubliciones(
					"La última escena del episodio dejó mi corazón en un puño. Quantum Eater realmente sabe manejar la tensión entre acción y emociones.",
					"2025-01-01 10:15:30", "La tensión final de Quantum Eater", 4L),
			new InsercionPubliciones(
					"¿Alguien más pensó que la relación entre los protagonistas se pondría más oscura después del ataque en el bar?",
					"2025-01-03 15:45:10", "El ataque sorpresa en Quantum Eater", 7L),
			new InsercionPubliciones(
					"El episodio 5 tuvo más acción que los anteriores, pero la historia romántica sigue en su punto justo.",
					"2024-12-20 12:10:50", "Episodio 5: Más acción, menos charla", 2L),
			new InsercionPubliciones(
					"Me atrapó completamente la idea de un héroe con sombras en su pasado. Quantum Eater está creando personajes profundos.",
					"2024-12-22 18:25:40", "El héroe roto de Quantum Eater", 15L),
			new InsercionPubliciones(
					"¡No puedo superar la pelea en el tejado! Esa coreografía fue alucinante. Quantum Eater nunca decepciona.",
					"2025-01-05 14:35:15", "La mejor pelea de la temporada", 6L),
			new InsercionPubliciones(
					"El misterio detrás del villano principal es increíblemente intrigante. ¿Quién más tiene teorías sobre su relación con el pasado de Eater?",
					"2024-12-30 09:10:00", "Teorías sobre el villano de Quantum Eater", 10L),
			new InsercionPubliciones(
					"Nunca esperé un giro tan intenso en el episodio 8. ¿Por qué todo el mundo sigue traicionando al protagonista?",
					"2025-01-02 20:05:20", "Traiciones y más traiciones", 12L),
			new InsercionPubliciones(
					"Quantum Eater debería ganar un premio solo por la cinematografía. Esa escena del río fue poesía visual.",
					"2025-01-04 16:55:25", "La escena del río", 3L),
			new InsercionPubliciones(
					"Me hizo llorar el flashback sobre el romance perdido del protagonista. La serie tiene mucho corazón.",
					"2024-12-25 11:50:35", "El romance olvidado de Eater", 5L),
			new InsercionPubliciones(
					"Alguien más está obsesionado con la banda sonora de Quantum Eater. ¡Es perfecta para las persecuciones!",
					"2025-01-07 08:40:00", "La banda sonora perfecta", 14L),
			new InsercionPubliciones(
					"Los monólogos internos de Eater son oro puro. Especialmente cuando empieza a cuestionarse sus elecciones.",
					"2024-12-27 22:15:45", "El dilema moral del protagonista", 11L),
			new InsercionPubliciones(
					"¡El episodio final fue una montaña rusa emocional! ¿Cuándo sale la próxima temporada?",
					"2025-01-09 14:30:15", "La temporada culmina con fuerza", 8L),
			new InsercionPubliciones(
					"La revelación del pasado del antagonista me dejó sin palabras. Quantum Eater sigue superando expectativas.",
					"2024-12-29 17:50:55", "Pasado sombrío revelado", 16L),
			new InsercionPubliciones(
					"El capítulo 9 tuvo un balance perfecto entre acción, diálogo y desarrollo romántico. ¡Imperdible!",
					"2025-01-06 09:15:30", "Quantum Eater: Capítulo 9", 9L),
			new InsercionPubliciones(
					"Siento que esta serie marca un antes y después en el género de acción con tintes oscuros.",
					"2024-12-26 13:40:20", "Quantum Eater redefine el género", 13L),
			new InsercionPubliciones(
					"La conspiración que se desarrolla en el episodio 7 es fascinante. ¡Quiero más episodios así!",
					"2025-01-08 12:35:45", "La conspiración en Quantum Eater", 4L),
			new InsercionPubliciones(
					"La despedida al final de la temporada fue devastadora. Una mezcla perfecta de tragedia y esperanza.",
					"2024-12-31 10:10:20", "Despedidas y esperanzas", 2L),
			new InsercionPubliciones(
					"Quantum Eater nunca teme mostrar los sacrificios de sus personajes. Eso lo hace único.",
					"2025-01-10 19:25:50", "Los sacrificios en Quantum Eater", 7L),
			new InsercionPubliciones("No sé cómo serán las próximas temporadas, pero esta dejó el listón muy alto.",
					"2025-01-11 20:45:35", "El futuro de Quantum Eater", 12L),
			new InsercionPubliciones(
					"A pesar de los tintes oscuros, siempre encuentro algo de esperanza en los episodios. Eso me atrapa.",
					"2024-12-28 18:15:25", "Esperanza entre sombras", 5L)
			
	);
	
	void anadirPublicaciones(InsercionPubliciones publicacion) {
		
		LocalDateTime fecha = LocalDateTime.parse(publicacion.fecha(), formato);
		repoPublicacion.save(new InicioPrincipal(publicacion.contenido(), EstadoPublicacion.ABIERTO, fecha,
				publicacion.titulo(), repoUsuarios.getReferenceById(publicacion.idUsuario())));
		
	}
	
	List<InsercionRespuestas> respuestas = List.of(

			new InsercionRespuestas(
					"¡Completamente de acuerdo! Las escenas de acción fueron espectaculares. Definitivamente el mejor episodio hasta ahora.",
					3L, 3L, "2024-12-20 14:25:30"),
			new InsercionRespuestas(
					"Creo que el equilibrio entre la acción y el romance fue perfecto. Me encanta cómo no pierden de vista el desarrollo de los personajes.",
					3L, 7L, "2024-12-20 16:40:10"),
			new InsercionRespuestas(
					"¡Ese enfrentamiento en el puente fue épico! Aunque espero que exploren más la relación romántica en los próximos episodios.",
					3L, 5L, "2024-12-21 10:15:45"),
			new InsercionRespuestas(
					"No puedo superar lo intenso que fue ese momento. Quantum Eater sigue superando mis expectativas.",
					3L, 9L, "2024-12-21 15:30:00"),
			new InsercionRespuestas(
					"El episodio tuvo un ritmo impecable. ¿Soy el único que piensa que el villano robó la escena esta vez?",
					3L, 12L, "2024-12-21 20:05:25"),
			new InsercionRespuestas(
					"¡Qué buena serie! Este episodio en particular demuestra lo bien que pueden mezclar acción y emoción sin perder el hilo de la historia.",
					3L, 8L, "2024-12-22 09:45:30"),
			new InsercionRespuestas(
					"Para mí, fue un punto de inflexión en la trama. El protagonista parece más vulnerable y más humano ahora.",
					3L, 14L, "2024-12-22 13:20:50"),
			new InsercionRespuestas("La vulnerabilidad del héroe lo hace muy real. Quantum Eater no se guarda nada.",
					4L, 7L, "2024-12-22 20:15:00"),
			new InsercionRespuestas(
					"¡La historia detrás de su pasado me hizo llorar! Es una serie verdaderamente única.", 4L, 10L,
					"2024-12-22 21:00:00"),
			new InsercionRespuestas(
					"Me pregunto si llegará a superar sus traumas o seguirá cargando con ellos en próximas temporadas.",
					4L, 13L, "2024-12-23 08:45:00"),
			new InsercionRespuestas(
					"Definitivamente redefine cómo vemos a los héroes. Lo oscuro de su pasado es fascinante.", 4L, 4L,
					"2024-12-23 10:30:00"),
			new InsercionRespuestas(
					"Siento que el pasado del héroe está conectado con el villano. Cada capítulo sorprende más.", 4L,
					15L, "2024-12-23 14:10:00"),
			new InsercionRespuestas(
					"Quantum Eater me recuerda que ser fuerte también significa aceptar tus errores. Excelente episodio.",
					4L, 6L, "2024-12-23 18:05:00"),
			new InsercionRespuestas(
					"Este capítulo fue una obra maestra. ¿Cómo será cuando finalmente enfrente a sus demonios?", 4L, 9L,
					"2024-12-23 20:20:00"),
			new InsercionRespuestas(
					"Ese flashback me hizo recordar cómo la serie equilibra la acción con momentos tan emotivos.", 9L,
					3L, "2024-12-25 15:30:45"),
			new InsercionRespuestas(
					"La profundidad emocional del protagonista es lo que hace que esta serie sea tan especial.", 9L,
					14L, "2024-12-26 10:10:30"),
			new InsercionRespuestas("Ese romance perdido realmente añade capas al desarrollo del personaje principal.",
					9L, 6L, "2024-12-26 16:45:20"),
			new InsercionRespuestas("Quantum Eater sabe cómo tocar el alma mientras mantiene el ritmo de acción.", 9L,
					11L, "2024-12-27 09:30:15"),
			new InsercionRespuestas("Ese capítulo dejó una marca, uno de los mejores momentos emotivos de la serie.",
					9L, 8L, "2024-12-28 14:50:05"),
			new InsercionRespuestas("Totalmente de acuerdo, no hay otra serie que combine acción y profundidad así.",
					15L, 2L, "2024-12-27 11:20:30"),
			new InsercionRespuestas("La mezcla de géneros es magistral, especialmente con esos tintes oscuros.", 15L,
					9L, "2024-12-27 17:45:55"),
			new InsercionRespuestas("Quantum Eater está creando un nuevo estándar para las series de acción.", 15L, 12L,
					"2024-12-28 08:05:10"),
			new InsercionRespuestas("La oscuridad en los temas tratados es lo que la hace sobresalir.", 15L, 4L,
					"2024-12-28 20:25:40"),
			new InsercionRespuestas("Redefinir géneros es difícil, pero esta serie lo logra con excelencia.", 15L, 15L,
					"2024-12-29 10:50:25"),
			new InsercionRespuestas(
					"Los monólogos realmente profundizan en los conflictos del personaje, impresionantes.", 11L, 5L,
					"2024-12-28 10:20:15"),
			new InsercionRespuestas("Esas dudas internas muestran un nivel de escritura que pocas series tienen.", 11L,
					10L, "2024-12-28 14:40:35"),
			new InsercionRespuestas("Cada monólogo revela capas de humanidad que hacen de Eater un gran protagonista.",
					11L, 7L, "2024-12-29 12:50:25"),
			new InsercionRespuestas("Las elecciones y los conflictos internos lo hacen un personaje tan real.", 11L, 6L,
					"2024-12-29 18:20:55"),
			new InsercionRespuestas("Ese dilema le agrega un peso increíble a la narrativa de la serie.", 11L, 13L,
					"2024-12-30 11:30:10"),
			new InsercionRespuestas("Es increíble cómo siempre hay un rayo de esperanza en medio del caos.", 20L, 3L,
					"2024-12-29 10:10:45"),
			new InsercionRespuestas("A pesar de lo oscuro, la serie tiene un mensaje positivo que resuena.", 20L, 12L,
					"2024-12-30 16:20:25"),
			new InsercionRespuestas("Justo esa dualidad entre sombras y esperanza es lo que más me gusta.", 20L, 9L,
					"2024-12-31 08:15:40"),
			new InsercionRespuestas("La revelación fue tan inesperada que me dejó helado, increíble episodio.", 13L,
					15L, "2024-12-30 10:25:15"),
			new InsercionRespuestas("El pasado del villano agrega tanto a la trama, qué excelente narrativa.", 13L, 5L,
					"2024-12-31 12:50:30"),
			new InsercionRespuestas("Los antagonistas profundos son los mejores, este no es una excepción.", 13L, 7L,
					"2025-01-01 09:10:20"),
			new InsercionRespuestas("Creo que tiene algo personal contra Eater, sus interacciones lo sugieren.", 6L,
					13L, "2024-12-31 15:30:45"),
			new InsercionRespuestas("Podría ser una figura del pasado de Eater, todo encajaría perfectamente.", 6L, 11L,
					"2025-01-01 08:50:20"),
			new InsercionRespuestas("Esa teoría sobre su conexión con el héroe parece cada vez más plausible.", 6L, 8L,
					"2025-01-01 14:25:10"),
			new InsercionRespuestas(
					"Ese final me dejó pensando en lo que significa realmente sacrificarse por los demás. Quantum Eater siempre logra sorprenderme.",
					17L, 5L, "2025-01-01 12:30:00"),
			new InsercionRespuestas(
					"La mezcla entre tragedia y esperanza en el desenlace fue perfecta. No puedo esperar para ver qué sigue en la próxima temporada.",
					17L, 14L, "2025-01-02 08:45:00"),
			new InsercionRespuestas("Esa escena final fue increíble. El balance entre acción y emoción fue impecable.",
					1L, 8L, "2025-01-01 14:20:00"),
			new InsercionRespuestas(
					"Quantum Eater realmente sabe cómo mantenernos al borde del asiento. Esa tensión fue perfecta para cerrar el episodio.",
					1L, 12L, "2025-01-02 16:15:00"),
			new InsercionRespuestas(
					"¡Estoy ansioso por ver qué depara el futuro de Quantum Eater! Esta temporada fue increíble.", 19L,
					10L, "2025-01-22 04:53:16"),
			new InsercionRespuestas(
					"No puedo creer lo intenso que fue el final de esta temporada. ¡Estoy emocionado por lo que vendrá!",
					19L, 9L, "2025-02-03 13:43:09"),
			new InsercionRespuestas(
					"Quantum Eater ha sabido mantenerme en vilo en cada episodio. ¡No puedo esperar para ver más!", 19L,
					11L, "2025-03-19 22:38:54"),
			new InsercionRespuestas(
					"¡Esta serie logra mantenerme enganchado temporada tras temporada! El futuro de Quantum Eater promete.",
					19L, 8L, "2025-04-01 00:52:45"),
			new InsercionRespuestas(
					"Los sacrificios en Quantum Eater añaden una capa de realismo y profundidad a la trama. ¡Es impresionante!",
					18L, 7L, "2025-05-18 20:34:17"),
			new InsercionRespuestas(
					"Es sorprendente la valentía de los personajes y los sacrificios que están dispuestos a hacer. Quantum Eater sabe cómo sorprender.",
					18L, 3L, "2025-06-04 01:06:12"),
			new InsercionRespuestas(
					"Los sacrificios en Quantum Eater hacen que los personajes sean más complejos y reales. ¡Me encanta esta faceta de la serie!",
					18L, 5L, "2025-06-15 15:47:00"),
			new InsercionRespuestas(
					"¡Qué final más emocionante para la temporada! Me encantó la intensidad del episodio y cómo dejaron abierta la trama para futuras entregas.",
					12L, 11L, "2025-01-10 09:15:25"),
			new InsercionRespuestas(
					"No puedo creer lo emocional que fue este episodio final. ¿Alguien sabe algo sobre la fecha de estreno de la próxima temporada?",
					12L, 5L, "2025-01-12 13:45:00"),
			new InsercionRespuestas(
					"La conspiración que se presentó en el episodio 7 fue una sorpresa inesperada. Cada vez se pone mejor esta serie.",
					16L, 9L, "2025-01-08 19:45:35"),
			new InsercionRespuestas(
					"Esa conspiración en el episodio 7 me hizo replantear muchas teorías sobre el villano. Quantum Eater siempre me tiene enganchado.",
					16L, 12L, "2025-01-09 08:10:15"),
			new InsercionRespuestas(
					"La música fue otro protagonista durante las persecuciones. Definitivamente, Quantum Eater destaca en todos los sentidos.",
					10L, 7L, "2025-01-08 16:25:40"),
			new InsercionRespuestas(
					"Esa pelea en el tejado fue épica, la forma en que las luces de la ciudad contrastaron con las sombras en el fondo... ¡impresionante!",
					5L, 3L, "2025-01-06 17:35:00"),
			new InsercionRespuestas(
					"Todavía estoy pensando en la coreografía de la pelea en el tejado. Cada movimiento estaba perfectamente calculado. ¡Increíble!",
					5L, 4L, "2025-01-06 20:35:00"),
			new InsercionRespuestas(
					"Esas persecuciones con la banda sonora que acompaña cada movimiento... ¿cómo no emocionarse? Esta serie hace magia con todo.",
					8L, 8L, "2025-01-05 21:55:00"),
			new InsercionRespuestas(
					"Quantum Eater está llevando el género a un nuevo nivel. Las escenas del río te atrapan y te dejan con ganas de más.",
					8L, 2L, "2025-01-05 18:55:00"),
			new InsercionRespuestas(
					"En ese ataque en el bar me quedé sin palabras. Los cambios en la relación de los personajes me tienen atrapado.",
					2L, 14L, "2025-01-04 22:45:00"),
			new InsercionRespuestas(
					"La trama de las traiciones en el episodio 8 ha sido uno de los giros más impactantes, ahora no me fío de nadie.",
					7L, 10L, "2025-01-04 22:05:00")

	);
	
	void anadirRespuestas (InsercionRespuestas respuesta) {
		
		LocalDateTime fecha = LocalDateTime.parse(respuesta.fecha(), formato);
		RespuestasPublicaciones respuestaAIngresar = new RespuestasPublicaciones(		
				respuesta.respuesta(),
				repoPublicacion.getReferenceById(respuesta.idPublicacion()),
				repoUsuarios.getReferenceById(respuesta.idUsuario()),
				fecha
		);
		repoRespuestas.save(respuestaAIngresar);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(Foro22Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		if (repoUsuarios.count() == 0) {
			
			RegistrarUsuario administrador = new RegistrarUsuario("administrador_primigenio", "administradorPrimigenio",
					"administrador@example.com");

			Usuarios nuevoAdministrador = new Usuarios(administrador,
					servicioEncriptarContrasena.encriptacionContrasena(administrador.contrasena()), Roles.ADMIN);

			repoUsuarios.save(nuevoAdministrador);

			usuariosACrear.forEach(usuario -> crearUsuarios(usuario));

			repoUsuarios.saveAll(usuarios);
			
		}
		
		if (repoPublicacion.count() == 0) {
			
			publicaciones.forEach(p -> anadirPublicaciones(p));
			
		}
		
		if (repoRespuestas.count() == 0) {
			
			respuestas.forEach(r -> anadirRespuestas(r));
			
		}
		
	}

}
