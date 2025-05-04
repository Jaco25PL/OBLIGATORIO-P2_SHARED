/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */
package obligatorio_shared;

/**
 * Representa una banda elástica colocada entre dos puntos adyacentes del tablero por un jugador.
 * Una banda es inmutable una vez creada.
 * La igualdad entre bandas se basa únicamente en los dos puntos que conecta,
 * independientemente del orden en que se especificaron o del jugador que la colocó.
 */
public class Banda {

    // Atributos finales para inmutabilidad
    private final Punto puntoA; // Punto "menor" (según criterio de ordenación)
    private final Punto puntoB; // Punto "mayor"
    private final Jugador jugador; // Jugador que colocó la banda

    /**
     * Constructor para crear una nueva Banda.
     * Valida que los puntos no sean nulos, no sean iguales, y sean adyacentes en el tablero.
     * También valida que el jugador no sea nulo.
     * Ordena internamente los puntos para asegurar consistencia en equals/hashCode.
     *
     * @param pA Uno de los puntos a conectar.
     * @param pB El otro punto a conectar.
     * @param jugador El jugador que coloca la banda.
     * @throws NullPointerException si alguno de los parámetros es nulo.
     * @throws IllegalArgumentException si los puntos son iguales o no son adyacentes.
     */
    public Banda(Punto pA, Punto pB, Jugador jugador) {
        // 1. Validar no nulos (forma tradicional)
        if (pA == null) {
            throw new NullPointerException("El primer punto (pA) no puede ser nulo.");
        }
        if (pB == null) {
            throw new NullPointerException("El segundo punto (pB) no puede ser nulo.");
        }
        if (jugador == null) {
            throw new NullPointerException("El jugador no puede ser nulo.");
        }
        this.jugador = jugador; // Asignar jugador después de validar que no es nulo

        // 2. Validar que no sean el mismo punto
        if (pA.equals(pB)) {
            throw new IllegalArgumentException("Los puntos de una banda no pueden ser iguales: " + pA);
        }

        // 3. Validar adyacencia
        if (!sonAdyacentes(pA, pB)) {
            throw new IllegalArgumentException("Los puntos " + pA + " y " + pB + " no son adyacentes.");
        }

        // 4. Ordenar los puntos para consistencia (puntoA siempre será el "menor")
        // Criterio: menor fila, y si son iguales, menor columna.
        if (pA.getFila() < pB.getFila() || (pA.getFila() == pB.getFila() && pA.getColumna() < pB.getColumna())) {
            this.puntoA = pA;
            this.puntoB = pB;
        } else {
            this.puntoA = pB; // pB es "menor"
            this.puntoB = pA;
        }
    }

    /**
     * Verifica si dos puntos son adyacentes en el tablero diamante.
     * Adyacentes significa:
     * - Misma fila, diferencia de 2 en columnas (ej: A4-C4).
     * - Fila adyacente, diferencia de 1 en columnas (ej: D1-C2, C2-B3).
     *
     * @param p1 Un punto.
     * @param p2 Otro punto.
     * @return true si son adyacentes, false en caso contrario.
     */
    private boolean sonAdyacentes(Punto p1, Punto p2) { // Cada banda es un guión, es decir: '-' o '/' o ''. Una banda larga de 3 sería 3 objetos banda consecutivos. 
        // No se necesita validar null aquí porque el constructor ya lo hizo
        int diffFilas = Math.abs(p1.getFila() - p2.getFila()); 
        int diffCols = Math.abs(p1.getColumna() - p2.getColumna());

        // Condición 1: Misma fila, 2 columnas de diferencia
        boolean mismaFilaAdy = (diffFilas == 0 && diffCols == 2); // Conexion Horizontal

        // Condición 2: Fila adyacente, 1 columna de diferencia
        boolean filaAdyacenteAdy = (diffFilas == 1 && diffCols == 1); // Conexion Diagonal

        return mismaFilaAdy || filaAdyacenteAdy;
    }

    // --- Getters ---

    /**
     * Obtiene el primer punto de la banda (el "menor" según el orden interno).
     * @return El primer punto (puntoA).
     */
    public Punto getPuntoA() {
        return puntoA;
    }

    /**
     * Obtiene el segundo punto de la banda (el "mayor" según el orden interno).
     * @return El segundo punto (puntoB).
     */
    public Punto getPuntoB() {
        return puntoB;
    }

    /**
     * Obtiene el jugador que colocó la banda.
     * @return El jugador.
     */
    public Jugador getJugador() {
        return jugador;
    }

    // --- equals y hashCode (Basados solo en los puntos ordenados) ---

    /**
     * Compara esta Banda con otro objeto para ver si son lógicamente iguales.
     * Dos bandas son iguales si conectan los mismos dos puntos, independientemente
     * del orden original o del jugador que las colocó.
     * @param o El objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (this == o) {
            sonIguales = true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Banda otraBanda = (Banda) o;
            // Compara usando los puntos ya ordenados internamente (puntoA y puntoB)
            // Se asume que Punto.equals maneja nulls correctamente si fuera posible,
            // pero el constructor de Banda previene que puntoA/puntoB sean null.
            sonIguales = this.puntoA.equals(otraBanda.puntoA) &&
                         this.puntoB.equals(otraBanda.puntoB);
        }
        return sonIguales;
    }

    /**
     * Genera un código hash para la Banda, basado únicamente en los dos puntos
     * que conecta (usando la representación ordenada interna puntoA y puntoB).
     * Es consistente con equals(): bandas iguales tendrán el mismo hashCode.
     * @return El código hash entero.
     */
    @Override
    public int hashCode() {
        // Cálculo manual del hash consistente con equals y que no depende del orden
        // Se basa en los hashCodes de los puntos ordenados (puntoA y puntoB)
        // El constructor asegura que puntoA y puntoB no son null.
        int result = puntoA.hashCode();
        result = 31 * result + puntoB.hashCode();
        return result;
    }

    // --- toString ---

    /**
     * Devuelve una representación textual de la Banda.
     * Muestra los puntos en su orden interno (A y B) y el username del jugador.
     * @return Un String con el formato "Banda [A: puntoA, B: puntoB, Jugador: username]".
     */
    @Override
    public String toString() {
        // El constructor asegura que jugador no es null
        return "Banda [A: " + puntoA + ", B: " + puntoB + ", Jugador: " + jugador.getUsername() + "]";
    }
}
