/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class Direccion {
    private final char codigo; // Made final for immutability, a common practice

    // Crea nueva dirección.
    public Direccion(char codigo) {
        this.codigo = Character.toUpperCase(codigo);
    }

    // Obtiene código de dirección.
    public char getCodigo() {
        return codigo;
    }

    // Compara esta dirección con otra.
    @Override
    public boolean equals(Object o) {
        boolean sonIguales = false;
        if (this == o) {
            sonIguales = true;
        } else if (o != null && getClass() == o.getClass()) {
            Direccion that = (Direccion) o;
            sonIguales = (codigo == that.codigo);
        }
        return sonIguales;
    }

    // genera código hash para dirección.
    @Override
    public int hashCode() {
        return Character.hashCode(codigo);
    }

    // Representación textual de dirección.
    @Override
    public String toString() {
        return "Direccion[" + codigo + "]";
    }
}