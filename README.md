Importante:
Este proyecto implementa la librería del proyexto Lombok, tener en cuenta,

Descripción general del proyecto:

Este proyecto usa como lenguaje principal Java 17 además de otras tecnologpias de spring como :

Spring boot
Spring Web
Spring Data JPA
Spring Security
Swagger para crear la documentación del mismo (la paginación aún no soporta el paramentro "sort"):
http://.../swagger-ui/index.html#/
La librería de auth0 para generar los tokens en conjunto con spring 

Y usa el conector de MySQL para usar esta base de datos para guardar los datos

Este proyecto simula ser una especie de foro básico para realizar pulblicciones, y comentarios a las mismas, identificando a los usuarios mediante el toke n que se les asigna al momento de iniciar sesión
cuenta con endpoints para crear un nuevo usuario, accesar al sitio, además de realizar las operaciones CRUD básicas para cada entidad que reprentan a las publicaciones y sus posibles comentarios, 
pagina ambas mediante objetos Page mediante párametros Pageable
Cuenta con endponits solo accesibles para aquellos con rol de admin para ocultar y reponer publicaciones ocultas, además de estos mismos poder borrar cualquier publicación y comentario siendo que los usuarios con el rol de usuario solo pueden eliminar sus propias publicaciones y comentarios
