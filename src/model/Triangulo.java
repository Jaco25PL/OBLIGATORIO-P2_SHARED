/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Triangulo {

    private Punto punto1;
    private Punto punto2;
    private Punto punto3;
    private Jugador jugadorGanador; 
    private boolean isWhitePlayer; // true if the winner is the white player

    // crea un nuevo triangulo.
    public Triangulo(Punto p1, Punto p2, Punto p3) {
        if (p1 == null || p2 == null || p3 == null) {
            throw new NullPointerException("Los puntos de un triángulo no pueden ser nulos.");
        }
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Puntos deben ser distintos.");
        }
        // Ordenar puntos para consistencia en equals
        Punto[] puntos = {p1, p2, p3};
        Arrays.sort(puntos, (pt1, pt2) -> {
            if (pt1.getFila() != pt2.getFila()) {
                return Integer.compare(pt1.getFila(), pt2.getFila());
            }
            return Character.compare(pt1.getColumna(), pt2.getColumna());
        });
        this.punto1 = puntos[0];
        this.punto2 = puntos[1];
        this.punto3 = puntos[2];
        this.jugadorGanador = null; 
    }

    // obtiene el primer punto.
    public Punto getPunto1() {
        return punto1;
    }

    // obtiene el segundo punto.
    public Punto getPunto2() {
        return punto2;
    }

    // obtiene el tercer punto.
    public Punto getPunto3() {
        return punto3;
    }

    // obtiene jugador ganador.
    public Jugador getJugadorGanador() {
        return jugadorGanador;
    }

    // establece jugador ganador.
    public void setJugadorGanador(Jugador jugadorGanador) {
        this.jugadorGanador = jugadorGanador;
    }

    // establece jugador ganador y si es jugador blanco.
    public void setJugadorGanador(Jugador jugador, boolean isWhitePlayer) {
        this.jugadorGanador = jugador;
        this.isWhitePlayer = isWhitePlayer;
    }

    // obtiene si es jugador blanco.
    public boolean isWhitePlayer() {
        return isWhitePlayer;
    }

    // verifica si contiene punto.
    public boolean contienePunto(Punto p) {
        if (p == null) {
            return false;
        }
        return p.equals(punto1) || p.equals(punto2) || p.equals(punto3);
    }

    // compara este triangulo con otro.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangulo otro = (Triangulo) o;

        // Compara los puntos ordenados
        return this.punto1.equals(otro.punto1) &&
               this.punto2.equals(otro.punto2) &&
               this.punto3.equals(otro.punto3);
    }

    // genera código hash.
    @Override
    public int hashCode() {
        Set<Punto> misPuntos = new HashSet<>(Arrays.asList(this.punto1, this.punto2, this.punto3));
        return misPuntos.hashCode();
    }

    // devuelve representación textual.
    @Override
    public String toString() {
        String ganadorStr = (jugadorGanador != null) ?
                "ganado por [" + jugadorGanador.getNombre() + "]" : "(No ganado)";
        return "Triángulo [" + punto1 + ", " + punto2 + ", " + punto3 + "] " + ganadorStr;
    }
}