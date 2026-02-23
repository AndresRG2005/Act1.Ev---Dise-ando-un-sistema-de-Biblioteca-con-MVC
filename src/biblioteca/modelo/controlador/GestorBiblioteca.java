package biblioteca.controlador;

import biblioteca.modelo.*;
import biblioteca.excepciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorBiblioteca {

    private List<Libro> libros;
    private List<Usuario> usuarios;

    public GestorBiblioteca() {
        this.libros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void prestarLibro(String isbn, String idUsuario) {
        Libro libro = buscarLibroPorIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        Usuario usuario = buscarUsuarioPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (libro.getCopiasDisponibles() <= 0) {
            throw new LibroNoDisponibleException("No hay copias disponibles");
        }

        Prestamo prestamo = new Prestamo(libro, usuario, LocalDate.now());
        usuario.getPrestamosActuales().add(prestamo);
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - 1);
        actualizarEstadoLibro(libro);
    }

    public void devolverLibro(String isbn, String idUsuario) {
    }

    public Optional<Libro> buscarLibroPorIsbn(String isbn) {
        return libros.stream().filter(l -> l.getIsbn().equals(isbn)).findFirst();
    }

    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    private void actualizarEstadoLibro(Libro libro) {
        if (libro.getCopiasDisponibles() == 0) {
            libro.setEstado(EstadoLibro.PRESTADO);
        } else {
            libro.setEstado(EstadoLibro.DISPONIBLE);
        }

    if (usuario.getPrestamosActuales().size() >= 3) {
        throw new LimitePrestamosExcedidoException("El usuario ya tiene 3 libros prestados");
    }

    boolean bloqueado = usuario.getHistorialPrestamos().stream() 
        .filter(p -> p.getLibro().getIsbn().equals(isbn))
        .anyMatch(p -> p.getFechaDevolucion() != null &&
        p.getFechaDevolucion().plusDays(7).isAfter(LocalDate.now()));
        
    if (bloqueado) {
        throw new IllegalArgumentException("Debe esperar 7 días para volver a pedir este libro");
    }
    
    }
}
