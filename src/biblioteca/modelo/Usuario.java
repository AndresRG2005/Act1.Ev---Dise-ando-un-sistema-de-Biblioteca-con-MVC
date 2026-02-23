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
}
