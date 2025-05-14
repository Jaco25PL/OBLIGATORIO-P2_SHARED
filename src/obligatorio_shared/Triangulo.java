/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */
package obligatorio_shared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un triángulo elemental (lado 1) formado por tres puntos en el tablero.
 * Puede ser "ganado" por un jugador.
 * La identidad de un triángulo se basa en sus tres puntos vértices, independientemente del orden.
 */
public class Triangulo {

    private final Punto punto1;
    private final Punto punto2;
    private final Punto punto3;
    private Jugador jugadorGanador; // null si no ha sido ganado

    /**
     * Constructor para crear un nuevo Triangulo.
     * Los puntos no deben ser nulos.
     *
     * @param p1 El primer punto vértice del triángulo.
     * @param p2 El segundo punto vértice del triángulo.
     * @param p3 El tercer punto vértice del triángulo.
     * @throws NullPointerException si alguno de los puntos es nulo.
     */
    public Triangulo(Punto p1, Punto p2, Punto p3) {
        if (p1 == null || p2 == null || p3 == null) {
            throw new NullPointerException("Los puntos de un triángulo no pueden ser nulos.");
        }
        // Para asegurar consistencia en equals y hashCode, podríamos ordenarlos,
        // pero para la lógica del juego, solo necesitamos que sean distintos.
        // La consigna no especifica que deban ser distintos, pero un triángulo degenerado
        // (puntos colineales o iguales) no suele ser el objetivo.
        // Por ahora, se asume que la lógica de detección de triángulos proveerá puntos válidos.
        this.punto1 = p1;
        this.punto2 = p2;
        this.punto3 = p3;
        this.jugadorGanador = null; // Inicialmente no ganado
    }

    // --- Getters ---

    public Punto getPunto1() {
        return punto1;
    }

    public Punto getPunto2() {
        return punto2;
    }

    public Punto getPunto3() {
        return punto3;
    }

    public Jugador getJugadorGanador() {
        return jugadorGanador;
    }

    // --- Setter ---

    /**
     * Establece el jugador que ha ganado este triángulo.
     * @param jugadorGanador El jugador que ganó el triángulo. Puede ser null si se "desgana".
     */
    public void setJugadorGanador(Jugador jugadorGanador) {
        this.jugadorGanador = jugadorGanador;
    }

    // --- Métodos Adicionales ---

    /**
     * Verifica si el punto dado es uno de los vértices de este triángulo.
     * @param p El punto a verificar.
     * @return true si el punto es uno de los vértices, false en caso contrario.
     */
    public boolean contienePunto(Punto p) {
        if (p == null) {
            return false;
        }
        return p.equals(punto1) || p.equals(punto2) || p.equals(punto3);
    }

    // --- equals, hashCode y toString ---

    /**
     * Compara este Triangulo con otro objeto para ver si son lógicamente iguales.
     * Dos triángulos son iguales si están formados por los mismos tres puntos,
     * independientemente del orden en que se especificaron los puntos.
     * El jugador ganador NO se considera para la igualdad de la estructura del triángulo.
     * @param o El objeto a comparar.
     * @return true si son iguales (mismos tres puntos), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangulo otro = (Triangulo) o;

        // Para comparar sin importar el orden, metemos los puntos en un Set
        // y comparamos los Sets. Los puntos deben tener equals/hashCode bien implementados.
        Set<Punto> misPuntos = new HashSet<>(Arrays.asList(this.punto1, this.punto2, this.punto3));
        Set<Punto> otrosPuntos = new HashSet<>(Arrays.asList(otro.punto1, otro.punto2, otro.punto3));

        return misPuntos.equals(otrosPuntos);
    }

    /**
     * Genera un código hash para el Triangulo.
     * El hash se basa en los tres puntos vértices, de forma que el orden no importe,
     * para ser consistente con equals().
     * El jugador ganador NO se considera para el hashCode.
     * @return El código hash entero.
     */
    @Override
    public int hashCode() {
        // Para un hashCode independiente del orden, podemos sumar los hashCodes de los puntos,
        // ya que la suma es conmutativa.
        // O usar el hashCode del Set de puntos, que es más robusto.
        Set<Punto> misPuntos = new HashSet<>(Arrays.asList(this.punto1, this.punto2, this.punto3));
        return misPuntos.hashCode();
    }

    /**
     * Devuelve una representación textual del Triangulo.
     * Ejemplo: "Triángulo en [D1, E2, F1] ganado por [JugadorX]" o
     * "Triángulo en [A4, B3, C4] (No ganado)"
     * @return Un String representando el triángulo.
     */
    @Override
    public String toString() {
        String ganadorStr;
        if (jugadorGanador != null) {
            ganadorStr = "ganado por [" + jugadorGanador.getNombre() + "]";
        } else {
            ganadorStr = "(No ganado)";
        }
        return "Triángulo en [" + punto1.toString() +
               ", " + punto2.toString() +
               ", " + punto3.toString() +
               "] " + ganadorStr;
    }
}
