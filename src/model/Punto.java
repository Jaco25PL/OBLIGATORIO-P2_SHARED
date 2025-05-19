/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class Punto {

    private int fila;
    private char columna;

    // Crea un nuevo punto.
    public Punto(int fila, char columna) {
        char colUpper = Character.toUpperCase(columna);
        boolean valido = false;

        if (fila == 1 || fila == 7) {
            valido = (colUpper == 'D' || colUpper == 'F' || colUpper == 'H' || colUpper == 'J');
        } else if (fila == 2 || fila == 6) {
            valido = (colUpper == 'C' || colUpper == 'E' || colUpper == 'G' || colUpper == 'I' || colUpper == 'K');
        } else if (fila == 3 || fila == 5) {
            valido = (colUpper == 'B' || colUpper == 'D' || colUpper == 'F' || colUpper == 'H' || colUpper == 'J' || colUpper == 'L');
        } else if (fila == 4) {
            valido = (colUpper == 'A' || colUpper == 'C' || colUpper == 'E' || colUpper == 'G' || colUpper == 'I' || colUpper == 'K' || colUpper == 'M');
        }

        if (!valido) {
            throw new IllegalArgumentException("Punto " + colUpper + fila + " inválido.");
        }

        this.fila = fila;
        this.columna = colUpper;
    }

    // Obtiene la fila.
    public int getFila() {
        return fila;
    }

    // Obtiene la columna.
    public char getColumna() {
        return columna;
    }

    // Devuelve representación textual.
    @Override
    public String toString() {
        return "" + columna + fila;
    }

    // Compara este punto con otro.
    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (this == o) {
            sonIguales = true;
        } else if (o != null && getClass() == o.getClass()) {
            Punto otroPunto = (Punto) o;
            sonIguales = (this.fila == otroPunto.fila && this.columna == otroPunto.columna);
        }
        return sonIguales;
    }

    // Devuelve coordenada completa formateada.
    public String coordenadaCompleta() {
        return "" + Character.toUpperCase(this.columna) + this.fila;
    }
}