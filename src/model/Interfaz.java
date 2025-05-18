/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Interfaz {

    private Scanner scanner;
    private ArrayList<Jugador> jugadoresRegistrados;
    private ConfiguracionPartida configuracionActual;
    private Partida partidaActual; 

    // constructor de la interfaz.
    public Interfaz() {
        this.scanner = new Scanner(System.in);
        this.jugadoresRegistrados = new ArrayList<>();
        this.configuracionActual = new ConfiguracionPartida();
        this.partidaActual = null; 


        // Test players for debugging
        try {
        // Create two test players with different names
        Jugador jugador1 = new Jugador("Test Player 1", 25);
        Jugador jugador2 = new Jugador("Test Player 2", 30);
        
        // Add them to the registered players list
        jugadoresRegistrados.add(jugador1);
        jugadoresRegistrados.add(jugador2);
        
    } catch (Exception e) {
        System.err.println("Error adding test players: " + e.getMessage());
    }
    }

    // inicia la aplicación.
    public void iniciarAplicacion() {
        mostrarTitulo();
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcionMenu();
            switch (opcion) {
                case 1:
                    registrarNuevoJugador();
                    break;
                case 2:
                    configurarPartida();
                    break;
                case 3:
                    jugarPartida();
                    break;
                case 4:
                    mostrarRanking();
                    break;
                case 5:
                    salir = true;
                    terminarPrograma();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            if (!salir) {
                presioneEnterParaContinuar();
            }
        }
        scanner.close();
    }

    // muestra el título.
    private void mostrarTitulo() {
        System.out.println("***************************************************");
        System.out.println("**           TRABAJO DESARROLLADO POR:           **");
        System.out.println("**[Matías Piedra 354007], [Joaquin Piedra 304804]**"); 
        System.out.println("***************************************************");
    }

    // muestra el menú principal.
    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Registrar un jugador");
        System.out.println("2. Configurar la partida");
        System.out.println("3. Comienzo de partida");
        System.out.println("4. Mostrar ranking y racha");
        System.out.println("5. Terminar el programa");
        System.out.print("Seleccione una opción: ");
    }

    // lee opción del menú.
    private int leerOpcionMenu() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            
        } finally {
            scanner.nextLine(); 
        }
        return opcion;
    }

    // registra un nuevo jugador.
    private void registrarNuevoJugador() {
        System.out.println("\n--- REGISTRO DE NUEVO JUGADOR ---");
        String nombre;
        int edad = -1;

        while (true) {
            System.out.print("Ingrese el nombre del jugador (único): ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                continue;
            }

            boolean nombreExiste = false;
            for (Jugador j : jugadoresRegistrados) {
                if (j.getNombre().equalsIgnoreCase(nombre)) { 
                    nombreExiste = true;
                    break;
                }
            }

            if (!nombreExiste) {
                break; 
            }
            System.out.println("El nombre '" + nombre + "' ya existe. Por favor, elija otro.");
        }

        while (true) {
            System.out.print("Ingrese la edad del jugador: ");
            try {
                edad = scanner.nextInt();
                scanner.nextLine(); 
                if (edad >= 0) {
                    break;
                }
                System.out.println("La edad no puede ser negativa.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida para la edad. Por favor, ingrese un número.");
                scanner.nextLine(); 
            }
        }

        try {
            Jugador nuevoJugador = new Jugador(nombre, edad);
            jugadoresRegistrados.add(nuevoJugador);
            System.out.println("Jugador '" + nuevoJugador.getNombre() + "' registrado exitosamente.");
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Error al crear el jugador: " + e.getMessage());
        }
    }

    // configura la partida.
    private void configurarPartida() {
        System.out.println("\n--- CONFIGURACIÓN DE PARTIDA ---");
        System.out.println("Configuración actual: \n" + configuracionActual);
        System.out.print("¿Desea usar la configuración por defecto (S) o personalizarla (N)? [S/N]: ");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        if (respuesta.equals("S")) {
            configuracionActual.resetToDefaults();
            System.out.println("Configuración restablecida a los valores por defecto.");
            System.out.println("\nNueva configuración:\n" + configuracionActual);
            return;
        }

        if (!respuesta.equals("N")) {
            System.out.println("Opción no válida. Por favor, ingrese S o N. Se mantiene la configuración actual.");
            return;
        }

        System.out.println("\nConfiguración personalizada:");
        boolean tempRequiereContacto;
        boolean tempLargoVariable;
        int tempLargoFijo = configuracionActual.getLargoFijo(); 
        int tempCantBandasFin;
        int tempCantTableros;

        String inputSN;

        // Validar Requiere Contacto
        while (true) {
            System.out.print("¿Se requiere contacto para nuevas bandas (después del 2do mov.)? (S/N) (Actual: " + (configuracionActual.isRequiereContacto() ? "Sí" : "No") + "): ");
            inputSN = scanner.nextLine().trim().toUpperCase();
            if (inputSN.equals("S")) {
                tempRequiereContacto = true;
                break;
            } else if (inputSN.equals("N")) {
                tempRequiereContacto = false;
                break;
            } else {
                System.out.println("Opción inválida. Por favor, ingrese S o N.");
            }
        }

        // Validar Largo de Bandas Variable
        while (true) {
            System.out.print("¿Largo de bandas variable (1-" + configuracionActual.getMaxLargoBanda() + ")? (S/N) (Actual: " + (configuracionActual.isLargoBandasVariable() ? "Variable" : "Fijo " + configuracionActual.getLargoFijo()) + "): ");
            inputSN = scanner.nextLine().trim().toUpperCase();
            if (inputSN.equals("S")) {
                tempLargoVariable = true;
                break;
            } else if (inputSN.equals("N")) {
                tempLargoVariable = false;
                break;
            } else {
                System.out.println("Opción inválida. Por favor, ingrese S o N.");
            }
        }

        if (!tempLargoVariable) {
            while (true) {
                System.out.print("Ingrese el largo fijo de las bandas (" + configuracionActual.getMinLargoBanda() + "-" + configuracionActual.getMaxLargoBanda() + ") (Actual: " + configuracionActual.getLargoFijo() + "): ");
                try {
                    int inputVal = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (inputVal >= configuracionActual.getMinLargoBanda() && inputVal <= configuracionActual.getMaxLargoBanda()) {
                        tempLargoFijo = inputVal;
                        break;
                    } else {
                        System.out.println("Largo inválido. Debe ser entre " + configuracionActual.getMinLargoBanda() + " y " + configuracionActual.getMaxLargoBanda() + ".");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Ingrese un número.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
        }

        // Validar Cantidad de Bandas para Finalizar
        while (true) {
            System.out.print("Ingrese la cantidad de bandas para finalizar la partida (mínimo " + configuracionActual.getMinBandasFin() + ") (Actual: " + configuracionActual.getCantidadBandasFin() + "): ");
            try {
                int inputVal = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (inputVal >= configuracionActual.getMinBandasFin()) {
                    tempCantBandasFin = inputVal;
                    break;
                } else {
                    System.out.println("Cantidad inválida. Debe ser como mínimo " + configuracionActual.getMinBandasFin() + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        // Validar Cantidad de Tableros a Mostrar
        while (true) {
            System.out.print("Ingrese la cantidad de tableros a mostrar (" + configuracionActual.getMinTablerosMostrar() + "-" + configuracionActual.getMaxTablerosMostrar() + ") (Actual: " + configuracionActual.getCantidadTablerosMostrar() + "): ");
            try {
                int inputVal = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (inputVal >= configuracionActual.getMinTablerosMostrar() && inputVal <= configuracionActual.getMaxTablerosMostrar()) {
                    tempCantTableros = inputVal;
                    break;
                } else {
                    System.out.println("Cantidad inválida. Debe ser entre " + configuracionActual.getMinTablerosMostrar() + " y " + configuracionActual.getMaxTablerosMostrar() + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        try {
            ConfiguracionPartida nuevaConfig = new ConfiguracionPartida(
                tempRequiereContacto,
                tempLargoVariable,
                tempLargoFijo,
                tempCantBandasFin,
                tempCantTableros
            );
            configuracionActual = nuevaConfig;
            System.out.println("Configuración actualizada exitosamente.");
            System.out.println("\nNueva configuración:\n" + configuracionActual);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear la configuración: " + e.getMessage());
            System.out.println("No se guardaron los cambios. Se mantiene la configuración anterior: \n" + configuracionActual);
        }
    }

    // inicia y juega partida.
    private void jugarPartida() {

        if (jugadoresRegistrados.size() < 2) {
            System.out.println("No hay suficientes jugadores registrados (se necesitan al menos 2).");
            return;
        }

        System.out.println("\n--- COMIENZO DE PARTIDA ---");

        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadoresRegistrados);
        Collections.sort(jugadoresOrdenados, Comparator.comparing(Jugador::getNombre, String.CASE_INSENSITIVE_ORDER));

        System.out.println("Lista de jugadores disponibles:");
        for (int i = 0; i < jugadoresOrdenados.size(); i++) {
            System.out.println((i + 1) + ". " + jugadoresOrdenados.get(i).getNombre());
        }

        Jugador jugador1 = null, jugador2 = null;
        int indiceJ1 = -1, indiceJ2 = -1;

        while (jugador1 == null) {
            System.out.print("Seleccione el número del primer jugador (será Blanco □): ");
            try {
                indiceJ1 = scanner.nextInt() - 1;
                scanner.nextLine(); 
                if (indiceJ1 >= 0 && indiceJ1 < jugadoresOrdenados.size()) {
                    jugador1 = jugadoresOrdenados.get(indiceJ1);
                } else {
                    System.out.println("Número de jugador inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }

        while (jugador2 == null) {
            System.out.print("Seleccione el número del segundo jugador (será Negro ■): ");
            try {
                indiceJ2 = scanner.nextInt() - 1;
                scanner.nextLine(); 
                if (indiceJ2 >= 0 && indiceJ2 < jugadoresOrdenados.size()) {
                    if (indiceJ2 == indiceJ1) {
                        System.out.println("Los jugadores deben ser diferentes.");
                    } else {
                        jugador2 = jugadoresOrdenados.get(indiceJ2);
                    }
                } else {
                    System.out.println("Número de jugador inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }
        
        Jugador jugadorBlanco = jugador1;
        Jugador jugadorNegro = jugador2;

        System.out.println("Jugador Blanco □: " + jugadorBlanco.getNombre());
        System.out.println("Jugador Negro ■: " + jugadorNegro.getNombre());
        System.out.println("Usando configuración: \n" + configuracionActual);
        
        partidaActual = new Partida(jugadorBlanco, jugadorNegro, configuracionActual);
        System.out.println("\n¡Que comience el juego!");

        // Main game loop
        while (partidaActual != null && !partidaActual.isPartidaTerminada()) {
            System.out.println("\n--- Tablero(s) ---");

            List<String> boardStringsToDisplay = new ArrayList<>();
            List<Tablero> snapshots = partidaActual.getHistorialDeTablerosSnapshots();
            int numConfigured = configuracionActual.getCantidadTablerosMostrar();

            if (!snapshots.isEmpty()) {
                // Los snapshots están en orden cronológico (el más antiguo primero, el más nuevo al final)
                // Queremos mostrar los últimos 'numConfigured' tableros.
                int numToShow = Math.min(snapshots.size(), numConfigured);
                int startIndex = snapshots.size() - numToShow;
                
                for (int i = 0; i < numToShow; i++) {
                    boardStringsToDisplay.add(snapshots.get(startIndex + i).toString());
                }
            }

            if (!boardStringsToDisplay.isEmpty()) {
                displayMultipleBoardStrings(boardStringsToDisplay);
            } else { // Fallback si no hay snapshots (no debería ocurrir con la lógica actual)
                System.out.println(partidaActual.getTablero().toString());
            }

            System.out.println(jugadorBlanco.getNombre() + " (Blancas □): " + partidaActual.getTriangulosJugadorBlanco() + " triángulos.");
            System.out.println(jugadorNegro.getNombre() + " (Negras ■): " + partidaActual.getTriangulosJugadorNegro() + " triángulos.");
            
            int bandasColocadas = partidaActual.getBandasColocadasEnPartida(); 
            System.out.println("Bandas colocadas: " + bandasColocadas + "/" + configuracionActual.getCantidadBandasFin());
            
            System.out.println("Turno de: " + partidaActual.getTurnoActual().getNombre() + 
            (partidaActual.getTurnoActual().equals(jugadorBlanco) ? " (Blanco □)" : " (Negro ■)"));
            
            if (partidaActual.getMovimientosRealizados() == 0) {
                String ejemploCantidadStr = Integer.toString(configuracionActual.getLargoFijo());
                System.out.print("Ingrese su jugada (ej: D1C" + ejemploCantidadStr + " para banda, H para historial, X para abandonar): ");
            } else {
                System.out.print("Ingrese su jugada (H para historial, X para abandonar): ");
            }
            String entrada = scanner.nextLine().trim(); 

            partidaActual.procesarJugada(entrada);
        }
        
        // After the loop, the game is over.
        // The Partida class has already printed all necessary end-of-game messages.
        // Ensure there are NO System.out.println calls here for the board,
        // winner, or "--- Fin de la Partida ---".

        partidaActual = null; // Clean up the current game instance.
    }

    // muestra el ranking.
    private void mostrarRanking() {
        System.out.println("\n--- RANKING DE JUGADORES ---");
        if (jugadoresRegistrados.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        ArrayList<Jugador> ranking = new ArrayList<>(jugadoresRegistrados);
        
        Collections.sort(ranking, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador j1, Jugador j2) {
                int comparacionGanadas = Integer.compare(j2.getPartidasGanadas(), j1.getPartidasGanadas());
                if (comparacionGanadas != 0) {
                    return comparacionGanadas;
                }
                return j1.getNombre().compareToIgnoreCase(j2.getNombre());
            }
        });

        System.out.println("Pos. | Nombre            | Edad | Ganadas | Racha Act. | Mejor Racha");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < ranking.size(); i++) {
            Jugador j = ranking.get(i);
            System.out.printf("%-4d | %-16s | %-4d | %-7d | %-10d | %-11d\n",
                    (i + 1),
                    j.getNombre(),
                    j.getEdad(),
                    j.getPartidasGanadas(),
                    j.getRachaActualVictorias(),
                    j.getMejorRachaVictorias());
        }
        System.out.println("---------------------------------------------------------------");

        int rachaMasLarga = 0;
        for (Jugador j : jugadoresRegistrados) {
            if (j.getMejorRachaVictorias() > rachaMasLarga) {
                rachaMasLarga = j.getMejorRachaVictorias();
            }
        }

        if (rachaMasLarga > 0) {
            System.out.print("\nJugador(es) con la racha ganadora más larga (" + rachaMasLarga + " victorias): ");
            List<String> jugadoresConMejorRacha = new ArrayList<>();
            for (Jugador j : jugadoresRegistrados) {
                if (j.getMejorRachaVictorias() == rachaMasLarga) {
                    jugadoresConMejorRacha.add(j.getNombre());
                }
            }
            System.out.println(String.join(", ", jugadoresConMejorRacha));
        } else {
            System.out.println("\nNadie tiene una racha ganadora registrada aún.");
        }
    }

    // termina el programa.
    private void terminarPrograma() {
        System.out.println("\nGracias por jugar a Triángulos. ¡Hasta pronto!");
    }

    // espera enter para continuar.
    private void presioneEnterParaContinuar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine(); 
    }

    // NUEVO: Método para mostrar múltiples tableros lado a lado
    private void displayMultipleBoardStrings(List<String> boardStrings) {
        if (boardStrings.isEmpty()) {
            return;
        }

        List<String[]> splitBoardLines = new ArrayList<>();
        int maxLines = 0;
        int boardDisplayWidth = 30; // Ancho para cada tablero, ajustar si es necesario

        for (String bs : boardStrings) {
            String[] lines = bs.split("\n");
            splitBoardLines.add(lines);
            if (lines.length > maxLines) {
                maxLines = lines.length;
            }
        }

        for (int i = 0; i < maxLines; i++) {
            String lineToShow = ""; 
            for (String[] boardLines : splitBoardLines) {
                if (i < boardLines.length) {
                    lineToShow += String.format("%-" + boardDisplayWidth + "s", boardLines[i]); // Concatenación de String
                } else {
                    
                    lineToShow += String.format("%-" + boardDisplayWidth + "s", ""); // Concatenación de String
                }
                lineToShow += "    ";
            }
            
            if (lineToShow.length() > 4) { 
                 System.out.println(lineToShow.substring(0, lineToShow.length() - 4));
            } else {
                 System.out.println(lineToShow);
            }
        } 
    }
}
