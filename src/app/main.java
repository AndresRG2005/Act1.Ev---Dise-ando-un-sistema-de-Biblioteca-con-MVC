package app;

import controlador.GestorBiblioteca;
import modelo.GeneroLibro;
import modelo.Libro;
import modelo.Usuario;
import vista.Consola;

public class Main {

    public static void main(String[] args) {
        GestorBiblioteca gestor = new GestorBiblioteca();

        
        cargarDatosDePrueba(gestor);

        Consola consola = new Consola(gestor);
        consola.mostrarMenuPrincipal();
    }

    private static void cargarDatosDePrueba(GestorBiblioteca gestor) {
        Libro l1 = new Libro("1111", "El Señor de los Anillos", "J.R.R. Tolkien",
                1954, "Minotauro", GeneroLibro.FANTASIA, 3);
        Libro l2 = new Libro("2222", "1984", "George Orwell",
                1949, "Secker & Warburg", GeneroLibro.CIENCIA_FICCION, 2);
        Libro l3 = new Libro("3333", "Historia de España", "Varios",
                2000, "Anaya", GeneroLibro.HISTORIA, 1);

        gestor.agregarLibro(l1);
        gestor.agregarLibro(l2);
        gestor.agregarLibro(l3);

        Usuario u1 = new Usuario("u1", "Ana Pérez");
        Usuario u2 = new Usuario("u2", "Luis García");

        gestor.agregarUsuario(u1);
        gestor.agregarUsuario(u2);
    }
}
