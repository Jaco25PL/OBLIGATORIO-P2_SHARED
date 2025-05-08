/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */
package obligatorio_shared;

// Ya no es estrictamente necesario importar java.util.Objects si no se usa requireNonNull o hash
// import java.util.Objects;

/**
 * Representa a un jugador registrado en el sistema.
 * Cada jugador tiene un nombre, un username único, edad y estadísticas de juego.
 * El nombre, username y edad se consideran inmutables una vez creado el jugador.
 */
public class Jugador {

    private final String nombre;
    private final String username; // Identificador único
    private final int edad;

    private int partidasGanadas;
    private int rachaActualVictorias;
    private int mejorRachaVictorias;

    /**
     * Constructor para crear un nuevo Jugador.
     *
     * @param nombre El nombre real del jugador (no nulo, no vacío).
     * @param username El nombre de usuario único para el jugador (no nulo, no vacío).
     * @param edad La edad del jugador (debe ser mayor o igual a 0).
     * @throws NullPointerException si nombre o username son nulos.
     * @throws IllegalArgumentException si nombre o username están vacíos, o si la edad es negativa.
     */
    public Jugador(String nombre, String username, int edad) {
        // Validación de entradas (forma tradicional)
        if (nombre == null) {
            throw new NullPointerException("El nombre no puede ser nulo.");
        }
        if (username == null) {
            throw new NullPointerException("El username no puede ser nulo.");
        }
        // Validación de no vacíos (después de saber que no son nulos)
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (username.trim().isEmpty()) {
             throw new IllegalArgumentException("El username no puede estar vacío."); // Corregido
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }

        // Asignación a atributos finales
        this.nombre = nombre;
        this.username = username;
        this.edad = edad;

        // Inicializa contadores
        this.partidasGanadas = 0;
        this.rachaActualVictorias = 0;
        this.mejorRachaVictorias = 0;
    }

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public String getUsername() {
        return username;
    }

    public int getEdad() {
        return edad;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getRachaActualVictorias() {
        return rachaActualVictorias;
    }

    public int getMejorRachaVictorias() {
        return mejorRachaVictorias;
    }

    // --- Métodos para actualizar estadísticas ---

    /**
     * Registra que el jugador ha ganado una partida.
     * Incrementa las partidas ganadas y actualiza las rachas de victorias.
     */
    public void incrementarPartidasGanadas() {
        this.partidasGanadas++;
        this.rachaActualVictorias++;
        if (this.rachaActualVictorias > this.mejorRachaVictorias) {
            this.mejorRachaVictorias = this.rachaActualVictorias;
        }
    }

    /**
     * Incrementa la racha actual de victorias del jugador.
     * Nota: Este método es independiente de incrementarPartidasGanadas.
     * Si se llama después de incrementarPartidasGanadas, la racha se incrementará dos veces.
     */
    public void incrementarRachaActual() {
        this.rachaActualVictorias++;
    }

    /**
     * Actualiza la mejor racha de victorias si la racha actual es mayor.
     * Nota: Este método es independiente de incrementarPartidasGanadas.
     * Se recomienda que la lógica de actualizar la mejor racha esté contenida
     * dentro del método que incrementa la racha actual (como en incrementarPartidasGanadas).
     */
    public void actualizarRachaMaxima() {
        if (this.rachaActualVictorias > this.mejorRachaVictorias) {
            this.mejorRachaVictorias = this.rachaActualVictorias;
        }
    }

    /**
     * Reinicia la racha actual de victorias a cero.
     * Se debe llamar cuando el jugador pierde una partida o la partida termina en empate.
     */
    public void resetRachaActual() { // Renamed from resetearRachaActual
        this.rachaActualVictorias = 0;
    }

    // --- equals y hashCode (Basados en el username) ---

    /**
     * Compara este Jugador con otro objeto para ver si son lógicamente iguales.
     * Dos jugadores son iguales si tienen el mismo username (sensible a mayúsculas/minúsculas).
     * @param o El objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (this == o) {
            sonIguales = true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Jugador otroJugador = (Jugador) o;
            // La igualdad se basa únicamente en el username
            if (this.username != null) {
                 sonIguales = this.username.equals(otroJugador.username);
            } else {
                 sonIguales = (otroJugador.username == null);
            }
        }
        return sonIguales;
    }

    /**
     * Genera un código hash para el Jugador, basado únicamente en su username.
     * Es consistente con equals(): jugadores iguales tendrán el mismo hashCode.
     * @return El código hash entero.
     */
    @Override
    public int hashCode() {
        // Si username es null, el hash es 0. Si no, usa el hash del String.
        return (username == null) ? 0 : username.hashCode();
    }

    // --- toString ---

    /**
     * Devuelve una representación textual del Jugador, incluyendo sus estadísticas.
     * @return Un String con el formato "Username [Nombre] (Edad: X, Victorias: Y, Racha Actual: Z, Mejor Racha: W)".
     */
    @Override
    public String toString() {
        return username + " [" + nombre + "] (Edad: " + edad
                + ", Victorias: " + partidasGanadas
                + ", Racha Actual: " + rachaActualVictorias
                + ", Mejor Racha: " + mejorRachaVictorias + ")";
    }

}
