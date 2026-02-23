package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {

    private String id;
    private String nombre;
    private List<Prestamo> prestamosActuales;
    private List<Prestamo> historialPrestamos;
    private Map<String, LocalDate> ultimaDevolucionPorIsbn;

    public Usuario(String id, String nombre) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío");
        }
        this.id = id;
        this.nombre = nombre;
        this.prestamosActuales = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
        this.ultimaDevolucionPorIsbn = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Prestamo> getPrestamosActuales() {
        return prestamosActuales;
    }

    public List<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }

    public boolean puedePedirMasLibros() {
        return prestamosActuales.size() < 3;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamosActuales.add(prestamo);
    }

    public void cerrarPrestamo(Prestamo prestamo, LocalDate fechaDevolucion) {
        prestamo.marcarDevuelto(fechaDevolucion);
        prestamosActuales.remove(prestamo);
        historialPrestamos.add(prestamo);
        String isbn = prestamo.getLibro().getIsbn();
        ultimaDevolucionPorIsbn.put(isbn, fechaDevolucion);
    }

    public boolean estaBloqueadoParaLibro(String isbn, LocalDate hoy) {
        LocalDate ultimaDevolucion = ultimaDevolucionPorIsbn.get(isbn);
        if (ultimaDevolucion == null) {
            return false;
        }
        LocalDate fechaFinBloqueo = ultimaDevolucion.plusDays(7);
        return !hoy.isAfter(fechaFinBloqueo);
    }

    public Prestamo buscarPrestamoActivoPorIsbn(String isbn) {
        for (Prestamo p : prestamosActuales) {
            if (p.getLibro().getIsbn().equals(isbn) && p.isActivo()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", prestamosActuales=" + prestamosActuales.size() +
                '}';
    }
}
