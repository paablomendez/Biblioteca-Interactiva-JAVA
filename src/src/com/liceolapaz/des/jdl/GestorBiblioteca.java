package src.com.liceolapaz.des.jdl; // Sustituye "jdl" por tus iniciales

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorBiblioteca {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    crearSocio();
                    break;
                case 2:
                    crearLibro();
                    break;
                case 3:
                    llevarseLibro();
                    break;
                case 4:
                    devolverLibro();
                    break;
                case 5:
                    mostrarLibrosDisponibles();
                    break;
                case 6:
                    mostrarLibrosNoDisponibles();
                    break;
                case 7:
                    mostrarLibrosDeSocio();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el Gestor Biblioteca! Hasta pronto.");
                    break;
                default:
                    System.out.println("Error: Opción no válida. Por favor, elija una opción del menú.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nGESTOR BIBLIOTECA");
        System.out.println("1. Crear socio");
        System.out.println("2. Crear libro");
        System.out.println("3. Llevarse libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Mostrar libros disponibles");
        System.out.println("6. Mostrar libros no disponibles");
        System.out.println("7. Mostrar libros de un socio");
        System.out.println("0. Salir");
        System.out.print("Escoja una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Opción no válida
        }
    }

    private static void crearSocio() {
        System.out.println("\n--- CREAR NUEVO SOCIO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine().trim();

        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine().trim();

        try {
            LocalDate fechaNacimiento = LocalDate.parse(fechaStr, formatter);

            if (biblioteca.crearSocio(nombre, apellidos, dni, email, fechaNacimiento)) {
                System.out.println("¡Socio creado correctamente!");
            } else {
                System.out.println("Error: No se pudo crear el socio. La biblioteca ha alcanzado el máximo de socios o el DNI ya existe.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Use el formato dd/MM/yyyy.");
        }
    }

    private static void crearLibro() {
        System.out.println("\n--- CREAR NUEVO LIBRO ---");
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();

        System.out.print("Número de páginas: ");
        int numeroPaginas;
        try {
            numeroPaginas = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El número de páginas debe ser un valor numérico.");
            return;
        }

        if (biblioteca.crearLibro(isbn, titulo, genero, numeroPaginas)) {
            System.out.println("¡Libro creado correctamente!");
        } else {
            System.out.println("Error: No se pudo crear el libro. La biblioteca ha alcanzado el máximo de libros o el ISBN ya existe.");
        }
    }

    private static void llevarseLibro() {
        System.out.println("\n--- LLEVARSE LIBRO ---");
        System.out.print("Número de socio: ");
        int numeroSocio;
        try {
            numeroSocio = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El número de socio debe ser un valor numérico.");
            return;
        }

        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine().trim();

        int resultado = biblioteca.prestarLibro(numeroSocio, isbn);
        switch (resultado) {
            case 0:
                System.out.println("¡Libro prestado correctamente!");
                break;
            case 1:
                System.out.println("Error: No existe ningún socio con ese número.");
                break;
            case 2:
                System.out.println("Error: El socio ya tiene el máximo de libros permitidos (5).");
                break;
            case 3:
                System.out.println("Error: No existe ningún libro con ese ISBN.");
                break;
            case 4:
                System.out.println("Error: El libro no está disponible actualmente.");
                break;
            default:
                System.out.println("Error desconocido al prestar el libro.");
        }
    }

    private static void devolverLibro() {
        System.out.println("\n--- DEVOLVER LIBRO ---");
        System.out.print("Número de socio: ");
        int numeroSocio;
        try {
            numeroSocio = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El número de socio debe ser un valor numérico.");
            return;
        }

        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine().trim();

        int resultado = biblioteca.devolverLibro(numeroSocio, isbn);
        switch (resultado) {
            case 0:
                System.out.println("¡Libro devuelto correctamente!");
                break;
            case 1:
                System.out.println("Error: No existe ningún socio con ese número.");
                break;
            case 2:
                System.out.println("Error: No existe ningún libro con ese ISBN.");
                break;
            case 3:
                System.out.println("Error: El libro ya está disponible.");
                break;
            case 4:
                System.out.println("Error: El libro no fue prestado a este socio.");
                break;
            default:
                System.out.println("Error desconocido al devolver el libro.");
        }
    }

    private static void mostrarLibrosDisponibles() {
        System.out.println("\n--- LIBROS DISPONIBLES ---");
        List<Libro> librosDisponibles = biblioteca.getLibrosDisponibles();

        if (librosDisponibles.isEmpty()) {
            System.out.println("No hay libros disponibles actualmente.");
            return;
        }

        for (Libro libro : librosDisponibles) {
            System.out.println(libro.toString());
        }
    }

    private static void mostrarLibrosNoDisponibles() {
        System.out.println("\n--- LIBROS NO DISPONIBLES ---");
        List<Libro> librosNoDisponibles = biblioteca.getLibrosNoDisponibles();

        if (librosNoDisponibles.isEmpty()) {
            System.out.println("Todos los libros están disponibles actualmente.");
            return;
        }

        for (Libro libro : librosNoDisponibles) {
            Socio socio = libro.getSocioAsignado();
            System.out.println(libro.toString() + " " + socio.toString());
        }
    }

    private static void mostrarLibrosDeSocio() {
        System.out.println("\n--- LIBROS DE UN SOCIO ---");
        System.out.print("Número de socio: ");
        int numeroSocio;
        try {
            numeroSocio = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El número de socio debe ser un valor numérico.");
            return;
        }

        List<Libro> librosDelSocio = biblioteca.getLibrosDeSocio(numeroSocio);
        if (librosDelSocio == null) {
            System.out.println("Error: No existe ningún socio con ese número.");
            return;
        }

        if (librosDelSocio.isEmpty()) {
            System.out.println("Este socio no tiene ningún libro en préstamo actualmente.");
            return;
        }

        for (Libro libro : librosDelSocio) {
            System.out.println(libro.toString());
        }
    }
}

// Clase para gestionar los libros
class Libro {
    private String isbn;
    private String titulo;
    private String genero;
    private int numeroPaginas;
    private boolean disponible;
    private Socio socioAsignado;

    public Libro(String isbn, String titulo, String genero, int numeroPaginas) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.genero = genero;
        this.numeroPaginas = numeroPaginas;
        this.disponible = true;
        this.socioAsignado = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Socio getSocioAsignado() {
        return socioAsignado;
    }

    public void setSocioAsignado(Socio socioAsignado) {
        this.socioAsignado = socioAsignado;
        this.disponible = (socioAsignado == null);
    }

    @Override
    public String toString() {
        return "ISBN " + isbn + " Título " + titulo + " Género " + genero + " Número de páginas " + numeroPaginas;
    }
}

// Clase para gestionar los socios
class Socio {
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private LocalDate fechaNacimiento;
    private int numeroSocio;
    private List<Libro> librosReservados;

    public Socio(String nombre, String apellidos, String dni, String email, LocalDate fechaNacimiento, int numeroSocio) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroSocio = numeroSocio;
        this.librosReservados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public List<Libro> getLibrosReservados() {
        return librosReservados;
    }

    public boolean agregarLibro(Libro libro) {
        if (librosReservados.size() >= 5) {
            return false;
        }
        librosReservados.add(libro);
        return true;
    }

    public boolean eliminarLibro(Libro libro) {
        return librosReservados.remove(libro);
    }

    public boolean tieneLibro(Libro libro) {
        return librosReservados.contains(libro);
    }

    public int getNumeroLibrosReservados() {
        return librosReservados.size();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Número de socio " + numeroSocio + " " + nombre + " " + apellidos + " " +
                email + " DNI " + dni + " " + fechaNacimiento.format(formatter);
    }
}

// Clase para gestionar la biblioteca
class Biblioteca {
    private static final int MAX_SOCIOS = 1000;
    private static final int MAX_LIBROS = 5000;
    private static final int MAX_LIBROS_POR_SOCIO = 5;

    private List<Socio> socios;
    private List<Libro> libros;
    private int contadorSocios;

    public Biblioteca() {
        this.socios = new ArrayList<>();
        this.libros = new ArrayList<>();
        this.contadorSocios = 1; // Empezamos desde 1 para los números de socio
    }

    public boolean crearSocio(String nombre, String apellidos, String dni, String email, LocalDate fechaNacimiento) {
        if (socios.size() >= MAX_SOCIOS) {
            return false;
        }

        // Verificar si ya existe un socio con el mismo DNI
        for (Socio socio : socios) {
            if (socio.getDni().equalsIgnoreCase(dni)) {
                return false;
            }
        }

        Socio nuevoSocio = new Socio(nombre, apellidos, dni, email, fechaNacimiento, contadorSocios++);
        socios.add(nuevoSocio);
        return true;
    }

    public boolean crearLibro(String isbn, String titulo, String genero, int numeroPaginas) {
        if (libros.size() >= MAX_LIBROS) {
            return false;
        }

        // Verificar si ya existe un libro con el mismo ISBN
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return false;
            }
        }

        Libro nuevoLibro = new Libro(isbn, titulo, genero, numeroPaginas);
        libros.add(nuevoLibro);
        return true;
    }

    public int prestarLibro(int numeroSocio, String isbn) {
        Socio socio = buscarSocio(numeroSocio);
        if (socio == null) {
            return 1; // Socio no encontrado
        }

        if (socio.getNumeroLibrosReservados() >= MAX_LIBROS_POR_SOCIO) {
            return 2; // Socio ya tiene 5 libros
        }

        Libro libro = buscarLibro(isbn);
        if (libro == null) {
            return 3; // Libro no encontrado
        }

        if (!libro.isDisponible()) {
            return 4; // Libro no disponible
        }

        libro.setSocioAsignado(socio);
        socio.agregarLibro(libro);
        return 0; // Operación exitosa
    }

    public int devolverLibro(int numeroSocio, String isbn) {
        Socio socio = buscarSocio(numeroSocio);
        if (socio == null) {
            return 1; // Socio no encontrado
        }

        Libro libro = buscarLibro(isbn);
        if (libro == null) {
            return 2; // Libro no encontrado
        }

        if (libro.isDisponible()) {
            return 3; // El libro ya está disponible
        }

        if (libro.getSocioAsignado().getNumeroSocio() != numeroSocio) {
            return 4; // El libro no está prestado a este socio
        }

        libro.setSocioAsignado(null);
        socio.eliminarLibro(libro);
        return 0; // Operación exitosa
    }

    public List<Libro> getLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isDisponible()) {
                disponibles.add(libro);
            }
        }
        return disponibles;
    }

    public List<Libro> getLibrosNoDisponibles() {
        List<Libro> noDisponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (!libro.isDisponible()) {
                noDisponibles.add(libro);
            }
        }
        return noDisponibles;
    }

    public List<Libro> getLibrosDeSocio(int numeroSocio) {
        Socio socio = buscarSocio(numeroSocio);
        if (socio == null) {
            return null;
        }
        return socio.getLibrosReservados();
    }

    public Socio buscarSocio(int numeroSocio) {
        for (Socio socio : socios) {
            if (socio.getNumeroSocio() == numeroSocio) {
                return socio;
            }
        }
        return null;
    }

    public Libro buscarLibro(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
}