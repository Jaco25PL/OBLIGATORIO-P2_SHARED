/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package obligatorio_shared;

import model.Interfaz;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class OBLIGATORIO_SHARED {

    public static void main(String[] args) {

        // Configuración de UTF-8 para la salida estándar
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error grave: Codificación UTF-8 no soportada. " + e.getMessage());
        }

        // Inicio de la aplicación a través de la Interfaz 
        Interfaz interfazDeUsuario = new Interfaz();
        interfazDeUsuario.iniciarAplicacion();
    }
}
