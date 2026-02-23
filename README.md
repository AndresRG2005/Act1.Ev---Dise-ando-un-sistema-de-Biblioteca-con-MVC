# Act1.Ev---Dise-ando-un-sistema-de-Biblioteca-con-MVC

Estructura del Proyecto
Código
src/
 ├── modelo/
 │    ├── Libro.java
 │    ├── Usuario.java
 │    └── Biblioteca.java
 ├── vista/
 │    └── VistaBiblioteca.java
 └── controlador/
    └── GestorBiblioteca.java
 
Modelo
Contiene las clases que representan la información y las reglas del sistema:

Libro: título, autor, estado de disponibilidad.

Usuario: datos del usuario y libros prestados.

Biblioteca: almacena y gestiona colecciones de libros y usuarios.

Vista
Gestiona la interacción con el usuario (por consola). Muestra menús, solicita datos y presenta resultados.

Controlador
Coordina la comunicación entre la vista y el modelo. Procesa las acciones del usuario y actualiza el sistema según corresponda.

Funcionalidades Principales

Registrar nuevos libros.

Registrar usuarios.

Prestar libros disponibles.

Devolver libros prestados.

Listar libros y su estado.

Validación básica de errores (libro no encontrado, usuario inexistente, etc.).

Ejecución del Programa

Clona el repositorio.

Compila los archivos .java.

Ejecuta la clase principal:

Código

GestorBiblioteca

Tecnologías Utilizadas

Java

Patrón MVC

Programación orientada a objetos

Andrés Ramos Guerra y Daniel Dos Santos Ferreira
