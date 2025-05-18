/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class ConfiguracionPartida {



    private int maxTablerosMostrar = 4;
    private int minTablerosMostrar = 1;
    private int minLargoBanda = 1;
    private int maxLargoBanda = 4;
    private int minBandasFin = 1; 

    private boolean defaultRequiereContacto = false;
    private boolean defaultLargoVariable = false;
    private int defaultLargoFijo = 4;
    private int defaultCantidadBandasFin = 10;
    private int defaultCantidadTableros = 1;

    private boolean requiereContacto;
    private boolean largoBandasVariable;
    private int largoFijo;
    private int cantidadBandasFin;
    private int cantidadTablerosMostrar;

    // inicializa con valores predeterminados.
    public ConfiguracionPartida() {
        this.requiereContacto = defaultRequiereContacto;
        this.largoBandasVariable = defaultLargoVariable;
        this.largoFijo = defaultLargoFijo;
        this.cantidadBandasFin = defaultCantidadBandasFin;
        this.cantidadTablerosMostrar = defaultCantidadTableros;
    }

    // inicializa con valores personalizados.
    public ConfiguracionPartida(boolean requiereContacto, boolean largoBandasVariable, int largoFijo, int cantidadBandasFin, int cantidadTablerosMostrar) {
        validarLargoFijo(largoFijo);
        validarCantidadBandasFin(cantidadBandasFin);
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);

        this.requiereContacto = requiereContacto;
        this.largoBandasVariable = largoBandasVariable;
        this.largoFijo = largoFijo; 
        this.cantidadBandasFin = cantidadBandasFin; 
        this.cantidadTablerosMostrar = cantidadTablerosMostrar; 
    }

    // valida largo fijo banda.
    private void validarLargoFijo(int largo) {
        if (largo < minLargoBanda || largo > maxLargoBanda) {
            throw new IllegalArgumentException("El largo fijo de la banda debe estar entre " + minLargoBanda + " y " + maxLargoBanda + ".");
        }
    }

    // valida cantidad bandas fin.
    private void validarCantidadBandasFin(int cantidad) {
        if (cantidad < minBandasFin) {
            throw new IllegalArgumentException("La cantidad de bandas para finalizar debe ser al menos " + minBandasFin + ".");
        }
    }

    // valida cantidad tableros mostrar.
    private void validarCantidadTablerosMostrar(int cantidad) {
        if (cantidad < minTablerosMostrar || cantidad > maxTablerosMostrar) {
            throw new IllegalArgumentException("La cantidad de tableros a mostrar debe estar entre " + minTablerosMostrar + " y " + maxTablerosMostrar + ".");
        }
    }

    // obtiene si requiere contacto.
    public boolean isRequiereContacto() {
        return requiereContacto;
    }

    // establece si requiere contacto.
    public void setRequiereContacto(boolean requiereContacto) {
        this.requiereContacto = requiereContacto;
    }

    // obtiene si largo variable.
    public boolean isLargoBandasVariable() {
        return largoBandasVariable;
    }

    // establece si largo variable.
    public void setLargoBandasVariable(boolean largoBandasVariable) {
        this.largoBandasVariable = largoBandasVariable;
    }

    // obtiene largo fijo banda.
    public int getLargoFijo() { 
        return largoFijo;
    }

    // establece largo fijo banda.
    public void setLargoFijo(int largoFijo) { 
        validarLargoFijo(largoFijo);
        this.largoFijo = largoFijo;
    }

    // obtiene cantidad bandas fin.
    public int getCantidadBandasFin() {
        return cantidadBandasFin;
    }

    // establece cantidad bandas fin.
    public void setCantidadBandasFin(int cantidadBandasFin) {
        validarCantidadBandasFin(cantidadBandasFin);
        this.cantidadBandasFin = cantidadBandasFin;
    }

    // obtiene cantidad tableros mostrar.
    public int getCantidadTablerosMostrar() {
        return cantidadTablerosMostrar;
    }

    // establece cantidad tableros mostrar.
    public void setCantidadTablerosMostrar(int cantidadTablerosMostrar) {
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);
        this.cantidadTablerosMostrar = cantidadTablerosMostrar;
    }

    // NUEVO: Getters for min/max values
    public int getMinLargoBanda() {
        return this.minLargoBanda;
    }

    public int getMaxLargoBanda() {
        return this.maxLargoBanda;
    }

    public int getMinBandasFin() {
        return this.minBandasFin;
    }

    public int getMinTablerosMostrar() {
        return this.minTablerosMostrar;
    }

    public int getMaxTablerosMostrar() {
        return this.maxTablerosMostrar;
    }

    // devuelve representación textual configuración.
    @Override
    public String toString() {
        String largoDesc;
        if (largoBandasVariable) {
            largoDesc = "Variable (" + this.minLargoBanda + "-" + this.maxLargoBanda + ")";
        } else {
            largoDesc = "Fijo (" + largoFijo + ")";
        }

        String contactoDesc;
        if (requiereContacto) {
            contactoDesc = "Sí";
        } else {
            contactoDesc = "No";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("- Requiere Contacto: ").append(contactoDesc).append("\n");
        sb.append("- Largo Bandas: ").append(largoDesc).append("\n");
        sb.append("- Bandas para Fin: ").append(cantidadBandasFin).append("\n");
        sb.append("- Tableros a Mostrar: ").append(cantidadTablerosMostrar);
        return sb.toString();
    }

    // restablece a valores predeterminados.
    public void resetToDefaults() {
        this.requiereContacto = defaultRequiereContacto;
        this.largoBandasVariable = defaultLargoVariable;
        this.largoFijo = defaultLargoFijo;
        this.cantidadBandasFin = defaultCantidadBandasFin;
        this.cantidadTablerosMostrar = defaultCantidadTableros;
    }
}