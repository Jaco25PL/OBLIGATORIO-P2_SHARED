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
                if (j.getNombre().equalsIgnoreCase(nombre)) { // Compara ignorando mayúsculas/minúsculas
                    nombreExiste = true;
                    break;
                }
            }

            if (!nombreExiste) {
                break; // Nombre válido y no existente
            }
            System.out.println("El nombre '" + nombre + "' ya existe. Por favor, elija otro.");
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
            Jugador nuevoJugador = new Jugador(nombre, edad);
            jugadoresRegistrados.add(nuevoJugador);
            System.out.println("Jugador '" + nuevoJugador.getNombre() + "' registrado exitosamente.");
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

        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadoresRegistrados);
        Collections.sort(jugadoresOrdenados, Comparator.comparing(Jugador::getNombre, String.CASE_INSENSITIVE_ORDER));

        // Asignar automáticamente los dos primeros jugadores de la lista ordenada.
        Jugador jugadorBlanco = jugadoresOrdenados.get(0);
        Jugador jugadorNegro = jugadoresOrdenados.get(1);

        System.out.println("Jugador Blanco □: " + jugadorBlanco.getNombre());
        System.out.println("Jugador Negro ■: " + jugadorNegro.getNombre());
        System.out.println("Usando configuración: " + configuracionActual);
        System.out.println("\n¡Que comience el juego!");

        // Crear una instancia del tablero para la partida
        Tablero tablero = new Tablero();
        // Aquí se podría instanciar la Partida y pasarle los jugadores, config y tablero
        // Partida partida = new Partida(jugadorBlanco, jugadorNegro, configuracionActual, tablero);
        // partida.iniciar(); // Método hipotético en Partida

        // --- Inicio del bucle de juego (simplificado) ---
        boolean partidaTerminada = false;
        Jugador jugadorActual = jugadorBlanco;
        int movimientos = 0;
        // Suponiendo que la partida termina después de X bandas o por abandono
        // int bandasParaTerminar = configuracionActual.getCantidadBandasFin(); 

        while (!partidaTerminada) {
            System.out.println("\n--- Tablero Actual ---");
            System.out.println(tablero.toString()); // Mostrar el tablero

            // Mostrar información de la partida (puntos, etc.)
            // System.out.println(jugadorBlanco.getNombre() + " (Blancas): " + tablero.getTriangulosGanadosPor(jugadorBlanco).size() + " triángulos.");
            // System.out.println(jugadorNegro.getNombre() + " (Negras): " + tablero.getTriangulosGanadosPor(jugadorNegro).size() + " triángulos.");
            // System.out.println("Bandas colocadas: " + tablero.getBandas().size() + "/" + bandasParaTerminar);


            System.out.println("\nTurno de: " + jugadorActual.getNombre() + (jugadorActual == jugadorBlanco ? " (Blancas)" : " (Negras)"));
            System.out.print("Ingrese su jugada (ej: D1C3 para banda, H para historial, X para abandonar): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("X")) {
                System.out.println(jugadorActual.getNombre() + " ha abandonado la partida.");
                // Aquí se determinaría el ganador (el otro jugador)
                // jugadorActual.resetearRachaActual(); // El que abandona pierde racha
                // Jugador ganador = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
                // ganador.incrementarPartidasGanadas();
                // System.out.println(ganador.getNombre() + " gana la partida por abandono.");
                partidaTerminada = true;
                continue;
            } else if (entrada.equals("H")) {
                System.out.println("Historial de jugadas (funcionalidad no implementada en esta simulación).");
                // Aquí se mostraría el historial de la partida.
                continue; // No cuenta como turno
            }

            // --- Lógica de procesar jugada (Placeholder) ---
            // 1. Parsear la entrada (ej: "D1C3") para obtener puntos y dirección/largo.
            // 2. Validar la jugada:
            //    - Puntos existen en el tablero.
            //    - Puntos son adyacentes (si es banda de largo 1) o válidos para la dirección/largo.
            //    - La banda no se superpone con otra existente.
            //    - Cumple con la regla de contacto (si aplica).
            // 3. Si la jugada es válida:
            //    - Crear el objeto Banda.
            //    - tablero.addBanda(nuevaBanda);
            //    - Incrementar contador de bandas del jugador.
            //    - Detectar nuevos triángulos:
            //        - List<Triangulo> nuevosTriangulos = detectarTriangulos(nuevaBanda, tablero);
            //        - For (Triangulo t : nuevosTriangulos) { tablero.addTrianguloGanado(t); t.setJugadorGanador(jugadorActual); }
            // 4. Si la jugada es inválida, mostrar mensaje y el jugador intenta de nuevo.

            System.out.println("Procesando jugada '" + entrada + "'... (lógica de jugada no implementada)");
            // Simulación de una banda colocada para ver cambios (eliminar/reemplazar con lógica real)
            if (movimientos == 0 && tablero.getPunto('D',1) != null && tablero.getPunto('C',2) != null) {
                try {
                    Banda bandaSimulada = new Banda(tablero.getPunto('D',1), tablero.getPunto('C',2), jugadorActual);
                    tablero.addBanda(bandaSimulada);
                    System.out.println("Banda simulada D1-C2 colocada.");
                } catch (Exception e) { System.out.println("Error al simular banda: " + e.getMessage());}
            } else if (movimientos == 1 && tablero.getPunto('F',1) != null && tablero.getPunto('E',2) != null) {
                 try {
                    Banda bandaSimulada2 = new Banda(tablero.getPunto('F',1), tablero.getPunto('E',2), jugadorActual);
                    tablero.addBanda(bandaSimulada2);
                    System.out.println("Banda simulada F1-E2 colocada.");
                } catch (Exception e) { System.out.println("Error al simular banda: " + e.getMessage());}
            }


            movimientos++;

            // --- Verificar condiciones de fin de partida ---
            // if (tablero.getBandas().size() >= bandasParaTerminar) {
            //     System.out.println("Se ha alcanzado el límite de bandas.");
            //     partidaTerminada = true;
            //     // Determinar ganador por puntos (triángulos)
            // }
            // Otra condición podría ser que no queden movimientos válidos.

            if (movimientos >= 5) { // Simulación de fin de partida después de algunos turnos
                System.out.println("\nSimulación de partida terminada después de " + movimientos + " movimientos.");
                // Aquí se calcularía el ganador real basado en triángulos.
                // Por ahora, solo terminamos.
                partidaTerminada = true;
            }

            if (!partidaTerminada) {
                // Alternar turno
                jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
            }
        }
        // --- Fin del bucle de juego ---

        System.out.println("\n--- Fin de la Partida ---");
        System.out.println(tablero.toString()); // Mostrar tablero final

        // Actualizar estadísticas de jugadores (esto debería hacerse en la clase Partida o aquí si no hay Partida)
        // Ejemplo:
        // Jugador ganador = determinarGanador(tablero, jugadorBlanco, jugadorNegro);
        // if (ganador != null) {
        //     ganador.incrementarPartidasGanadas();
        //     Jugador perdedor = (ganador == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
        //     perdedor.resetearRachaActual();
        //     System.out.println("¡El ganador es " + ganador.getNombre() + "!");
        //     // Aquí iría la animación
        // } else {
        //     System.out.println("¡La partida es un empate!");
        //     jugadorBlanco.resetearRachaActual();
        //     jugadorNegro.resetearRachaActual();
        // }
        System.out.println("La lógica completa de juego y determinación de ganador aún no está implementada.");
    }

    private void mostrarRanking() {
        System.out.println("\n--- RANKING DE JUGADORES ---");
        if (jugadoresRegistrados.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        ArrayList<Jugador> ranking = new ArrayList<>(jugadoresRegistrados);
        // Ordenar por partidas ganadas (descendente), luego por nombre (ascendente) como criterio secundario
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

    private void terminarPrograma() {
        System.out.println("\nGracias por jugar a Triángulos. ¡Hasta pronto!");
    }

    private void presioneEnterParaContinuar() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine(); // Espera a que el usuario presione Enter
    }
}
