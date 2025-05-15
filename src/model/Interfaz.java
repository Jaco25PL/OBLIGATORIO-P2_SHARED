/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */

package model;

import model.ConfiguracionPartida;
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
        System.out.println("**        TRABAJO DESARROLLADO POR:            **");
        System.out.println("**  [Matías Piedra 354007], [Joaquin Piedra]   **"); 
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
        System.out.println("Configuración actual: " + configuracionActual);
        System.out.print("¿Desea usar la configuración por defecto (S) o personalizarla (N)? [S/N]: ");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        if (respuesta.equals("S")) {
            configuracionActual.resetToDefaults();
            System.out.println("Configuración restablecida a los valores por defecto.");
            System.out.println("Nueva configuración: " + configuracionActual);
            return;
        }

        if (!respuesta.equals("N")) {
            System.out.println("Opción no válida. Se mantiene la configuración actual.");
            return;
        }

        System.out.println("Configuración personalizada:");
        boolean tempRequiereContacto;
        boolean tempLargoVariable;
        int tempLargoFijo = configuracionActual.getLargoFijo(); 
        int tempCantBandasFin;
        int tempCantTableros;

        System.out.print("¿Se requiere contacto para nuevas bandas (después del 2do mov.)? (S/N) (Actual: " + (configuracionActual.isRequiereContacto() ? "Sí" : "No") + "): ");
        tempRequiereContacto = scanner.nextLine().trim().equalsIgnoreCase("S");

        System.out.print("¿Largo de bandas variable (1-" + ConfiguracionPartida.MAX_LARGO_BANDA + ")? (S/N) (Actual: " + (configuracionActual.isLargoBandasVariable() ? "Variable" : "Fijo " + configuracionActual.getLargoFijo()) + "): ");
        tempLargoVariable = scanner.nextLine().trim().equalsIgnoreCase("S");

        if (!tempLargoVariable) {
            while (true) {
                System.out.print("Ingrese el largo fijo de las bandas (" + ConfiguracionPartida.MIN_LARGO_BANDA + "-" + ConfiguracionPartida.MAX_LARGO_BANDA + ") (Actual: " + configuracionActual.getLargoFijo() + "): ");
                try {
                    tempLargoFijo = scanner.nextInt();
                    scanner.nextLine();
                    
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Ingrese un número.");
                    scanner.nextLine();
                }
            }
        }

        while (true) {
            System.out.print("Ingrese la cantidad de bandas para finalizar la partida (mínimo " + ConfiguracionPartida.MIN_BANDAS_FIN + ") (Actual: " + configuracionActual.getCantidadBandasFin() + "): ");
            try {
                tempCantBandasFin = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }

        while (true) {
            System.out.print("Ingrese la cantidad de tableros a mostrar (" + ConfiguracionPartida.MIN_TABLEROS_MOSTRAR + "-" + ConfiguracionPartida.MAX_TABLEROS_MOSTRAR + ") (Actual: " + configuracionActual.getCantidadTablerosMostrar() + "): ");
            try {
                tempCantTableros = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
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
            System.out.println("Nueva configuración: " + configuracionActual);
        } catch (IllegalArgumentException e) {
            System.err.println("Error en la configuración: " + e.getMessage());
            System.out.println("No se guardaron los cambios. Se mantiene la configuración anterior: " + configuracionActual);
        }
    }

    // inicia y juega partida.
    private void jugarPartida() {
        System.out.println("\n--- COMIENZO DE PARTIDA ---");

        if (jugadoresRegistrados.size() < 2) {
            System.out.println("No hay suficientes jugadores registrados (se necesitan al menos 2).");
            return;
        }

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
        System.out.println("Usando configuración: " + configuracionActual);
        
        partidaActual = new Partida(jugadorBlanco, jugadorNegro, configuracionActual);
        System.out.println("\n¡Que comience el juego!");


        while (partidaActual != null && !partidaActual.isPartidaTerminada()) {
            System.out.println("\n--- Tablero Actual ---");
            System.out.println(partidaActual.getTablero().toString()); 

            System.out.println(jugadorBlanco.getNombre() + " (Blancas □): " + partidaActual.getTriangulosJugadorBlanco() + " triángulos.");
            System.out.println(jugadorNegro.getNombre() + " (Negras ■): " + partidaActual.getTriangulosJugadorNegro() + " triángulos.");
            
            int bandasColocadas = partidaActual.getBandasColocadasEnPartida(); // Use the new getter
            System.out.println("Bandas colocadas: " + bandasColocadas + "/" + configuracionActual.getCantidadBandasFin());
            
            System.out.println("Turno de: " + partidaActual.getTurnoActual().getNombre() + 
            (partidaActual.getTurnoActual().equals(jugadorBlanco) ? " (Blanco □)" : " (Negro ■)"));
            
            String ejemploCantidadStr = Integer.toString(configuracionActual.getLargoFijo());
            System.out.print("Ingrese su jugada (ej: D1C" + ejemploCantidadStr + " para banda, H para historial, X para abandonar): ");
            String entrada = scanner.nextLine().trim(); 

            partidaActual.procesarJugada(entrada);
            
        }
        
        System.out.println("\n--- Fin de la Partida ---");
        if (partidaActual != null && partidaActual.getTablero() != null) {
             System.out.println(partidaActual.getTablero().toString()); 
        }

        if (partidaActual != null) {
            if (partidaActual.getJugadorAbandono() != null) {
                System.out.println("Partida terminada por abandono de " + partidaActual.getJugadorAbandono().getNombre() + ".");
            }
            Jugador ganador = partidaActual.getGanador();
            if (ganador != null) {
                System.out.println("¡El ganador es " + ganador.getNombre() + "!");
                
                
            } else if (partidaActual.getJugadorAbandono() == null && partidaActual.isPartidaTerminada()) { 
                
                System.out.println("¡La partida es un empate!");
            }
        }
        partidaActual = null; 
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
}
