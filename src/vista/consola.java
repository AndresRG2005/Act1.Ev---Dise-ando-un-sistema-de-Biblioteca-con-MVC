package vista;

import controlador.GestorBiblioteca;
import excepciones.LibroNoDisponibleException;
import excepciones.LimitePrestamosExcedidoException;
import modelo.GeneroLibro;
import modelo.Libro;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class Consola {

    private GestorBiblioteca gestor;
    private Scanner scanner;

    public Consola(GestorBiblioteca gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("===== BIBLIOTECA =====");
            System.out.println("1. Listar libros");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Reservar libro");
            System.out.println("6. Buscar libro por título");
            System.out.println("7. Buscar libro por ISBN");
            System.out.println("8. Buscar libros por género");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarLibros();
                case 2 -> listarUsuarios();
                case 3 -> prestarLibro();
                case 4 -> devolverLibro();
                case 5 -> reservarLibro();
                case 6 -> buscarPorTitulo();
                case 7 -> buscarPorIsbn();
                case 8 -> buscarPorGenero();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida");
            }

            System.out.println();
        } while (opcion != 0);
    }

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.print("Introduce un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private void listarLibros() {
        List<Libro> libros = gestor.obtenerTodosLosLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = gestor.obtenerTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            usuarios.forEach(u -> {
                System.out.println(u);
                u.getPrestamosActuales().forEach(p ->
                        System.out.println("   - " + p.getLibro().getTitulo() + " (vence: " + p.getFechaVencimiento() + ")"));
            });
        }
    }

    private void prestarLibro() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();

        try {
            gestor.prestarLibro(isbn, idUsuario);
            System.out.println("Préstamo realizado correctamente.");
        } catch (LibroNoDisponibleException | LimitePrestamosExcedidoException | IllegalArgumentException e) {
            System.out.println("Error al prestar libro: " + e.getMessage());
        }
    }

    private void devolverLibro() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();

        try {
            gestor.devolverLibro(isbn, idUsuario);
            System.out.println("Devolución realizada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al devolver libro: " + e.getMessage());
        }
    }

    private void reservarLibro() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();

        try {
            gestor.reservarLibro(isbn, idUsuario);
            System.out.println("Reserva realizada correctamente.");
        } catch (LibroNoDisponibleException | IllegalArgumentException e) {
            System.out.println("Error al reservar libro: " + e.getMessage());
        }
    }

    private void buscarPorTitulo() {
        System.out.print("Título (o parte): ");
        String titulo = scanner.nextLine();
        List<Libro> resultados = gestor.buscarLibrosPorTitulo(titulo);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            resultados.forEach(System.out::println);
        }
    }

    private void buscarPorIsbn() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        Libro libro = gestor.buscarLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("No se encontró un libro con ese ISBN.");
        } else {
            System.out.println(libro);
            var usuario = gestor.obtenerUsuarioConLibroPrestado(isbn);
            if (usuario != null) {
                System.out.println("Actualmente lo tiene prestado: " + usuario.getNombre());
            }
        }
    }

    private void buscarPorGenero() {
        System.out.println("Géneros disponibles:");
        for (GeneroLibro g : GeneroLibro.values()) {
            System.out.println("- " + g.name());
        }
        System.out.print("Introduce el género exactamente como aparece: ");
        String generoStr = scanner.nextLine();

        try {
            GeneroLibro genero = GeneroLibro.valueOf(generoStr.toUpperCase());
            List<Libro> resultados = gestor.buscarLibrosPorGenero(genero);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron libros de ese género.");
            } else {
                resultados.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Género no válido.");
        }
    }
}
