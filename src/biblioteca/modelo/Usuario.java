package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<Prestamo> prestamosActuales;
    private List<Prestamo> historialPrestamos;

    public Usuario(String id, String nombre) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID de usuario no puede ser vacío");
        }
        this.id = id;
        this.nombre = nombre;
        this.prestamosActuales = new ArrayList<>();
        this.historialPrestamos = new ArrayList<>();
    }

    public Object getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public List<Libro> getPrestamosActuales() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrestamosActuales'");
    }
}
