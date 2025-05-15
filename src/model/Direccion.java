/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */

package model;

public enum Direccion {
    NOROESTE('Q'), // Fila-1, Col-1
    NORESTE('E'),  // Fila-1, Col+1
    ESTE('D'),     // Fila,   Col+2
    SURESTE('C'),  // Fila+1, Col+1
    SUROESTE('Z'), // Fila+1, Col-1
    OESTE('A');    // Fila,   Col-2

    private final char codigo;

    Direccion(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    // obtiene dirección desde carácter
    public static Direccion fromChar(char c) {
        char upperC = Character.toUpperCase(c);
        for (Direccion dir : values()) {
            if (dir.getCodigo() == upperC) {
                return dir;
            }
        }
        return null; 
    }
}