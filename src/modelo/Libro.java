package modelo;

public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String editorial;
    private GeneroLibro genero;
    private int totalCopias;
    private int copiasDisponibles;
    private EstadoLibro estado;

    public Libro(String isbn, String titulo, String autor, int anioPublicacion,
                 String editorial, GeneroLibro genero, int totalCopias) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        if (totalCopias <= 0) {
            throw new IllegalArgumentException("El número de copias debe ser mayor que 0");
        }
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.genero = genero;
        this.totalCopias = totalCopias;
        this.copiasDisponibles = totalCopias;
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public GeneroLibro getGenero() {
        return genero;
    }

    public int getTotalCopias() {
        return totalCopias;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    private void actualizarEstado() {
        if (copiasDisponibles > 0) {
            if (estado != EstadoLibro.RESERVADO) {
                estado = EstadoLibro.DISPONIBLE;
            }
        } else {
            estado = EstadoLibro.PRESTADO;
        }
    }

    public boolean estaDisponible() {
        return copiasDisponibles > 0 && estado == EstadoLibro.DISPONIBLE;
    }

    public void prestarEjemplar() {
        if (copiasDisponibles <= 0) {
            throw new IllegalStateException("No hay copias disponibles para prestar");
        }
        copiasDisponibles--;
        actualizarEstado();
    }

    public void devolverEjemplar() {
        if (copiasDisponibles >= totalCopias) {
            throw new IllegalStateException("No se pueden devolver más copias de las que existen");
        }
        copiasDisponibles++;
        actualizarEstado();
    }

    public void reservar() {
        if (estado == EstadoLibro.PRESTADO && copiasDisponibles == 0) {
            estado = EstadoLibro.RESERVADO;
        } else if (estado == EstadoLibro.DISPONIBLE) {
            estado = EstadoLibro.RESERVADO;
        } else {
            throw new IllegalStateException("No se puede reservar este libro en su estado actual");
        }
    }

    public void quitarReserva() {
        if (estado == EstadoLibro.RESERVADO) {
            actualizarEstado();
        }
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero=" + genero +
                ", totalCopias=" + totalCopias +
                ", copiasDisponibles=" + copiasDisponibles +
                ", estado=" + estado +
                '}';
    }
}
