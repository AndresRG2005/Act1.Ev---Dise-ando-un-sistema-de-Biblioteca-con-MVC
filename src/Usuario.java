package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id;
    private String nombre;
    private List<Prestamo> prestamosActuales;
    private List<Prestamo> historialPrestamos;
}
