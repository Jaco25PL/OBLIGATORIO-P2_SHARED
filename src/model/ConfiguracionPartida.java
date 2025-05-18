/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

public class ConfiguracionPartida {

    private final int maxTablerosMostrar = 4;
    private final int minTablerosMostrar = 1;
    private final int minLargoBanda = 1;
    private final int maxLargoBanda = 4;
    private final int minBandasFin = 1;

    private boolean requiereContacto;
    private boolean largoBandasVariable;
    private int largoFijo;
    private int cantidadBandasFin;
    private int cantidadTablerosMostrar;

    // Crea configuración por defecto.
    public ConfiguracionPartida() {
        this.requiereContacto = false;
        this.largoBandasVariable = false;
        this.largoFijo = 4;
        this.cantidadBandasFin = 10;
        this.cantidadTablerosMostrar = 1;
    }

    // Crea configuración personalizada.
    public ConfiguracionPartida(
        boolean requiereContacto, 
        boolean largoBandasVariable, 
        int largoFijo, 
        int cantidadBandasFin, 
        int cantidadTablerosMostrar) {
            
        validarLargoFijo(largoFijo);
        validarCantidadBandasFin(cantidadBandasFin);
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);

        this.requiereContacto = requiereContacto;
        this.largoBandasVariable = largoBandasVariable;
        this.largoFijo = largoFijo;
        this.cantidadBandasFin = cantidadBandasFin;
        this.cantidadTablerosMostrar = cantidadTablerosMostrar;
    }

    // Valida largo de banda.
    private void validarLargoFijo(int largo) {
        if (largo < minLargoBanda || largo > maxLargoBanda) {
            throw new IllegalArgumentException("Largo banda entre " + minLargoBanda + " y " + maxLargoBanda + ".");
        }
    }

    // Valida cantidad bandas fin.
    private void validarCantidadBandasFin(int cantidad) {
        if (cantidad < minBandasFin) {
            throw new IllegalArgumentException("Cantidad bandas fin al menos " + minBandasFin + ".");
        }
    }

    // Valida tableros a mostrar.
    private void validarCantidadTablerosMostrar(int cantidad) {
        if (cantidad < minTablerosMostrar || cantidad > maxTablerosMostrar) {
            throw new IllegalArgumentException("Tableros mostrar entre " + minTablerosMostrar + " y " + maxTablerosMostrar + ".");
        }
    }

    // Indica si requiere contacto.
    public boolean isRequiereContacto() {
        return requiereContacto;
    }

    // Define si requiere contacto.
    public void setRequiereContacto(boolean requiereContacto) {
        this.requiereContacto = requiereContacto;
    }

    // Indica si largo es variable.
    public boolean isLargoBandasVariable() {
        return largoBandasVariable;
    }

    // Define si largo es variable.
    public void setLargoBandasVariable(boolean largoBandasVariable) {
        this.largoBandasVariable = largoBandasVariable;
    }

    // Obtiene largo fijo banda.
    public int getLargoFijo() {
        return largoFijo;
    }

    // Define largo fijo banda.
    public void setLargoFijo(int largoFijo) {
        validarLargoFijo(largoFijo);
        this.largoFijo = largoFijo;
    }

    // Obtiene bandas para fin.
    public int getCantidadBandasFin() {
        return cantidadBandasFin;
    }

    // Define bandas para fin.
    public void setCantidadBandasFin(int cantidadBandasFin) {
        validarCantidadBandasFin(cantidadBandasFin);
        this.cantidadBandasFin = cantidadBandasFin;
    }

    // Obtiene tableros a mostrar.
    public int getCantidadTablerosMostrar() {
        return cantidadTablerosMostrar;
    }

    // Define tableros a mostrar.
    public void setCantidadTablerosMostrar(int cantidadTablerosMostrar) {
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);
        this.cantidadTablerosMostrar = cantidadTablerosMostrar;
    }

    // Obtiene mínimo largo banda.
    public int getMinLargoBanda() {
        return this.minLargoBanda;
    }

    // Obtiene máximo largo banda.
    public int getMaxLargoBanda() {
        return this.maxLargoBanda;
    }

    // Obtiene mínimo bandas fin.
    public int getMinBandasFin() {
        return this.minBandasFin;
    }

    // Obtiene mínimo tableros mostrar.
    public int getMinTablerosMostrar() {
        return this.minTablerosMostrar;
    }

    // Obtiene máximo tableros mostrar.
    public int getMaxTablerosMostrar() {
        return this.maxTablerosMostrar;
    }

    // Texto de la configuración.
    @Override
    public String toString() {
        String largoDesc = largoBandasVariable ? "Variable (" + minLargoBanda + "-" + maxLargoBanda + ")" : "Fijo (" + largoFijo + ")";
        String contactoDesc = requiereContacto ? "Sí" : "No";
        return "- Requiere Contacto: " + contactoDesc + "\n" +
               "- Largo Bandas: " + largoDesc + "\n" +
               "- Bandas para Fin: " + cantidadBandasFin + "\n" +
               "- Tableros a Mostrar: " + cantidadTablerosMostrar;
    }

    // Restablece valores por defecto.
    public void resetToDefaults() {
        this.requiereContacto = false;
        this.largoBandasVariable = false;
        this.largoFijo = 4;
        this.cantidadBandasFin = 10;
        this.cantidadTablerosMostrar = 1;
    }
}