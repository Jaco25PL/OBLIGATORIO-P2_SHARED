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

        System.out.println("--- Probando la clase Punto ---");
        System.out.println("Caracteres de prueba: □ ■ áéíóúñ"); // Prueba para ver si UTF-8 funciona

        // 1. Pruebas de creación de Puntos VÁLIDOS
        System.out.println("\nIntentando crear puntos válidos:");
        try {
            Punto p1 = new Punto(1, 'D');
            System.out.println("Creado OK: " + p1); // Usa toString() implícitamente

            Punto p2 = new Punto(4, 'A');
            System.out.println("Creado OK: " + p2);

            Punto p3 = new Punto(7, 'J');
            System.out.println("Creado OK: " + p3);

            Punto p4 = new Punto(3, 'l'); // Prueba con minúscula
            System.out.println("Creado OK (con minúscula 'l'): " + p4);

        } catch (IllegalArgumentException e) {
            System.err.println("ERROR INESPERADO al crear punto válido: " + e.getMessage());
        }

        // 2. Pruebas de creación de Puntos INVÁLIDOS
        System.out.println("\nIntentando crear puntos inválidos (se esperan excepciones):");
        try {
            Punto pInv1 = new Punto(1, 'A'); // Inválido en fila 1
            System.err.println("ERROR: Se creó un punto inválido! " + pInv1);
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Excepción capturada como esperado -> " + e.getMessage());
        }

        try {
            Punto pInv2 = new Punto(0, 'D'); // Fila fuera de rango
            System.err.println("ERROR: Se creó un punto inválido! " + pInv2);
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Excepción capturada como esperado -> " + e.getMessage());
        }

        try {
            Punto pInv3 = new Punto(4, 'N'); // Columna fuera de rango
            System.err.println("ERROR: Se creó un punto inválido! " + pInv3);
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Excepción capturada como esperado -> " + e.getMessage());
        }

        try {
            Punto pInv4 = new Punto(5, 'C'); // Columna C no válida en fila 5
            System.err.println("ERROR: Se creó un punto inválido! " + pInv4);
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Excepción capturada como esperado -> " + e.getMessage());
        }

        // 3. Pruebas de equals y hashCode
        System.out.println("\nProbando equals y hashCode:");
        try {
            Punto pA = new Punto(4, 'E');
            Punto pB = new Punto(4, 'E'); // Igual a pA
            Punto pC = new Punto(4, 'G'); // Diferente a pA y pB
            Punto pD = new Punto(5, 'E'); // Diferente a pA y pB

            System.out.println("pA: " + pA + " (hashCode: " + pA.hashCode() + ")");
            System.out.println("pB: " + pB + " (hashCode: " + pB.hashCode() + ")");
            System.out.println("pC: " + pC + " (hashCode: " + pC.hashCode() + ")");
            System.out.println("pD: " + pD + " (hashCode: " + pD.hashCode() + ")");

            System.out.println("pA.equals(pB)? " + pA.equals(pB)); // Debería ser true
            System.out.println("pA.hashCode() == pB.hashCode()? " + (pA.hashCode() == pB.hashCode())); // Debería ser true

            System.out.println("pA.equals(pC)? " + pA.equals(pC)); // Debería ser false
            System.out.println("pA.equals(pD)? " + pA.equals(pD)); // Debería ser false
            System.out.println("pA.equals(null)? " + pA.equals(null)); // Debería ser false
            System.out.println("pA.equals(\"E4\")? " + pA.equals("E4")); // Debería ser false

        } catch (IllegalArgumentException e) {
            System.err.println("ERROR INESPERADO durante pruebas equals/hashCode: " + e.getMessage());
        }

        System.out.println("\n--- Fin pruebas Punto ---");
    }
}
