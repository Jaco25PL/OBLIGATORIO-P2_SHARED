/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class Direccion {
    private final char codigo; // Made final for immutability, a common practice

    // Constructor: crea una nueva dirección.
    public Direccion(char codigo) {
        this.codigo = Character.toUpperCase(codigo);
    }

    // obtiene el código de la dirección.
    public char getCodigo() {
        return codigo;
    }

    // compara esta dirección con otra.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion that = (Direccion) o;
        return codigo == that.codigo;
    }

    // genera código hash para dirección.
    @Override
    public int hashCode() {
        return Character.hashCode(codigo);
    }

    // devuelve representación textual de dirección.
    @Override
    public String toString() {
        return "Direccion[" + codigo + "]";
    }
}