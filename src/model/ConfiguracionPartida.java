/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */

package model;

public class ConfiguracionPartida {

    public static final int MAX_TABLEROS_MOSTRAR = 4;
    public static final int MIN_TABLEROS_MOSTRAR = 1;
    public static final int MIN_LARGO_BANDA = 1;
    public static final int MAX_LARGO_BANDA = 4;
    public static final int MIN_BANDAS_FIN = 1; 

    public static final boolean DEFAULT_REQUIERE_CONTACTO = false;
    public static final boolean DEFAULT_LARGO_VARIABLE = false;
    public static final int DEFAULT_LARGO_FIJO = 4;
    public static final int DEFAULT_CANTIDAD_BANDAS_FIN = 10;
    public static final int DEFAULT_CANTIDAD_TABLEROS = 1;

    private boolean requiereContacto;
    private boolean largoBandasVariable;
    private int largoFijo;
    private int cantidadBandasFin;
    private int cantidadTablerosMostrar;

    // inicializa con valores predeterminados.
    public ConfiguracionPartida() {
        this.requiereContacto = DEFAULT_REQUIERE_CONTACTO;
        this.largoBandasVariable = DEFAULT_LARGO_VARIABLE;
        this.largoFijo = DEFAULT_LARGO_FIJO;
        this.cantidadBandasFin = DEFAULT_CANTIDAD_BANDAS_FIN;
        this.cantidadTablerosMostrar = DEFAULT_CANTIDAD_TABLEROS;
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
        if (largo < MIN_LARGO_BANDA || largo > MAX_LARGO_BANDA) {
            throw new IllegalArgumentException("El largo fijo de la banda debe estar entre " + MIN_LARGO_BANDA + " y " + MAX_LARGO_BANDA + ".");
        }
    }

    // valida cantidad bandas fin.
    private void validarCantidadBandasFin(int cantidad) {
        if (cantidad < MIN_BANDAS_FIN) {
            throw new IllegalArgumentException("La cantidad de bandas para finalizar debe ser al menos " + MIN_BANDAS_FIN + ".");
        }
    }

    // valida cantidad tableros mostrar.
    private void validarCantidadTablerosMostrar(int cantidad) {
        if (cantidad < MIN_TABLEROS_MOSTRAR || cantidad > MAX_TABLEROS_MOSTRAR) {
            throw new IllegalArgumentException("La cantidad de tableros a mostrar debe estar entre " + MIN_TABLEROS_MOSTRAR + " y " + MAX_TABLEROS_MOSTRAR + ".");
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

    // devuelve representación textual configuración.
    @Override
    public String toString() {
        String largoDesc;
        if (largoBandasVariable) {
            largoDesc = "Variable (" + MIN_LARGO_BANDA + "-" + MAX_LARGO_BANDA + ")";
        } else {
            largoDesc = "Fijo (" + largoFijo + ")";
        }

        String contactoDesc;
        if (requiereContacto) {
            contactoDesc = "Sí";
        } else {
            contactoDesc = "No";
        }

        return "ConfiguracionPartida [" +
               "Requiere Contacto=" + contactoDesc +
               ", Largo Bandas=" + largoDesc +
               ", Bandas para Fin=" + cantidadBandasFin +
               ", Tableros a Mostrar=" + cantidadTablerosMostrar +
               ']';
    }

    // restablece a valores predeterminados.
    public void resetToDefaults() {
        this.requiereContacto = DEFAULT_REQUIERE_CONTACTO;
        this.largoBandasVariable = DEFAULT_LARGO_VARIABLE;
        this.largoFijo = DEFAULT_LARGO_FIJO;
        this.cantidadBandasFin = DEFAULT_CANTIDAD_BANDAS_FIN;
        this.cantidadTablerosMostrar = DEFAULT_CANTIDAD_TABLEROS;
    }
}