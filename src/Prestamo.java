package biblioteca.modelo;

import java.time.LocalDate;

public class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private LocalDate fechaDevolucion;

    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaPrestamo.plusDays(30);
    }

    public boolean estaVencido() {
        return fechaDevolucion == null && LocalDate.now().isAfter(fechaVencimiento);
    }

    public void marcarDevuelto(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
