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

        // --- Pruebas adicionales de Punto ---
        Punto pA4 = null, pC4 = null, pD1 = null, pC2 = null, pB3 = null, pD3 = null, pE4 = null; // Puntos para Banda
        try {
             pA4 = new Punto(4, 'A'); // Corregido: fila 4, columna A
             pC4 = new Punto(4, 'C'); // Corregido: fila 4, columna C
             pD1 = new Punto(1, 'D'); // Corregido: fila 1, columna D
             pC2 = new Punto(2, 'C'); // Corregido: fila 2, columna C
             pB3 = new Punto(3, 'B'); // Corregido: fila 3, columna B
             pD3 = new Punto(3, 'D'); // Corregido: fila 3, columna D
             pE4 = new Punto(4, 'E'); // Corregido: fila 4, columna E
             System.out.println("Puntos creados para pruebas Banda: " + pA4 + ", " + pC4 + ", " + pD1 + ", " + pC2 + ", " + pB3 + ", " + pD3 + ", " + pE4);
        } catch (Exception e) {
             System.err.println("ERROR creando puntos base para pruebas Banda: " + e.getMessage());
             return; // Salir si no se pueden crear puntos base
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

        // --- Pruebas adicionales de Jugador ---
        Jugador jPrueba1 = null, jPrueba2 = null; // Jugadores para Banda
        try {
            jPrueba1 = new Jugador("Tester Uno", "test1", 20);
            jPrueba2 = new Jugador("Tester Dos", "test2", 25);
            System.out.println("Jugadores creados para pruebas Banda: " + jPrueba1.getUsername() + ", " + jPrueba2.getUsername());
        } catch (Exception e) {
            System.err.println("ERROR creando jugadores base para pruebas Banda: " + e.getMessage());
            return; // Salir si no se pueden crear jugadores base
        }
        System.out.println("\n--- Fin pruebas Jugador ---");

        // ---------------------------

        // --- Pruebas Banda ---
        System.out.println("\n\n--- Probando la clase Banda ---");

        // Verificar que los objetos base no sean null antes de usarlos
        if (pA4 == null || pC4 == null || pD1 == null || pC2 == null || pB3 == null || pD3 == null || pE4 == null || jPrueba1 == null || jPrueba2 == null) {
             System.err.println("ERROR FATAL: No se pudieron crear los Puntos o Jugadores necesarios para las pruebas de Banda.");
             return;
        }

        // 1. Pruebas de creación de Bandas VÁLIDAS
        System.out.println("\nIntentando crear bandas válidas:");
        Banda b1 = null, b2 = null, b3 = null, b4 = null;
        try {
            // Horizontal
            b1 = new Banda(pA4, pC4, jPrueba1);
            System.out.println("Creada OK (Horizontal): " + b1); // Usa toString()

            // Diagonal 1
            b2 = new Banda(pD1, pC2, jPrueba2);
            System.out.println("Creada OK (Diagonal 1): " + b2);

            // Diagonal 2 (mismos puntos que b2 pero en orden inverso)
            b3 = new Banda(pC2, pD1, jPrueba1); // Jugador diferente también
            System.out.println("Creada OK (Diagonal 2, orden inverso): " + b3);

            // Diagonal 3
            b4 = new Banda(pC2, pB3, jPrueba1);
            System.out.println("Creada OK (Diagonal 3): " + b4);

        } catch (Exception e) {
            System.err.println("ERROR INESPERADO al crear banda válida: " + e.getClass().getName() + ": " + e.getMessage());
        }

        // 2. Pruebas de creación de Bandas INVÁLIDAS
        System.out.println("\nIntentando crear bandas inválidas (se esperan excepciones):");
        try {
            Banda bInv1 = new Banda(null, pC4, jPrueba1);
            System.err.println("ERROR: Se creó banda con punto A null! " + bInv1);
        } catch (NullPointerException e) {
            System.out.println("OK (Punto A null): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Punto A null): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Banda bInv2 = new Banda(pA4, null, jPrueba1);
            System.err.println("ERROR: Se creó banda con punto B null! " + bInv2);
        } catch (NullPointerException e) {
            System.out.println("OK (Punto B null): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Punto B null): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Banda bInv3 = new Banda(pA4, pC4, null);
            System.err.println("ERROR: Se creó banda con jugador null! " + bInv3);
        } catch (NullPointerException e) {
            System.out.println("OK (Jugador null): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Jugador null): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Banda bInv4 = new Banda(pA4, pA4, jPrueba1); // Puntos iguales
            System.err.println("ERROR: Se creó banda con puntos iguales! " + bInv4);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (Puntos iguales): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Puntos iguales): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        try {
            Banda bInv5 = new Banda(pA4, pE4, jPrueba1); // Puntos NO adyacentes (A4 a E4)
            System.err.println("ERROR: Se creó banda con puntos no adyacentes! " + bInv5);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (Puntos no adyacentes): Excepción capturada -> " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR (Puntos no adyacentes): Excepción inesperada -> " + e.getClass().getName() + ": " + e.getMessage());
        }

        // 3. Pruebas de equals y hashCode
        System.out.println("\nProbando equals y hashCode:");
        try {
            // Usamos b1, b2, b3, b4 creadas antes
            Banda b1Copia = new Banda(pA4, pC4, jPrueba2); // Misma banda que b1, diferente jugador
            Banda b2Copia = new Banda(pD1, pC2, jPrueba1); // Misma banda que b2 y b3, diferente jugador

            System.out.println("b1: " + b1 + " (hashCode: " + (b1 != null ? b1.hashCode() : "N/A") + ")");
            System.out.println("b2: " + b2 + " (hashCode: " + (b2 != null ? b2.hashCode() : "N/A") + ")");
            System.out.println("b3: " + b3 + " (hashCode: " + (b3 != null ? b3.hashCode() : "N/A") + ")"); // Mismos puntos que b2, orden inverso
            System.out.println("b4: " + b4 + " (hashCode: " + (b4 != null ? b4.hashCode() : "N/A") + ")");
            System.out.println("b1Copia: " + b1Copia + " (hashCode: " + b1Copia.hashCode() + ")"); // Mismos puntos que b1, otro jugador
            System.out.println("b2Copia: " + b2Copia + " (hashCode: " + b2Copia.hashCode() + ")"); // Mismos puntos que b2/b3, otro jugador

            if (b1 != null && b2 != null && b3 != null && b4 != null) {
                // Comparaciones clave
                System.out.println("b1.equals(b1Copia)? " + b1.equals(b1Copia)); // Debería ser true (mismos puntos, ignora jugador)
                System.out.println("b1.hashCode() == b1Copia.hashCode()? " + (b1.hashCode() == b1Copia.hashCode())); // Debería ser true

                System.out.println("b2.equals(b3)? " + b2.equals(b3)); // Debería ser true (mismos puntos, ignora orden y jugador)
                System.out.println("b2.hashCode() == b3.hashCode()? " + (b2.hashCode() == b3.hashCode())); // Debería ser true

                System.out.println("b2.equals(b2Copia)? " + b2.equals(b2Copia)); // Debería ser true (mismos puntos, ignora jugador)
                System.out.println("b2.hashCode() == b2Copia.hashCode()? " + (b2.hashCode() == b2Copia.hashCode())); // Debería ser true

                // Comparaciones de desigualdad
                System.out.println("b1.equals(b2)? " + b1.equals(b2)); // Debería ser false (diferentes puntos)
                System.out.println("b1.equals(b4)? " + b1.equals(b4)); // Debería ser false (diferentes puntos)
                System.out.println("b2.equals(b4)? " + b2.equals(b4)); // Debería ser false (diferentes puntos)

                // Comparaciones con otros tipos
                System.out.println("b1.equals(null)? " + b1.equals(null));   // Debería ser false
                System.out.println("b1.equals(pA4)? " + b1.equals(pA4)); // Debería ser false (diferente clase)
            }

        } catch (Exception e) {
             System.err.println("ERROR INESPERADO durante pruebas equals/hashCode Banda: " + e.getClass().getName() + ": " + e.getMessage());
        }

        // 4. Pruebas de Getters
        System.out.println("\nProbando Getters (usando b1 y b3):");
        if (b1 != null && b3 != null) {
            try {
                System.out.println("b1.getPuntoA(): " + b1.getPuntoA() + " (Esperado: A4)");
                System.out.println("b1.getPuntoB(): " + b1.getPuntoB() + " (Esperado: C4)");
                System.out.println("b1.getJugador().getUsername(): " + b1.getJugador().getUsername() + " (Esperado: test1)");

                // Corrección en los comentarios "Esperado":
                System.out.println("b3.getPuntoA(): " + b3.getPuntoA() + " (Esperado: D1 - el 'menor')"); // D1 es menor que C2 (fila 1 vs 2)
                System.out.println("b3.getPuntoB(): " + b3.getPuntoB() + " (Esperado: C2 - el 'mayor')");
                System.out.println("b3.getJugador().getUsername(): " + b3.getJugador().getUsername() + " (Esperado: test1)");
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO durante pruebas de getters: " + e.getClass().getName() + ": " + e.getMessage());
            }
        } else {
            System.out.println("No se pueden probar getters porque b1 o b3 son null.");
        }

        System.out.println("\n--- Fin pruebas Banda ---");
    }
}
