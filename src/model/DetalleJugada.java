package model;

// Originally from Partida.java

public class DetalleJugada {
    private Punto origen;
    private Direccion direccion;
    private int largo;

    // Crea objeto de datos jugada parseada.
    public DetalleJugada(Punto origen, Direccion direccion, int largo) {
        this.origen = origen;
        this.direccion = direccion;
        this.largo = largo;
    }

    // Obtiene punto de origen del movimiento.
    public Punto getOrigen() {
        return origen;
    }

    // Establece punto de origen del movimiento.
    public void setOrigen(Punto origen) {
        this.origen = origen;
    }

    // Obtiene dirección del movimiento.
    public Direccion getDireccion() {
        return direccion;
    }

    // Obtiene largo de banda del movimiento.
    public int getLargo() {
        return largo;
    }
}
