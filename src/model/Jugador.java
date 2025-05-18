/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class Jugador {

    private String nombre; 
    private int edad;
    private int partidasGanadas;
    private int rachaActualVictorias;
    private int mejorRachaVictorias;

    // Crea nuevo jugador.
    public Jugador(String nombre, int edad) {
        if (nombre == null) {
            throw new NullPointerException("Nombre no puede ser nulo.");
        }
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío.");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("Edad no puede ser negativa.");
        }

        this.nombre = nombre;
        this.edad = edad;

        this.partidasGanadas = 0;
        this.rachaActualVictorias = 0;
        this.mejorRachaVictorias = 0;
    }

    // Obtiene el nombre.
    public String getNombre() {
        return nombre;
    }

    // Obtiene la edad.
    public int getEdad() {
        return edad;
    }

    // Obtiene partidas ganadas.
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    // Obtiene racha actual.
    public int getRachaActualVictorias() {
        return rachaActualVictorias;
    }

    // Obtiene mejor racha.
    public int getMejorRachaVictorias() {
        return mejorRachaVictorias;
    }

    // Incrementa partidas ganadas.
    public void incrementarPartidasGanadas() {
        this.partidasGanadas++;
    }

    // Incrementa racha actual.
    public void incrementarRachaActual() {
        this.rachaActualVictorias++;
    }

    // Actualiza mejor racha.
    public void actualizarRachaMaxima() {
        if (this.rachaActualVictorias > this.mejorRachaVictorias) {
            this.mejorRachaVictorias = this.rachaActualVictorias;
        }
    }

    // Reinicia racha actual.
    public void resetRachaActual() { 
        this.rachaActualVictorias = 0;
    }

    // Compara con otro jugador.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return nombre.equals(jugador.nombre);
    }

    // Representación textual jugador.
    @Override
    public String toString() {
        return nombre + " (Edad: " + edad +
               ", Victorias: " + partidasGanadas +
               ", Racha: " + rachaActualVictorias +
               ", Mejor Racha: " + mejorRachaVictorias + ")";
    }

}