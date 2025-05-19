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

    // Crea nueva instancia de partida.
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

    // Obtiene historial de tableros 
    public List<Tablero> getHistorialDeTablerosSnapshots() {
        return new ArrayList<>(this.historialDeTablerosSnapshots);
    }

    // Obtiene jugador del turno actual.
    public Jugador getTurnoActual() {
        return turnoActual;
    }

    // Obtiene el tablero de juego actual.
    public Tablero getTablero() {
        return tablero;
    }

    // Obtiene triángulos del jugador blanco.
    public int getTriangulosJugadorBlanco() {
        return triangulosJugadorBlanco;
    }

    // Obtiene triángulos del jugador negro.
    public int getTriangulosJugadorNegro() {
        return triangulosJugadorNegro;
    }

    // Obtiene la lista de jugadas realizadas.
    public List<String> getHistorialJugadas() {
        return new ArrayList<>(historialJugadas);
    }

    // Verifica si la partida ha terminado.
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    // Obtiene el ganador de la partida.
    public Jugador getGanador() {
        return ganador;
    }

    // Obtiene jugador que abandonó partida.
    public Jugador getJugadorAbandono() {
        return jugadorAbandono;
    }

    // Obtiene bandas colocadas en partida.
    public int getBandasColocadasEnPartida() {
        return bandasColocadasEnPartida;
    }

    // Obtiene total de movimientos realizados.
    public int getMovimientosRealizados() {
        return movimientosRealizados;
    }

    // Procesa la jugada ingresada por jugador.
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
                continuarProcesando = false;
            } else if ("H".equals(inputUpper)) {
                mostrarHistorial();
                jugadaValidaYProcesada = false; 
                continuarProcesando = false;
            }
        }

        if (continuarProcesando) {
            DetalleJugada jugada = parsearJugadaInput(inputJugada.toUpperCase());
            if (jugada == null) {
                System.out.println("Formato de jugada incorrecto.");
                continuarProcesando = false;
            } else {
                if (!validarLogicaJugada(jugada)) {
                    continuarProcesando = false;
                } else {
                    Punto puntoActualLoop = jugada.getOrigen();
                    boolean errorEnLoop = false;
                    int segmentosProcesados = 0;

                    while (segmentosProcesados < jugada.getLargo() && !errorEnLoop) {
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
                        segmentosProcesados++;
                    }

                    if (!errorEnLoop) {
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
                    }
                }
            }
        }
        return jugadaValidaYProcesada;
    }

    // Detecta nuevos triángulos con banda.
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
                    int k = 0;
                    List<Triangulo> triangulosExistentes = tablero.getTriangulosGanados();
                    while(k < triangulosExistentes.size() && !yaExiste) {
                        if (triangulosExistentes.get(k).equals(nuevoTriangulo)) {
                            yaExiste = true;
                        }
                        k++;
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

    // Analiza entrada de jugada del jugador.
    private DetalleJugada parsearJugadaInput(String input) {
        DetalleJugada jugadaParseada = null;
        boolean errorParseo = false;

        if (input == null || input.length() < 2) {
            errorParseo = true;
        }

        if (!errorParseo) {
            char colChar = input.charAt(0);
            int fila = 0; 
            Direccion dir = null; 
            int largo = configuracion.isLargoBandasVariable() ? 0 : configuracion.getLargoFijo();
            int dirIndex = 0; 

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
                        largo = 4; 
                    }
                }
            }

            if (!errorParseo) {
                Punto origen = null;
                try {
                    origen = new Punto(fila, colChar);
                    jugadaParseada = new DetalleJugada(origen, dir, largo);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error en la jugada: " + e.getMessage());
                }
            }
        }
        return jugadaParseada;
    }

    // Valida la lógica de la jugada.
    private boolean validarLogicaJugada(DetalleJugada jugada) {
        boolean esValida = true;

        Punto origenTablero = tablero.getPunto(jugada.getOrigen().getColumna(), jugada.getOrigen().getFila());
        if (origenTablero == null) {
            System.out.println("Punto de origen inválido o fuera del tablero.");
            esValida = false;
        }

        if (esValida) {
            jugada.setOrigen(origenTablero); 

            if (configuracion.isLargoBandasVariable()) {
                if (jugada.getLargo() < this.configuracion.getMinLargoBanda() || jugada.getLargo() > this.configuracion.getMaxLargoBanda()) {
                    System.out.println("Largo de banda (" + jugada.getLargo() + ") inválido. Debe ser entre " +
                                       this.configuracion.getMinLargoBanda() + " y " + this.configuracion.getMaxLargoBanda() + ".");
                    esValida = false;
                }
            } else { 
                if (jugada.getLargo() != configuracion.getLargoFijo()) {
                    System.out.println("Largo de banda debe ser fijo de " + configuracion.getLargoFijo() +
                                       ", se intentó " + jugada.getLargo());
                    esValida = false;
                }
            }
        }

        if (esValida && configuracion.isRequiereContacto() && movimientosRealizados > 0) {
            boolean contactoEstablecido = false;
            if (origenTablero != null && !tablero.getBandasQueUsanPunto(origenTablero).isEmpty()) {
                contactoEstablecido = true;
            }

            if (!contactoEstablecido) {
                Punto puntoActualEnVerificacion = origenTablero;
                boolean continuarBusquedaContacto = true;
                for (int i = 0; i < jugada.getLargo() && continuarBusquedaContacto; i++) {
                    Punto puntoSiguienteEnVerificacion = calcularPuntoSiguiente(puntoActualEnVerificacion, jugada.getDireccion());

                    if (puntoSiguienteEnVerificacion == null || tablero.getPunto(puntoSiguienteEnVerificacion.getColumna(), puntoSiguienteEnVerificacion.getFila()) == null) {
                        continuarBusquedaContacto = false; 
                    } else {
                        if (!tablero.getBandasQueUsanPunto(puntoSiguienteEnVerificacion).isEmpty()) {
                            contactoEstablecido = true;
                            continuarBusquedaContacto = false; 
                        } else {
                           puntoActualEnVerificacion = puntoSiguienteEnVerificacion;
                        }
                    }
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
            for (int i = 0; i < jugada.getLargo() && esValida; i++) {
                Punto next = calcularPuntoSiguiente(current, jugada.getDireccion());
                if (next == null || tablero.getPunto(next.getColumna(), next.getFila()) == null) {
                    System.out.println("La banda se sale del tablero o pasa por un punto inválido en el segmento " + (i + 1) + ".");
                    esValida = false;
                } else {
                    Punto currentFromBoard = tablero.getPunto(current.getColumna(), current.getFila());
                    Punto nextFromBoard = tablero.getPunto(next.getColumna(), next.getFila());

                    if (currentFromBoard == null || nextFromBoard == null || !Tablero.sonPuntosAdyacentes(currentFromBoard, nextFromBoard)) {
                        System.out.println("Segmento " + (i + 1) + " (" + current.coordenadaCompleta() + " a " + next.coordenadaCompleta() + ") no conecta puntos adyacentes.");
                        esValida = false;
                    } else {
                        current = next;
                    }
                }
            }
        }
        return esValida;
    }

    // Calcula el siguiente punto dada dirección.
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

    // Cambia el turno al siguiente jugador.
    private void cambiarTurno() {
        if (turnoActual.equals(jugadorBlanco)) {
            turnoActual = jugadorNegro;
        } else {
            turnoActual = jugadorBlanco;
        }
        System.out.println("Turno del jugador: " + turnoActual.getNombre());
    }

    // Verifica condiciones de fin de partida.
    private boolean verificarFinPartida() {
        boolean fin = false;
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            partidaTerminada = true;
            fin = true;
        }
        if (!fin) { 
            int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
            if (totalTriangulosFormados >= this.maxTriangulosPosibles) {
                partidaTerminada = true;
                fin = true;
            }
        }
        return fin;
    }

    // Obtiene razón de fin natural partida.
    private String getNaturalEndReason() {
        String reason = null;
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            reason = "Partida terminada: Se alcanzó el límite de bandas colocadas.";
        } else { 
            int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
            if (totalTriangulosFormados >= this.maxTriangulosPosibles) {
                reason = "Partida terminada: Se formaron todos los triángulos posibles en el tablero.";
            }
        }
        return reason;
    }

    // Determina y anuncia el ganador final.
    private void determinarGanadorFinal() {
        boolean procederConDeterminacion = partidaTerminada;

        if (procederConDeterminacion) {
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
    }

    // Muestra animación de fuegos artificiales.
    private void mostrarAnimacionFuegosArtificiales() {
        FireworksAnimation animator = new FireworksAnimation(70, 20);
        animator.play(5, 6000);
    }

    // Maneja abandono de partida por jugador.
    private void abandonarPartida(Jugador jugadorQueAbandona) {
        this.partidaTerminada = true;
        this.jugadorAbandono = jugadorQueAbandona;
        System.out.println("El jugador " + jugadorQueAbandona.getNombre() + " ha abandonado la partida.");
        determinarGanadorFinal();
    }

    // Muestra el historial de jugadas.
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
}