Sistema de Gestión de Biblioteca
Descripción
Este proyecto implementa un sistema de gestión para bibliotecas que permite administrar socios, libros y préstamos. Desarrollado en Java, proporciona una interfaz de línea de comandos para realizar todas las operaciones necesarias en una biblioteca.

Requisitos
Java JDK 23
Entorno de desarrollo compatible con Java (IntelliJ IDEA recomendado)
Funcionalidades
El sistema permite:

Gestionar socios:

Crear nuevos socios con sus datos personales
Asignar automáticamente un número único de socio
Gestionar libros:

Registrar libros con ISBN, título, género y número de páginas
Consultar disponibilidad de libros
Gestionar préstamos:

Prestar libros a socios (máximo 5 por socio)
Devolver libros a la biblioteca
Consultas:

Mostrar libros disponibles
Mostrar libros prestados
Listar libros por socio
Estructura del Proyecto
GestorBiblioteca.java: Clase principal que contiene el menú interactivo y maneja la interfaz de usuario
Clases de dominio:
Biblioteca: Gestiona la colección de libros y socios
Libro: Representa un libro con sus atributos y estado
Socio: Representa a un socio de la biblioteca
Uso
Compile y ejecute la clase GestorBiblioteca
Utilice el menú interactivo para gestionar la biblioteca:
Limitaciones
El sistema admite un máximo de 1000 socios
La biblioteca puede almacenar hasta 5000 libros
Cada socio puede tener prestados un máximo de 5 libros
Autor
Desarrollado como proyecto educativo para el Liceo La Paz.
