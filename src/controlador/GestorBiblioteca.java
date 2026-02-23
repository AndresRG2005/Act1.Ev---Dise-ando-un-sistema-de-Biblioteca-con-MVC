package controlador;

import excepciones.LibroNoDisponibleException;
import excepciones.LimitePrestamosExcedidoException;
import modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorBiblioteca {

    private List<Libro> libros;
    private List<Usuario> usuarios;
    private static final int DIAS_MAX_PRESTAMO = 30;

    public GestorBiblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        if (libro == null) {
            throw new NullPointerException("El libro no puede ser nulo");
        }
        libros.add(libro);
    }

    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new NullPointerException("El usuario no puede ser nulo");
        }
        usuarios.add(usuario);
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        return libros.stream()
                .filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> buscarLibrosPorGenero(GeneroLibro genero) {
        return libros.stream()
                .filter(l -> l.getGenero() == genero)
                .collect(Collectors.toList());
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void prestarLibro(String isbn, String idUsuario)
            throws LibroNoDisponibleException, LimitePrestamosExcedidoException {

        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("No existe un libro con ese ISBN");
        }

        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese ID");
        }

        LocalDate hoy = LocalDate.now();

        if (usuario.estaBloqueadoParaLibro(isbn, hoy)) {
            throw new IllegalArgumentException("El usuario está bloqueado para este libro durante 7 días tras la devolución");
        }

        if (!usuario.puedePedirMasLibros()) {
            throw new LimitePrestamosExcedidoException("El usuario ya tiene 3 libros prestados");
        }

        if (!libro.estaDisponible()) {
            throw new LibroNoDisponibleException("El libro no está disponible para préstamo");
        }

        libro.prestarEjemplar();
        Prestamo prestamo = new Prestamo(libro, usuario, hoy, DIAS_MAX_PRESTAMO);
        usuario.agregarPrestamo(prestamo);
    }

    public void devolverLibro(String isbn, String idUsuario) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("No existe un libro con ese ISBN");
        }

        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese ID");
        }

        Prestamo prestamo = usuario.buscarPrestamoActivoPorIsbn(isbn);
        if (prestamo == null) {
            throw new IllegalArgumentException("El usuario no tiene este libro prestado");
        }

        LocalDate hoy = LocalDate.now();
        usuario.cerrarPrestamo(prestamo, hoy);
        libro.devolverEjemplar();
        libro.quitarReserva(); 
    }

    public void reservarLibro(String isbn, String idUsuario) throws LibroNoDisponibleException {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("No existe un libro con ese ISBN");
        }

        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese ID");
        }

        if (libro.getEstado() == EstadoLibro.RESERVADO) {
            throw new LibroNoDisponibleException("El libro ya está reservado");
        }

        libro.reservar();
    }

    public Usuario obtenerUsuarioConLibroPrestado(String isbn) {
        for (Usuario u : usuarios) {
            Prestamo p = u.buscarPrestamoActivoPorIsbn(isbn);
            if (p != null) {
                return u;
            }
        }
        return null;
    }

    public List<Libro> obtenerTodosLosLibros() {
        return new ArrayList<>(libros);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(usuarios);
    }
}
