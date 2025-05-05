/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */
package obligatorio_shared;

/**
 * Almacena las reglas configurables para una o más partidas del juego "Triángulos".
 * Permite definir comportamientos como la necesidad de contacto para colocar bandas,
 * si el largo de las bandas es variable, el límite de bandas para finalizar, etc.
 * Los valores por defecto se toman de la consigna del obligatorio.
 */
public class ConfiguracionPartida {

    // --- Constantes para Límites y Defaults (según consigna) ---
    // Se hacen públicas para poder usarlas desde fuera si es necesario (ej. en la UI para mostrar límites)
    public static final int MAX_TABLEROS_MOSTRAR = 4;
    public static final int MIN_TABLEROS_MOSTRAR = 1;
    public static final int MIN_LARGO_BANDA = 1;
    public static final int MAX_LARGO_BANDA = 4;
    public static final int MIN_BANDAS_FIN = 1; // Mínimo razonable

    // Valores por defecto explícitos según consigna
    public static final boolean DEFAULT_REQUIERE_CONTACTO = false;
    public static final boolean DEFAULT_LARGO_VARIABLE = false;
    public static final int DEFAULT_LARGO_FIJO = 4;
    public static final int DEFAULT_CANTIDAD_BANDAS_FIN = 10;
    public static final int DEFAULT_CANTIDAD_TABLEROS = 1;


    // --- Atributos privados para encapsular la configuración ---
    private boolean requiereContacto;
    private boolean largoBandasVariable;
    // Renombrado desde largoFijoDefault para mayor claridad
    private int largoFijo;
    private int cantidadBandasFin;
    private int cantidadTablerosMostrar;

    /**
     * Constructor por defecto.
     * Inicializa la configuración con los valores predeterminados especificados
     * en la consigna del obligatorio usando las constantes definidas.
     */
    public ConfiguracionPartida() {
        this.requiereContacto = DEFAULT_REQUIERE_CONTACTO;
        this.largoBandasVariable = DEFAULT_LARGO_VARIABLE;
        this.largoFijo = DEFAULT_LARGO_FIJO;
        this.cantidadBandasFin = DEFAULT_CANTIDAD_BANDAS_FIN;
        this.cantidadTablerosMostrar = DEFAULT_CANTIDAD_TABLEROS;
        // No se necesita validación aquí porque los defaults son válidos.
    }

    /**
     * Constructor para configuración personalizada.
     * Permite establecer todos los parámetros de configuración explícitamente.
     * Se aplican validaciones a los parámetros numéricos.
     *
     * @param requiereContacto        Indica si se requiere contacto para colocar una banda (true) o no (false).
     * @param largoBandasVariable     Indica si el largo de la banda es variable (true) o fijo (false).
     * @param largoFijo               Si el largo no es variable, este es el largo fijo a usar (debe estar entre MIN_LARGO_BANDA y MAX_LARGO_BANDA).
     * @param cantidadBandasFin       El número total de bandas que finalizan la partida (debe ser >= MIN_BANDAS_FIN).
     * @param cantidadTablerosMostrar Cuántos tableros mostrar (entre MIN_TABLEROS_MOSTRAR y MAX_TABLEROS_MOSTRAR).
     * @throws IllegalArgumentException si algún parámetro numérico está fuera de su rango válido.
     */
    public ConfiguracionPartida(boolean requiereContacto, boolean largoBandasVariable, int largoFijo, int cantidadBandasFin, int cantidadTablerosMostrar) {
        // Validar parámetros numéricos ANTES de asignarlos
        validarLargoFijo(largoFijo);
        validarCantidadBandasFin(cantidadBandasFin);
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);

