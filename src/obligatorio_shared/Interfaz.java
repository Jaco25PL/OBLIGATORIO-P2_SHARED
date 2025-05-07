package obligatorio_shared;

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
    // private Partida partidaEnCurso; // Descomentar cuando la clase Partida esté lista

    public Interfaz() {
        this.scanner = new Scanner(System.in);
        this.jugadoresRegistrados = new ArrayList<>();
        // Por defecto, la configuración se inicializa con los valores default de ConfiguracionPartida
        this.configuracionActual = new ConfiguracionPartida();
        // this.partidaEnCurso = null;
    }

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

    private void mostrarTitulo() {
        System.out.println("***************************************************");
        System.out.println("**        TRABAJO DESARROLLADO POR:            **");
        System.out.println("**  [Matías Piedra 354007], [Joaquin Piedra]   **"); // Reemplazar con datos reales
        System.out.println("***************************************************");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Registrar un jugador");
        System.out.println("2. Configurar la partida");
        System.out.println("3. Comienzo de partida");
        System.out.println("4. Mostrar ranking y racha");
        System.out.println("5. Terminar el programa");
        System.out.print("Seleccione una opción: ");
    }

    private int leerOpcionMenu() {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            // No se consume el newline aquí, se hará en el finally para cubrir todos los casos
        } finally {
            scanner.nextLine(); // Consumir el newline restante o la entrada incorrecta
        }
        return opcion;
    }

    private void registrarNuevoJugador() {
        System.out.println("\n--- REGISTRO DE NUEVO JUGADOR ---");
        String nombre;
        String username;
        int edad = -1;

        while (true) {
            System.out.print("Ingrese el nombre del jugador: ");
            nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) {
                break;
            }
            System.out.println("El nombre no puede estar vacío.");
        }

        while (true) {
            System.out.print("Ingrese el username del jugador (único): ");
            username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("El username no puede estar vacío.");
                continue;
            }
            boolean usernameExiste = false;
            for (Jugador j : jugadoresRegistrados) {
                if (j.getUsername().equalsIgnoreCase(username)) { // Compara ignorando mayúsculas/minúsculas
                    usernameExiste = true;
                    break;
                }
            }
            if (!usernameExiste) {
                break;
            }
            System.out.println("El username '" + username + "' ya existe. Por favor, elija otro.");
        }

        while (true) {
            System.out.print("Ingrese la edad del jugador: ");
            try {
                edad = scanner.nextInt();
                scanner.nextLine(); // Consumir newline
                if (edad >= 0) {
                    break;
                }
                System.out.println("La edad no puede ser negativa.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida para la edad. Por favor, ingrese un número.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }

        try {
            Jugador nuevoJugador = new Jugador(nombre, username, edad);
            jugadoresRegistrados.add(nuevoJugador);
            System.out.println("Jugador '" + nuevoJugador.getUsername() + "' registrado exitosamente.");
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Error al crear el jugador: " + e.getMessage());
        }
    }

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
        int tempLargoFijo = configuracionActual.getLargoFijo(); // Valor por si no se cambia
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
                    // La validación se hará al crear el objeto ConfiguracionPartida
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
            // Se crea una nueva instancia para validar. Si es válida, se asigna.
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

    private void jugarPartida() {
        System.out.println("\n--- COMIENZO DE PARTIDA ---");

        if (jugadoresRegistrados.size() < 2) {
            System.out.println("No hay suficientes jugadores registrados (se necesitan al menos 2).");
            return;
        }

        System.out.println("Jugadores disponibles (ordenados alfabéticamente por username):");
        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadoresRegistrados);
        // Ordenar por username, ignorando mayúsculas/minúsculas
        Collections.sort(jugadoresOrdenados, Comparator.comparing(Jugador::getUsername, String.CASE_INSENSITIVE_ORDER));

        for (int i = 0; i < jugadoresOrdenados.size(); i++) {
            System.out.println((i + 1) + ". " + jugadoresOrdenados.get(i).getUsername() + " (" + jugadoresOrdenados.get(i).getNombre() + ")");
        }

        Jugador jugadorBlanco = null;
        Jugador jugadorNegro = null;
        int indiceJ1 = -1;

        while (jugadorBlanco == null) {
            System.out.print("Seleccione el número del Jugador 1 (Blancas): ");
            try {
                indiceJ1 = scanner.nextInt() - 1;
                scanner.nextLine();
                if (indiceJ1 >= 0 && indiceJ1 < jugadoresOrdenados.size()) {
                    jugadorBlanco = jugadoresOrdenados.get(indiceJ1);
                } else {
                    System.out.println("Número de jugador inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }

        while (jugadorNegro == null) {
            System.out.print("Seleccione el número del Jugador 2 (Negras): ");
            try {
                int indiceJ2 = scanner.nextInt() - 1;
                scanner.nextLine();
                if (indiceJ2 >= 0 && indiceJ2 < jugadoresOrdenados.size()) {
                    if (indiceJ2 == indiceJ1) {
                        System.out.println("Los jugadores deben ser diferentes.");
                    } else {
                        jugadorNegro = jugadoresOrdenados.get(indiceJ2);
                    }
                } else {
                    System.out.println("Número de jugador inválido.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }

        System.out.println("\nJugador Blanco: " + jugadorBlanco.getUsername());
        System.out.println("Jugador Negro: " + jugadorNegro.getUsername());
        System.out.println("Usando configuración: " + configuracionActual);
        System.out.println("\n¡Que comience el juego!");

        // --- Placeholder para la lógica de la Partida ---
        System.out.println("\n(Aquí se instanciaría la clase Partida y comenzaría el bucle de juego)");
        System.out.println("(Se mostraría el tablero, se pedirían jugadas, se alternarían turnos, etc.)");
        System.out.println("Ejemplo de interacción (a implementar con la clase Partida):");
        System.out.println("  - Mostrar tablero (usando Tablero.toString())");
        System.out.println("  - Indicar turno: " + jugadorBlanco.getUsername());
        System.out.println("  - Pedir jugada: LetraFilaDireccionCantidad (ej: D1C3), H para historial, X para abandonar");
        System.out.println("  - Validar y procesar jugada...");
        System.out.println("  - Detectar triángulos, actualizar puntajes...");
        System.out.println("  - Verificar fin de partida...");
        System.out.println("  - Mostrar animación si hay ganador...");
        // --- Fin Placeholder ---

        // Simulación de fin de partida para probar estadísticas (eliminar cuando Partida esté lista)
        // System.out.println("\nSimulando fin de partida...");
        // System.out.print("¿Quién ganó? (1 para " + jugadorBlanco.getUsername() + ", 2 para " + jugadorNegro.getUsername() + ", 0 para empate): ");
        // String ganadorSimulado = scanner.nextLine();
        // if (ganadorSimulado.equals("1")) {
        //     jugadorBlanco.incrementarPartidasGanadas();
        //     jugadorNegro.resetearRachaActual();
        //     System.out.println(jugadorBlanco.getUsername() + " gana la partida simulada.");
        // } else if (ganadorSimulado.equals("2")) {
        //     jugadorNegro.incrementarPartidasGanadas();
        //     jugadorBlanco.resetearRachaActual();
        //     System.out.println(jugadorNegro.getUsername() + " gana la partida simulada.");
        // } else {
        //     jugadorBlanco.resetearRachaActual();
        //     jugadorNegro.resetearRachaActual();
        //     System.out.println("Partida simulada termina en empate.");
        // }
        // System.out.println("Funcionalidad de juego real aún no implementada.");
    }

    private void mostrarRanking() {
        System.out.println("\n--- RANKING DE JUGADORES ---");
        if (jugadoresRegistrados.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        ArrayList<Jugador> ranking = new ArrayList<>(jugadoresRegistrados);
        // Ordenar por partidas ganadas (descendente), luego por username (ascendente) como criterio secundario
        Collections.sort(ranking, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador j1, Jugador j2) {
                int comparacionGanadas = Integer.compare(j2.getPartidasGanadas(), j1.getPartidasGanadas());
                if (comparacionGanadas != 0) {
                    return comparacionGanadas;
                }
                return j1.getUsername().compareToIgnoreCase(j2.getUsername());
            }
        });

        System.out.println("Pos. | Username         | Nombre              | Edad | Ganadas | Racha Act. | Mejor Racha");
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i = 0; i < ranking.size(); i++) {
            Jugador j = ranking.get(i);
            System.out.printf("%-4d | %-16s | %-19s | %-4d | %-7d | %-10d | %-11d\n",
                    (i + 1),
                    j.getUsername(),
                    j.getNombre(),
                    j.getEdad(),
                    j.getPartidasGanadas(),
                    j.getRachaActualVictorias(),
                    j.getMejorRachaVictorias());
        }
        System.out.println("---------------------------------------------------------------------------------------");

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
                    jugadoresConMejorRacha.add(j.getUsername());
                }
            }
            System.out.println(String.join(", ", jugadoresConMejorRacha));
        } else {
            System.out.println("\nNadie tiene una racha ganadora registrada aún.");
        }
    }

    private void terminarPrograma() {
        System.out.println("\nGracias por jugar a Triángulos. ¡Hasta pronto!");
    }

    private void presioneEnterParaContinuar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine(); // Espera a que el usuario presione Enter
    }
}
