/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package obligatorio_shared;

// Importaciones necesarias para la configuración de UTF-8
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author 59894 // Reemplazar con tus datos
 */
public class OBLIGATORIO_SHARED {

    public static void main(String[] args) {

        // --- Configuración de UTF-8 para la salida estándar ---
        // Colocar esto ANTES de cualquier System.out.println
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            // Manejar el (muy improbable) caso de que UTF-8 no sea soportado
            System.err.println("Error grave: Codificación UTF-8 no soportada. " + e.getMessage());
            // Considerar salir si la codificación es crucial
            // System.exit(1);
        }
        // -------------------------------------------------------
    }
}
