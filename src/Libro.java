package biblioteca.modelo;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String editorial;
    private Genero genero;
    private int copiasTotales;
    private int copiasDisponibles;
    private EstadoLibro estado;

   public Libro(String isbn, String titulo, String autor,
                 int anioPublicacion, String editorial,
                 Genero genero, int copiasTotales) {
    }
}