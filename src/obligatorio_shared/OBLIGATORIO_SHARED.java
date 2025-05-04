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
            Punto pD = new Punto(5, 'F'); // Diferente a pA y pB

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

        // ---------------------------

        System.out.println("\n\n--- Probando la clase Jugador ---");

        // 1. Pruebas de creación de Jugadores VÁLIDOS
        System.out.println("\nIntentando crear jugadores válidos:");
        Jugador j1 = null, j2 = null, j3 = null; // Declarar fuera para usar en pruebas equals
        try {
            j1 = new Jugador("Ana García", "anita88", 35);
            System.out.println("Creado OK: " + j1); // Usa toString()

            j2 = new Jugador("Luis Pérez", "lucho_p", 22);
            System.out.println("Creado OK: " + j2);

            j3 = new Jugador("Ana García", "ana_g", 40); // Mismo nombre, diferente username
            System.out.println("Creado OK: " + j3);

        } catch (Exception e) { // Captura genérica por si acaso
            System.err.println("ERROR INESPERADO al crear jugador válido: " + e.getMessage());
        }

        // 2. Pruebas de creación de Jugadores INVÁLIDOS
        System.out.println("\nIntentando crear jugadores inválidos (se esperan excepciones):");
        try {
            Jugador jInv1 = new Jugador(null, "errorUser", 30);
            System.err.println("ERROR: Se creó jugador con nombre null! " + jInv1);
        } catch (NullPointerException e) {
            System.out.println("OK (Nombre null): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Nombre null): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Jugador jInv2 = new Jugador("Nombre Real", null, 30);
            System.err.println("ERROR: Se creó jugador con username null! " + jInv2);
        } catch (NullPointerException e) {
            System.out.println("OK (Username null): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Username null): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Jugador jInv3 = new Jugador("   ", "userVacio", 30); // Nombre vacío después de trim
            System.err.println("ERROR: Se creó jugador con nombre vacío! " + jInv3);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (Nombre vacío): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Nombre vacío): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Jugador jInv4 = new Jugador("Nombre OK", "  ", 30); // Username vacío después de trim
            System.err.println("ERROR: Se creó jugador con username vacío! " + jInv4);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (Username vacío): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Username vacío): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Jugador jInv5 = new Jugador("Nombre OK", "userNeg", -5); // Edad negativa
            System.err.println("ERROR: Se creó jugador con edad negativa! " + jInv5);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (Edad negativa): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Edad negativa): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        // 3. Pruebas de equals y hashCode
        System.out.println("\nProbando equals y hashCode:");
        try {
            // Usamos j1 ("anita88") creado antes
            Jugador j1Bis = new Jugador("Ana García López", "anita88", 36); // Mismo username, otros datos
            Jugador j2Copia = new Jugador("Luis Pérez", "lucho_p", 22); // Igual a j2
            // Usamos j3 ("ana_g") creado antes

            System.out.println("j1:      " + j1 + " (hashCode: " + (j1 != null ? j1.hashCode() : "N/A") + ")");
            System.out.println("j1Bis:   " + j1Bis + " (hashCode: " + j1Bis.hashCode() + ")");
            System.out.println("j2:      " + j2 + " (hashCode: " + (j2 != null ? j2.hashCode() : "N/A") + ")");
            System.out.println("j2Copia: " + j2Copia + " (hashCode: " + j2Copia.hashCode() + ")");
            System.out.println("j3:      " + j3 + " (hashCode: " + (j3 != null ? j3.hashCode() : "N/A") + ")");

            if (j1 != null) {
                System.out.println("j1.equals(j1Bis)? " + j1.equals(j1Bis)); // Debería ser true (mismo username)
                System.out.println("j1.hashCode() == j1Bis.hashCode()? " + (j1.hashCode() == j1Bis.hashCode())); // Debería ser true
                System.out.println("j1.equals(j2)? " + j1.equals(j2));     // Debería ser false
                System.out.println("j1.equals(j3)? " + j1.equals(j3));     // Debería ser false
                System.out.println("j1.equals(null)? " + j1.equals(null));   // Debería ser false
                System.out.println("j1.equals(\"anita88\")? " + j1.equals("anita88")); // Debería ser false
            }
             if (j2 != null) {
                 System.out.println("j2.equals(j2Copia)? " + j2.equals(j2Copia)); // Debería ser true
                 System.out.println("j2.hashCode() == j2Copia.hashCode()? " + (j2.hashCode() == j2Copia.hashCode())); // Debería ser true
             }

        } catch (Exception e) {
             System.err.println("ERROR INESPERADO durante pruebas equals/hashCode Jugador: " + e.getMessage());
        }

        // 4. Pruebas de métodos de estadísticas
        System.out.println("\nProbando métodos de estadísticas:");
        try {
            Jugador jStats = new Jugador("Carlos Duty", "cal ड्यूटी", 28); // Usando caracteres no ASCII
            System.out.println("Estado inicial:      " + jStats);

            jStats.incrementarPartidasGanadas();
            System.out.println("Después de ganar 1:  " + jStats);

            jStats.incrementarPartidasGanadas();
            System.out.println("Después de ganar 2:  " + jStats);

            jStats.resetearRachaActual();
            System.out.println("Después de perder:   " + jStats); // Racha actual a 0, mejor racha se mantiene

            jStats.incrementarPartidasGanadas();
            System.out.println("Después de ganar 3:  " + jStats); // Racha actual 1

            jStats.incrementarPartidasGanadas();
            jStats.incrementarPartidasGanadas();
            System.out.println("Después de ganar 4,5:" + jStats); // Racha actual 3, mejor racha 3

        } catch (Exception e) {
             System.err.println("ERROR INESPERADO durante pruebas de estadísticas: " + e.getMessage());
        }


        System.out.println("\n--- Fin pruebas Jugador ---");
    }
}
