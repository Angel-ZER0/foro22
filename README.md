Foro 22
=======

Descripción general del proyecto
--------------------------------

Este proyecto fue realizado usando tecnologías tales como:

### Spring Boot

*   spring-boot-starter-data-jpa:
    *   Proporciona integración con JPA (Java Persistence API) para trabajar con bases de datos relacionales.
    *   Incluye Hibernate como implementación de JPA.
    *   Facilita el manejo de entidades, repositorios y consultas de bases de datos.
*   spring-boot-starter-security:
    *   Implementa seguridad en el proyecto con Spring Security.
    *   Permite la configuración de autenticación y autorización.
    *   Permite la configuración de autenticación y autorización.
*   spring-boot-starter-validation:
    *   Proporciona soporte para la validación de datos a través de anotaciones de Bean Validation (JSR 380).
    *   Permite definir reglas de validación sobre clases de DTOs (Data Transfer Objects) y entidades.
*   spring-boot-starter-web:
    *   Añade soporte para construir servicios REST y aplicaciones web.
    *   Incluye un servidor embebido (por defecto, Tomcat) para ejecutar la aplicación.
    *   Facilita la creación de controladores y la configuración de rutas.
*   spring-boot-devtools:
    *   Agilizar desarrolo del proyecto mediante recarga automática

### MySQL 8.0

Usado como base de datos del proyecto

### Lombok

Agilización de escritura del código mediante sus anotaciones

### Java JWT de Auth0

Usado en conjunto con spring-boot-starter-security para implementar un sistema de token que se usan en el proyeto para autenticar y validar a los usuarios mediante el uso de Jason Web Token y permitir o no, realizar acciones a los usuarios en los diferentes endpoints del proyecto.

### springdoc-openapi-starter-webmvc-ui

*   Generación automática de la documentación de la API, una vez ejecutado el proyecto en la dirección http://localhost:8080/swagger-ui/index.html
    *   Siendo el caso de que se usan objetos **Page**, **Pageable** para la paginación del proyecto, esta se debe realizar en los parámetros de la query, eliminado los parametros que tiene la documentacion, de lo contrario causará errores al momento de la consulta

### Java 17

Para la escritura total del código del proyecto

En esta versión del proyecto se añadieron cambios como:
-------------------------------------------------------

*   Mejor tratamiento de errores en general
*   Mejor manejo de validaciones mediante implementación de nuevos métodos en los diferentes repositorios
*   Nuevo servicio para la obtención del usuario
*   Implementacion de servicion mediantes interfaces para todos los endpoints, evitando exponer información en los contraladores
*   Nuevos endpoints que añaden funciones a la API como eliminacion total de algún usuario, el usuario ahora puede acceder a una lista con todas sus "publicaciones", creación de un administrador de la aplicacion mediante un endpoint especifíco al que solo un usuario con permiso de administrador puede acceder
*   Mejoras generales en la lógica del negocio
*   Se han creado usuarios, publicaciones, respuestas que serán insertadas en las bases de datos cuando el proyecto sea ejecutado para poder interectuar con las mismas, siendo el más importante el administrador que puede ocultar publcaciones juntos con sus comentarios, crear usuarios para aydarlo en la moderacion del sitio siendo sus credenciales de acceso:
    *   "nombreUsuario": "administrador\_primigenio"
    *   "contrasena": "administradorPrimigenio"
*   Para probar estas características
