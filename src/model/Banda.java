/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class Banda {

    private Punto puntoA;
    private Punto puntoB;
    private Jugador jugador;

    // crea una nueva banda.
    public Banda(Punto pA, Punto pB, Jugador jugador) {
        if (pA == null || pB == null || jugador == null) {
            throw new NullPointerException("Puntos o jugador nulos.");
        }
        if (pA.equals(pB)) {
            throw new IllegalArgumentException("Puntos no pueden ser iguales.");
        }
        if (!sonAdyacentes(pA, pB)) {
            throw new IllegalArgumentException("Puntos no son adyacentes.");
        }
        this.jugador = jugador;

        if (pA.getFila() < pB.getFila() || (pA.getFila() == pB.getFila() && pA.getColumna() < pB.getColumna())) {
            this.puntoA = pA;
            this.puntoB = pB;
        } else {
            this.puntoA = pB;
            this.puntoB = pA;
        }
    }

    // verifica si puntos son adyacentes.
    public boolean sonAdyacentes(Punto p1, Punto p2) {
        int diffFilas = Math.abs(p1.getFila() - p2.getFila());
        int diffCols = Math.abs(p1.getColumna() - p2.getColumna());
        boolean adyacentes = (diffFilas == 0 && diffCols == 2) || (diffFilas == 1 && diffCols == 1);
        return adyacentes;
    }

    // obtiene el primer punto.
    public Punto getPuntoA() {
        return puntoA;
    }

    // obtiene el segundo punto.
    public Punto getPuntoB() {
        return puntoB;
    }

    // obtiene el jugador.
    public Jugador getJugador() {
        return jugador;
    }

    //compara esta banda con otra.
    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (this == o) {
            sonIguales = true;
        } else if (o != null && getClass() == o.getClass()) {
            Banda otraBanda = (Banda) o;
            sonIguales = puntoA.equals(otraBanda.puntoA) && puntoB.equals(otraBanda.puntoB);
        }
        return sonIguales;
    }

    // devuelve representación textual de banda.
    @Override
    public String toString() {
        return "Banda [A: " + puntoA + ", B: " + puntoB + ", Jugador: " + jugador.getNombre() + "]";
    }
}
