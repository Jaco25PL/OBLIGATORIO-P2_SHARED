/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######]
 */
package obligatorio_shared;

/**
 * Representa una ubicación ("clavo") específica e inmutable en el tablero.
 * Un punto se define por su fila (1-7) y su columna ('A'-'M').
 * Una vez creado, un Punto no puede cambiar su fila o columna.
 * Este constructor valida que el punto pertenezca al área de juego válida.
 */
public class Punto {

    private final int fila;
    private final char columna;

    /**
     * Constructor para crear un nuevo Punto. Valida que las coordenadas
     * correspondan a un punto existente en el tablero con forma de diamante.
     * Lanza IllegalArgumentException si las coordenadas son inválidas.
     *
     * @param fila La fila del punto (1 a 7).
     * @param columna La columna del punto ('A' a 'M').
     * @throws IllegalArgumentException si la fila, columna o la combinación de ambas no corresponde a un punto válido en el tablero.
     */

    public Punto(int fila, char columna) {
        // Convertir columna a mayúscula para validación consistente
        char colUpper = Character.toUpperCase(columna);
        boolean valido = false;

        if (fila == 1 || fila == 7) { // Filas 1 y 7: D, F, H, J
            valido = (colUpper == 'D' || colUpper == 'F' || colUpper == 'H' || colUpper == 'J');
        } else if (fila == 2 || fila == 6) { // Filas 2 y 6: C, E, G, I, K
            valido = (colUpper == 'C' || colUpper == 'E' || colUpper == 'G' || colUpper == 'I' || colUpper == 'K');
        } else if (fila == 3 || fila == 5) { // Filas 3 y 5: B, D, F, H, J, L
            valido = (colUpper == 'B' || colUpper == 'D' || colUpper == 'F' || colUpper == 'H' || colUpper == 'J' || colUpper == 'L');
        } else if (fila == 4) { // Fila 4: A, C, E, G, I, K, M
            valido = (colUpper == 'A' || colUpper == 'C' || colUpper == 'E' || colUpper == 'G' || colUpper == 'I' || colUpper == 'K' || colUpper == 'M');
        }
        // Si la fila no es ninguna de las anteriores (1-7), 'valido' permanecerá false.

        if (!valido) {
            throw new IllegalArgumentException("El punto " + colUpper + fila + " no es válido o no existe en el tablero.");
        }

        // Si pasó la validación, asigna los atributos finales (guardando la columna en mayúscula)
        this.fila = fila;
        this.columna = colUpper; // Guardar siempre en mayúscula
    }

    /**
     * Obtiene la fila del punto.
     * @return El número de la fila.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna del punto.
     * @return El carácter de la columna.
     */
    public char getColumna() {
        return columna;
    }

    // --- Métodos equals, hashCode y toString (Importantes para colecciones y depuración) ---

    @Override 
    public String toString() {
        return "" + columna + fila; // Devuelve la representación en forma de string del punto (ej: "A1", "B2", etc.)
        // Por ejemplo:
        // Punto miPunto = new Punto(4, 'E');
        // String representacion = miPunto.toString();
        // System.out.println(representacion); // Imprime "E4"
    }

    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false; // 1. Inicializa la variable de resultado a false

        // 2. Primera condición: ¿Son la misma instancia en memoria?
        if (this == o) {
            sonIguales = true; // Si son la misma instancia, son iguales
        }
        // 3. Segunda condición: ¿El otro objeto no es nulo Y es de la clase Punto?
        else if (o != null && getClass() == o.getClass()) {
            // Si cumple, podemos hacer el cast de forma segura
            Punto otroPunto = (Punto) o;
            // 4. Tercera condición: ¿Coinciden los atributos relevantes (fila y columna)?
            if (this.fila == otroPunto.fila && this.columna == otroPunto.columna) {
                sonIguales = true; // Si coinciden, son iguales
            }
            // Si no coinciden los atributos, sonIguales permanece false
        }
        // Si 'o' era nulo o de otra clase, sonIguales permanece false

        // 5. Único punto de retorno
        return sonIguales;
    }

    @Override
    public int hashCode() { // Genera un código hash basado en las coordenadas del punto
        int result = fila; // Inicializa el resultado con la fila
        result = 31 * result + (int) columna; // Multiplica el resultado por 31 y le suma el valor de la columna
        // El 31 es un número primo que ayuda a distribuir mejor los hash en las tablas hash
        return result; // Devuelve el código hash final
    }

    // NO HAY MÉTODOS setFila() NI setColumna() -> Es inmutable
}
