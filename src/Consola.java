package biblioteca.vista;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Usuario;

import java.util.List;

public class Consola {

    public void mostrarLibros(List<Libro> libros) {
        System.out.println("=== Libros ===");
        for (Libro l : libros) {
            System.out.println(l);
        }
    }
}