        this.requiereContacto = requiereContacto;
        this.largoBandasVariable = largoBandasVariable;
        this.largoFijo = largoFijo; // Asignar después de validar
        this.cantidadBandasFin = cantidadBandasFin; // Asignar después de validar
        this.cantidadTablerosMostrar = cantidadTablerosMostrar; // Asignar después de validar
    }

    // --- Métodos de Validación Privados ---
    // (Estos métodos permanecen igual)

    private void validarLargoFijo(int largo) {
        if (largo < MIN_LARGO_BANDA || largo > MAX_LARGO_BANDA) {
            throw new IllegalArgumentException("El largo fijo de la banda debe estar entre " + MIN_LARGO_BANDA + " y " + MAX_LARGO_BANDA + ".");
        }
    }

    private void validarCantidadBandasFin(int cantidad) {
        if (cantidad < MIN_BANDAS_FIN) {
            throw new IllegalArgumentException("La cantidad de bandas para finalizar debe ser al menos " + MIN_BANDAS_FIN + ".");
        }
    }

    private void validarCantidadTablerosMostrar(int cantidad) {
        if (cantidad < MIN_TABLEROS_MOSTRAR || cantidad > MAX_TABLEROS_MOSTRAR) {
            throw new IllegalArgumentException("La cantidad de tableros a mostrar debe estar entre " + MIN_TABLEROS_MOSTRAR + " y " + MAX_TABLEROS_MOSTRAR + ".");
        }
    }


    // --- Getters y Setters (con validación en setters) ---
    // (Actualizados para usar 'largoFijo' en lugar de 'largoFijoDefault')

    public boolean isRequiereContacto() {
        return requiereContacto;
    }

    public void setRequiereContacto(boolean requiereContacto) {
        this.requiereContacto = requiereContacto;
    }

    public boolean isLargoBandasVariable() {
        return largoBandasVariable;
    }

    public void setLargoBandasVariable(boolean largoBandasVariable) {
        this.largoBandasVariable = largoBandasVariable;
    }

    /**
     * Obtiene el largo fijo de las bandas, aplicable si `isLargoBandasVariable()` es false.
     * @return El largo fijo.
     */
    public int getLargoFijo() { // Getter renombrado
        return largoFijo;
    }

    /**
     * Establece el largo fijo de las bandas. Solo tiene efecto si `isLargoBandasVariable()` es false.
     * @param largoFijo El nuevo largo fijo (debe estar entre MIN_LARGO_BANDA y MAX_LARGO_BANDA).
     * @throws IllegalArgumentException si el largo está fuera del rango permitido.
     */
    public void setLargoFijo(int largoFijo) { // Setter y parámetro renombrados
        validarLargoFijo(largoFijo);
        this.largoFijo = largoFijo;
    }

    public int getCantidadBandasFin() {
        return cantidadBandasFin;
    }

    public void setCantidadBandasFin(int cantidadBandasFin) {
        validarCantidadBandasFin(cantidadBandasFin);
        this.cantidadBandasFin = cantidadBandasFin;
    }

    public int getCantidadTablerosMostrar() {
        return cantidadTablerosMostrar;
    }

    public void setCantidadTablerosMostrar(int cantidadTablerosMostrar) {
        validarCantidadTablerosMostrar(cantidadTablerosMostrar);
        this.cantidadTablerosMostrar = cantidadTablerosMostrar;
    }

    // --- toString ---
    // (Actualizado para usar 'largoFijo' y if-else)
    @Override
    public String toString() {
        // Reemplazo del ternario para largoDesc
        String largoDesc;
        if (largoBandasVariable) {
            largoDesc = "Variable (" + MIN_LARGO_BANDA + "-" + MAX_LARGO_BANDA + ")";
        } else {
            largoDesc = "Fijo (" + largoFijo + ")";
        }

        // Reemplazo del ternario para contactoDesc
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

    // --- Métodos Adicionales (Opcional) ---

    /**
     * Restablece la configuración a los valores por defecto definidos en las constantes.
     * Útil si se quiere volver a la configuración default sin crear un objeto nuevo.
     */
    public void resetToDefaults() {
        this.requiereContacto = DEFAULT_REQUIERE_CONTACTO;
        this.largoBandasVariable = DEFAULT_LARGO_VARIABLE;
        this.largoFijo = DEFAULT_LARGO_FIJO;
        this.cantidadBandasFin = DEFAULT_CANTIDAD_BANDAS_FIN;
        this.cantidadTablerosMostrar = DEFAULT_CANTIDAD_TABLEROS;
    }
}
