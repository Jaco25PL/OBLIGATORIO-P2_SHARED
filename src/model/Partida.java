/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Tablero tablero;
    private ConfiguracionPartida configuracion;
    private Jugador turnoActual;
    private int bandasColocadasEnPartida;
    private int triangulosJugadorBlanco;
    private int triangulosJugadorNegro;
    private List<String> historialJugadas;
    private boolean partidaTerminada;
    private Jugador ganador;
    private Jugador jugadorAbandono;
    private int movimientosRealizados;
    private int maxTriangulosPosibles;
    private List<Tablero> historialDeTablerosSnapshots;
    private int maxHistorialSnapshots;

    // Creates a new game instance.
    public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, ConfiguracionPartida configuracion) {
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
        this.configuracion = configuracion;
        this.tablero = new Tablero();
        this.turnoActual = jugadorBlanco;
        this.bandasColocadasEnPartida = 0;
        this.triangulosJugadorBlanco = 0;
        this.triangulosJugadorNegro = 0;
        this.historialJugadas = new ArrayList<>();
        this.partidaTerminada = false;
        this.ganador = null;
        this.jugadorAbandono = null;
        this.movimientosRealizados = 0;
        this.maxTriangulosPosibles = 54;

        this.maxHistorialSnapshots = configuracion.getCantidadTablerosMostrar();
        this.historialDeTablerosSnapshots = new ArrayList<>();

        if (this.maxHistorialSnapshots > 0) {
            this.historialDeTablerosSnapshots.add(new Tablero(this.tablero));
        }
    }

    // Gets board history snapshots.
    public List<Tablero> getHistorialDeTablerosSnapshots() {
        return new ArrayList<>(this.historialDeTablerosSnapshots);
    }

    // Gets current player's turn.
    public Jugador getTurnoActual() {
        return turnoActual;
    }

    // Gets the current game board.
    public Tablero getTablero() {
        return tablero;
    }

    // Gets white player's triangles.
    public int getTriangulosJugadorBlanco() {
        return triangulosJugadorBlanco;
    }

    // Gets black player's triangles.
    public int getTriangulosJugadorNegro() {
        return triangulosJugadorNegro;
    }

    // Gets list of moves made.
    public List<String> getHistorialJugadas() {
        return new ArrayList<>(historialJugadas);
    }

    // Checks if game has ended.
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    // Gets the game winner.
    public Jugador getGanador() {
        return ganador;
    }

    // Gets player who forfeited.
    public Jugador getJugadorAbandono() {
        return jugadorAbandono;
    }

    // Gets bands placed in game.
    public int getBandasColocadasEnPartida() {
        return bandasColocadasEnPartida;
    }

    // Gets total moves made.
    public int getMovimientosRealizados() {
        return movimientosRealizados;
    }

    // Processes player's move input.
    public boolean procesarJugada(String inputJugada) {
        boolean continuarProcesando = true;
        boolean jugadaValidaYProcesada = true;

        if (partidaTerminada) {
            System.out.println("La partida ya ha terminado.");
            jugadaValidaYProcesada = false;
            continuarProcesando = false;
        }

        if (continuarProcesando) {
            String inputUpper = inputJugada.toUpperCase();

            if ("X".equals(inputUpper)) {
                abandonarPartida(turnoActual);
                // jugadaValidaYProcesada remains true
                continuarProcesando = false;
            } else if ("H".equals(inputUpper)) {
                mostrarHistorial();
                jugadaValidaYProcesada = false; // History shown, but not a game move processed
                continuarProcesando = false;
            }
        }

        if (continuarProcesando) {
            ParsedJugada jugada = parsearJugadaInput(inputJugada.toUpperCase());
            if (jugada == null) {
                System.out.println("Formato de jugada incorrecto.");
                // jugadaValidaYProcesada remains true (as per original logic, it returned true)
                continuarProcesando = false;
            } else {
                if (!validarLogicaJugada(jugada)) {
                    // jugadaValidaYProcesada remains true (as per original logic, it returned true)
                    continuarProcesando = false;
                } else {
                    Punto puntoActualLoop = jugada.getOrigen();
                    boolean errorEnLoop = false;

                    for (int i = 0; i < jugada.getLargo() && !errorEnLoop; i++) {
                        Punto puntoSiguiente = calcularPuntoSiguiente(puntoActualLoop, jugada.getDireccion());

                        if (puntoSiguiente == null || tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila()) == null) {
                            System.out.println("Error: Movimiento fuera de tablero.");
                            errorEnLoop = true;
                        } else {
                            Punto pA = tablero.getPunto(puntoActualLoop.getColumna(), puntoActualLoop.getFila());
                            Punto pB = tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila());

                            if (pA == null || pB == null || !Tablero.sonPuntosAdyacentes(pA, pB)) {
                                System.out.println("Error: Segmento inválido.");
                                errorEnLoop = true;
                            } else {
                                Banda nuevoSegmento = new Banda(pA, pB, turnoActual);
                                tablero.addBanda(nuevoSegmento);

                                int nuevosTriangulos = detectarNuevosTriangulosConBanda(nuevoSegmento);
                                if (turnoActual.equals(jugadorBlanco)) {
                                    triangulosJugadorBlanco += nuevosTriangulos;
                                } else {
                                    triangulosJugadorNegro += nuevosTriangulos;
                                }
                                puntoActualLoop = puntoSiguiente;
                            }
                        }
                    }

                    if (errorEnLoop) {
                        // jugadaValidaYProcesada remains true (as per original logic, it returned true)
                        // No further processing for this move
                    } else {
                        this.bandasColocadasEnPartida++;
                        historialJugadas.add(inputJugada);
                        movimientosRealizados++;

                        if (this.maxHistorialSnapshots > 0) {
                            this.historialDeTablerosSnapshots.add(new Tablero(this.tablero));
                            while (this.historialDeTablerosSnapshots.size() > this.maxHistorialSnapshots) {
                                this.historialDeTablerosSnapshots.remove(0);
                            }
                        }

                        if (verificarFinPartida()) {
                            determinarGanadorFinal();
                        } else {
                            cambiarTurno();
                        }
                        // jugadaValidaYProcesada remains true
                    }
                }
            }
        }
        return jugadaValidaYProcesada;
    }

    // Detects new triangles from band.
    private int detectarNuevosTriangulosConBanda(Banda banda) {
        int nuevos = 0;
        Punto a = banda.getPuntoA();
        Punto b = banda.getPuntoB();
        Jugador jugador = banda.getJugador();

        List<Punto> adyacentesA = tablero.getPuntosAdyacentes(a);
        List<Punto> adyacentesB = tablero.getPuntosAdyacentes(b);

        for (Punto c : adyacentesA) {
            if (adyacentesB.contains(c)) {
                Banda acBanda = null;
                Banda bcBanda = null;

                for (Banda other : tablero.getBandas()) {
                    if ((other.getPuntoA().equals(a) && other.getPuntoB().equals(c)) ||
                        (other.getPuntoA().equals(c) && other.getPuntoB().equals(a))) {
                        acBanda = other;
                    }
                    if ((other.getPuntoA().equals(b) && other.getPuntoB().equals(c)) ||
                        (other.getPuntoA().equals(c) && other.getPuntoB().equals(b))) {
                        bcBanda = other;
                    }
                }

                if (acBanda != null && bcBanda != null) {
                    Triangulo nuevoTriangulo = new Triangulo(a, b, c);
                    boolean yaExiste = false;
                    for (Triangulo existente : tablero.getTriangulosGanados()) {
                        if (existente.equals(nuevoTriangulo)) {
                            yaExiste = true;
                            break;
                        }
                    }
                    if (!yaExiste) {
                        nuevoTriangulo.setJugadorGanador(jugador, jugador.equals(jugadorBlanco));
                        tablero.addTrianguloGanado(nuevoTriangulo);
                        nuevos++;
                    }
                }
            }
        }
        return nuevos;
    }

    // Parses player move string.
    private ParsedJugada parsearJugadaInput(String input) {
        ParsedJugada jugadaParseada = null;
        boolean errorParseo = false;

        if (input == null || input.length() < 2) {
            errorParseo = true;
        }

        if (!errorParseo) {
            char colChar = input.charAt(0);
            int fila = 0; // Initialize
            Direccion dir = null; // Initialize
            int largo = configuracion.isLargoBandasVariable() ? 0 : configuracion.getLargoFijo();
            int dirIndex = 0; // Initialize

            if (Character.isDigit(input.charAt(1))) {
                if (input.length() < 3) {
                    errorParseo = true;
                } else {
                    try {
                        fila = Integer.parseInt(input.substring(1, 2));
                        dirIndex = 2;
                    } catch (NumberFormatException e) {
                        errorParseo = true;
                    }
                }
            } else {
                errorParseo = true;
            }

            if (!errorParseo) {
                if (input.length() <= dirIndex) {
                    errorParseo = true;
                } else {
                    char dirCharInput = input.charAt(dirIndex);
                    char upperDirChar = Character.toUpperCase(dirCharInput);

                    if (!(upperDirChar == 'Q' || upperDirChar == 'E' || upperDirChar == 'D' ||
                          upperDirChar == 'C' || upperDirChar == 'Z' || upperDirChar == 'A')) {
                        System.out.println("Dirección inválida: " + dirCharInput);
                        errorParseo = true;
                    } else {
                        dir = new Direccion(dirCharInput);
                    }
                }
            }

            if (!errorParseo) {
                if (input.length() > dirIndex + 1) {
                    try {
                        largo = Integer.parseInt(input.substring(dirIndex + 1));
                    } catch (NumberFormatException e) {
                        System.out.println("Largo de banda inválido.");
                        errorParseo = true;
                    }
                } else {
                    if (configuracion.isLargoBandasVariable()) {
                        largo = 4; // Default if not specified and variable
                    }
                }
            }

            if (!errorParseo) {
                Punto origen = null;
                try {
                    origen = new Punto(fila, colChar);
                    jugadaParseada = new ParsedJugada(origen, dir, largo);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en la jugada: " + e.getMessage());
                    // jugadaParseada remains null
                }
            }
        }
        return jugadaParseada;
    }

    // Validates move logic.
    private boolean validarLogicaJugada(ParsedJugada jugada) {
        boolean esValida = true;

        Punto origenTablero = tablero.getPunto(jugada.getOrigen().getColumna(), jugada.getOrigen().getFila());
        if (origenTablero == null) {
            System.out.println("Punto de origen inválido o fuera del tablero.");
            esValida = false;
        }

        if (esValida) {
            jugada.setOrigen(origenTablero); // Update jugada with the actual point from board

            if (configuracion.isLargoBandasVariable()) {
                if (jugada.getLargo() < this.configuracion.getMinLargoBanda() || jugada.getLargo() > this.configuracion.getMaxLargoBanda()) {
                    System.out.println("Largo de banda (" + jugada.getLargo() + ") inválido. Debe ser entre " +
                                       this.configuracion.getMinLargoBanda() + " y " + this.configuracion.getMaxLargoBanda() + ".");
                    esValida = false;
                }
            } else { // Largo fijo
                if (jugada.getLargo() != configuracion.getLargoFijo()) {
                    System.out.println("Largo de banda debe ser fijo de " + configuracion.getLargoFijo() +
                                       ", se intentó " + jugada.getLargo());
                    esValida = false;
                }
            }
        }

        if (esValida && configuracion.isRequiereContacto() && movimientosRealizados > 0) {
            boolean contactoEstablecido = false;
            // Check if the origin point itself has contact
            if (origenTablero != null && !tablero.getBandasQueUsanPunto(origenTablero).isEmpty()) {
                contactoEstablecido = true;
            }

            // If origin point doesn't have contact, check all subsequent points of the proposed band
            if (!contactoEstablecido) {
                Punto puntoActualEnVerificacion = origenTablero;
                for (int i = 0; i < jugada.getLargo(); i++) {
                    Punto puntoSiguienteEnVerificacion = calcularPuntoSiguiente(puntoActualEnVerificacion, jugada.getDireccion());

                    // Ensure the next point is valid and on the board
                    if (puntoSiguienteEnVerificacion == null || tablero.getPunto(puntoSiguienteEnVerificacion.getColumna(), puntoSiguienteEnVerificacion.getFila()) == null) {
                        // Band would go off-board here, so it cannot make further contact along this path.
                        // The move will likely be invalidated by the later path check anyway.
                        break;
                    }

                    if (!tablero.getBandasQueUsanPunto(puntoSiguienteEnVerificacion).isEmpty()) {
                        contactoEstablecido = true;
                        break; // Contact found
                    }
                    puntoActualEnVerificacion = puntoSiguienteEnVerificacion;
                }
            }

            if (!contactoEstablecido) {
                String jugadaPropuestaStr = jugada.getOrigen().coordenadaCompleta() +
                                          jugada.getDireccion().getCodigo() +
                                          Integer.toString(jugada.getLargo());
                System.out.println("Jugada inválida: Se requiere contacto con una banda existente y ningún punto de la nueva banda propuesta (" +
                                   jugadaPropuestaStr + ") lo tiene.");
                esValida = false;
            }
        }

        if (esValida) {
            Punto current = origenTablero;
            for (int i = 0; i < jugada.getLargo(); i++) {
                Punto next = calcularPuntoSiguiente(current, jugada.getDireccion());
                if (next == null || tablero.getPunto(next.getColumna(), next.getFila()) == null) {
                    System.out.println("La banda se sale del tablero o pasa por un punto inválido en el segmento " + (i + 1) + ".");
                    esValida = false;
                    break;
                }
                Punto currentFromBoard = tablero.getPunto(current.getColumna(), current.getFila());
                Punto nextFromBoard = tablero.getPunto(next.getColumna(), next.getFila());

                if (currentFromBoard == null || nextFromBoard == null || !Tablero.sonPuntosAdyacentes(currentFromBoard, nextFromBoard)) {
                    System.out.println("Segmento " + (i + 1) + " (" + current + " a " + next + ") no conecta puntos adyacentes.");
                    esValida = false;
                    break;
                }
                current = next;
            }
        }
        return esValida;
    }

    // Calculates next point from direction.
    private Punto calcularPuntoSiguiente(Punto actual, Direccion dir) {
        Punto puntoSiguiente = null;
        if (actual != null && dir != null) {
            int filaActual = actual.getFila();
            char colActual = actual.getColumna();
            int nuevaFila = filaActual;
            char nuevaCol = colActual;
            boolean direccionValida = true;

            switch (dir.getCodigo()) {
                case 'Q':
                    nuevaFila--;
                    nuevaCol--;
                    break;
                case 'E':
                    nuevaFila--;
                    nuevaCol++;
                    break;
                case 'D':
                    nuevaCol += 2;
                    break;
                case 'C':
                    nuevaFila++;
                    nuevaCol++;
                    break;
                case 'Z':
                    nuevaFila++;
                    nuevaCol--;
                    break;
                case 'A':
                    nuevaCol -= 2;
                    break;
                default:
                    direccionValida = false;
                    break;
            }

            if (direccionValida) {
                try {
                    puntoSiguiente = new Punto(nuevaFila, nuevaCol);
                } catch (IllegalArgumentException e) {
                    // puntoSiguiente remains null
                }
            }
        }
        return puntoSiguiente;
    }

    // Switches to the next player.
    private void cambiarTurno() {
        if (turnoActual.equals(jugadorBlanco)) {
            turnoActual = jugadorNegro;
        } else {
            turnoActual = jugadorBlanco;
        }
        System.out.println("Turno del jugador: " + turnoActual.getNombre());
    }

    // Checks game end conditions.
    private boolean verificarFinPartida() {
        boolean fin = false;
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            partidaTerminada = true;
            fin = true;
        }
        if (!fin) { // Check only if not already ended by band limit
            int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
            if (totalTriangulosFormados >= this.maxTriangulosPosibles) {
                partidaTerminada = true;
                fin = true;
            }
        }
        return fin;
    }

    // Gets reason for natural end.
    private String getNaturalEndReason() {
        String reason = null;
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            reason = "Partida terminada: Se alcanzó el límite de bandas colocadas.";
        } else { // Check only if not ended by band limit
            int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
            if (totalTriangulosFormados >= this.maxTriangulosPosibles) {
                reason = "Partida terminada: Se formaron todos los triángulos posibles en el tablero.";
            }
        }
        return reason;
    }

    // Determines and announces game winner.
    private void determinarGanadorFinal() {
        if (!partidaTerminada) {
             // Early exit for void method is fine
            return;
        }

        Jugador determinedWinner = null;
        Jugador determinedLoser = null;

        if (jugadorAbandono != null) {
            determinedWinner = (jugadorAbandono.equals(jugadorBlanco)) ? jugadorNegro : jugadorBlanco;
            determinedLoser = jugadorAbandono;
        } else {
            if (triangulosJugadorBlanco > triangulosJugadorNegro) {
                determinedWinner = jugadorBlanco;
                determinedLoser = jugadorNegro;
            } else if (triangulosJugadorNegro > triangulosJugadorBlanco) {
                determinedWinner = jugadorNegro;
                determinedLoser = jugadorBlanco;
            }
        }

        this.ganador = determinedWinner;

        if (this.ganador != null) {
            mostrarAnimacionFuegosArtificiales();
        }

        System.out.println("\n" + this.tablero.toString() + "\n");

        if (jugadorAbandono == null) {
            String reason = getNaturalEndReason();
            if (reason != null) {
                System.out.println(reason);
            }
        }

        if (this.ganador != null) {
            System.out.println("Partida finalizada. Ganador: " + this.ganador.getNombre());
            this.ganador.incrementarPartidasGanadas();
            this.ganador.incrementarRachaActual();
            this.ganador.actualizarRachaMaxima();
            if (determinedLoser != null) {
                determinedLoser.resetRachaActual();
            }
            System.out.println("¡Felicidades " + this.ganador.getNombre() + "!");
        } else if (jugadorAbandono == null) {
            System.out.println("Partida finalizada. Es un empate!");
            if (jugadorBlanco != null) jugadorBlanco.resetRachaActual();
            if (jugadorNegro != null) jugadorNegro.resetRachaActual();
            System.out.println("¡Ha sido un empate!");
        }

        System.out.println("--- Puntuación Final ---");
        System.out.println(jugadorBlanco.getNombre() + " (Blanco): " + triangulosJugadorBlanco + " triángulos.");
        System.out.println(jugadorNegro.getNombre() + " (Negro): " + triangulosJugadorNegro + " triángulos.");

        System.out.println("--- Fin de la Partida ---");
    }

    // Displays fireworks animation for winner.
    private void mostrarAnimacionFuegosArtificiales() {
        FireworksAnimation animator = new FireworksAnimation(70, 20);
        animator.play(5, 6000);
    }

    // Handles player forfeiting the game.
    private void abandonarPartida(Jugador jugadorQueAbandona) {
        this.partidaTerminada = true;
        this.jugadorAbandono = jugadorQueAbandona;
        System.out.println("El jugador " + jugadorQueAbandona.getNombre() + " ha abandonado la partida.");
        determinarGanadorFinal();
    }

    // Displays history of moves.
    private void mostrarHistorial() {
        System.out.println("--- Historial de Jugadas ---");
        if (historialJugadas.isEmpty()) {
            System.out.println("No se han realizado jugadas aún.");
        } else {
            for (int i = 0; i < historialJugadas.size(); i++) {
                System.out.print(historialJugadas.get(i).toUpperCase());
                if (i < historialJugadas.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println("");
        System.out.println("----------------------------");
    }

    // Represents a parsed player move.
    private static class ParsedJugada {
        private Punto origen;
        private Direccion direccion;
        private int largo;

        // Creates parsed move data object.
        public ParsedJugada(Punto origen, Direccion direccion, int largo) {
            this.origen = origen;
            this.direccion = direccion;
            this.largo = largo;
        }

        // Gets move origin point.
        public Punto getOrigen() {
            return origen;
        }

        // Sets move origin point.
        public void setOrigen(Punto origen) {
            this.origen = origen;
        }

        // Gets move direction.
        public Direccion getDireccion() {
            return direccion;
        }

        // Gets move band length.
        public int getLargo() {
            return largo;
        }
    }
}