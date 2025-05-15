/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */

package model;

public class Banda {

    // Atributos finales para inmutabilidad
    private final Punto puntoA; 
    private final Punto puntoB; 
    private final Jugador jugador; // Jugador que colocó la banda

    // crea una nueva banda.
    public Banda(Punto pA, Punto pB, Jugador jugador) {
        if (pA == null) {
            throw new NullPointerException("El primer punto (pA) no puede ser nulo.");
        }
        if (pB == null) {
            throw new NullPointerException("El segundo punto (pB) no puede ser nulo.");
        }
        if (jugador == null) {
            throw new NullPointerException("El jugador no puede ser nulo.");
        }
        this.jugador = jugador; 

        if (pA.equals(pB)) {
            throw new IllegalArgumentException("Los puntos de una banda no pueden ser iguales: " + pA);
        }

        if (!sonAdyacentes(pA, pB)) {
            throw new IllegalArgumentException("Los puntos " + pA + " y " + pB + " no son adyacentes.");
        }

        if (pA.getFila() < pB.getFila() || (pA.getFila() == pB.getFila() && pA.getColumna() < pB.getColumna())) {
            this.puntoA = pA;
            this.puntoB = pB;
        } else {
            this.puntoA = pB; 
            this.puntoB = pA;
        }
    }

    // verifica si puntos son adyacentes.
    private boolean sonAdyacentes(Punto p1, Punto p2) { 
        int diffFilas = Math.abs(p1.getFila() - p2.getFila()); 
        int diffCols = Math.abs(p1.getColumna() - p2.getColumna());

        boolean mismaFilaAdy = (diffFilas == 0 && diffCols == 2); 

        boolean filaAdyacenteAdy = (diffFilas == 1 && diffCols == 1); 

        return mismaFilaAdy || filaAdyacenteAdy;
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
        } else if (o != null && this.getClass() == o.getClass()) {
            Banda otraBanda = (Banda) o;
            sonIguales = this.puntoA.equals(otraBanda.puntoA) &&
                         this.puntoB.equals(otraBanda.puntoB);
        }
        return sonIguales;
    }

    //genera código hash para banda.
    @Override
    public int hashCode() {
        int result = puntoA.hashCode();
        result = 31 * result + puntoB.hashCode();
        return result;
    }

    // devuelve representación textual de banda.
    @Override
    public String toString() {
        return "Banda [A: " + puntoA + ", B: " + puntoB + ", Jugador: " + jugador.getNombre() + "]";
    }
}
