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
    private static final int MAX_TRIANGULOS_POSIBLES = 54; // Nuevo constante
    private List<Tablero> historialDeTablerosSnapshots; // NUEVO: para guardar los estados del tablero
    private int maxHistorialSnapshots; // NUEVO: cuántos tableros guardar según configuración

    // Crea una nueva partida.
    public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, ConfiguracionPartida configuracion) {
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
        this.configuracion = configuracion;
        this.tablero = new Tablero(); // Este es el tablero actual/vivo
        this.turnoActual = jugadorBlanco; 
        this.bandasColocadasEnPartida = 0;
        this.triangulosJugadorBlanco = 0;
        this.triangulosJugadorNegro = 0;
        this.historialJugadas = new ArrayList<>();
        this.partidaTerminada = false;
        this.ganador = null;
        this.jugadorAbandono = null;
        this.movimientosRealizados = 0;

        this.maxHistorialSnapshots = configuracion.getCantidadTablerosMostrar(); // Guardar la configuración
        this.historialDeTablerosSnapshots = new ArrayList<>();
        
        // Guardar el estado inicial del tablero si se mostrará más de uno (o siempre si es >=1)
        if (this.maxHistorialSnapshots > 0) {
            this.historialDeTablerosSnapshots.add(new Tablero(this.tablero));
        }
    }

    // Obtiene historial de tableros.
    public List<Tablero> getHistorialDeTablerosSnapshots() {
        return new ArrayList<>(this.historialDeTablerosSnapshots); // Devuelve una copia para evitar modificaciones externas
    }

    // obtiene el turno actual.
    public Jugador getTurnoActual() {
        return turnoActual;
    }

    // obtiene el tablero.
    public Tablero getTablero() {
        return tablero;
    }

    // obtiene triángulos jugador blanco.
    public int getTriangulosJugadorBlanco() {
        return triangulosJugadorBlanco;
    }

    // obtiene triángulos jugador negro.
    public int getTriangulosJugadorNegro() {
        return triangulosJugadorNegro;
    }

    // obtiene historial de jugadas.
    public List<String> getHistorialJugadas() {
        return new ArrayList<>(historialJugadas); 
    }
    
    // verifica si partida terminó.
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    // obtiene el ganador.
    public Jugador getGanador() {
        return ganador;
    }

    // obtiene jugador que abandonó.
    public Jugador getJugadorAbandono() {
        return jugadorAbandono;
    }

    // Getter para bandasColocadasEnPartida
    public int getBandasColocadasEnPartida() {
        return bandasColocadasEnPartida;
    }

    // Getter para movimientosRealizados
    public int getMovimientosRealizados() {
        return movimientosRealizados;
    }

    // Procesa la jugada ingresada.
    public boolean procesarJugada(String inputJugada) {
        if (partidaTerminada) {
            System.out.println("La partida ya ha terminado.");
            return false;
        }

        String inputUpper = inputJugada.toUpperCase();

        if ("X".equals(inputUpper)) {
            abandonarPartida(turnoActual);
            return true;
        }

        if ("H".equals(inputUpper)) {
            mostrarHistorial();
            return false;
        }

        ParsedJugada jugada = parsearJugadaInput(inputUpper);
        if (jugada == null) {
            System.out.println("Formato de jugada incorrecto.");
            return true;
        }

        if (!validarLogicaJugada(jugada)) {
            return true;
        }

        Punto puntoActualLoop = jugada.getOrigen();

        for (int i = 0; i < jugada.getLargo(); i++) {
            Punto puntoSiguiente = calcularPuntoSiguiente(puntoActualLoop, jugada.getDireccion());

            if (puntoSiguiente == null || tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila()) == null) {
                System.out.println("Error: Movimiento fuera de tablero.");
                return true;
            }

            Punto pA = tablero.getPunto(puntoActualLoop.getColumna(), puntoActualLoop.getFila());
            Punto pB = tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila());

            if (pA == null || pB == null || !Tablero.sonPuntosAdyacentes(pA, pB)) {
                System.out.println("Error: Segmento inválido.");
                return true;
            }

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
        return true;
    }

    // Detecta nuevos triángulos formados.
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

    // parsea entrada de jugada.
    private ParsedJugada parsearJugadaInput(String input) {
        if (input == null || input.length() < 2) return null;

        char colChar = input.charAt(0);
        int fila;
        Direccion dir;
        int largo = configuracion.isLargoBandasVariable() ? 0 : configuracion.getLargoFijo(); 

        int dirIndex;
        if (Character.isDigit(input.charAt(1))) { 
            if (input.length() < 3) return null; 
            try {
                fila = Integer.parseInt(input.substring(1, 2));
                dirIndex = 2;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null; 
        }
        
        if (input.length() <= dirIndex) return null; 
        char dirCharInput = input.charAt(dirIndex);
        char upperDirChar = Character.toUpperCase(dirCharInput);

        // Validate the direction character
        if (!(upperDirChar == 'Q' || upperDirChar == 'E' || upperDirChar == 'D' || upperDirChar == 'C' || upperDirChar == 'Z' || upperDirChar == 'A')) {
            System.out.println("Dirección inválida: " + dirCharInput);
            return null;
        }
        dir = new Direccion(dirCharInput); // Constructor of Direccion class handles toUpperCase

        if (input.length() > dirIndex + 1) { 
            try {
                largo = Integer.parseInt(input.substring(dirIndex + 1));
            } catch (NumberFormatException e) {
                System.out.println("Largo de banda inválido.");
                return null;
            }
        } else {
            if (configuracion.isLargoBandasVariable()) {
                 largo = 4; 
            }
        }

        Punto origen = null; // MODIFICADO: Inicializar a null
        try {
            origen = new Punto(fila, colChar); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la jugada: " + e.getMessage()); // MODIFICADO: Mostrar mensaje de error
            return null; // MODIFICADO: Retornar null si el punto es inválido
        }
        
        return new ParsedJugada(origen, dir, largo);
    }

    // valida lógica de jugada.
    private boolean validarLogicaJugada(ParsedJugada jugada) {
        Punto origenTablero = tablero.getPunto(jugada.getOrigen().getColumna(), jugada.getOrigen().getFila());
        if (origenTablero == null) {
            System.out.println("Punto de origen inválido o fuera del tablero.");
            return false;
        }
        jugada.setOrigen(origenTablero); 

        
        if (configuracion.isLargoBandasVariable()) {
            if (jugada.getLargo() < this.configuracion.getMinLargoBanda() || jugada.getLargo() > this.configuracion.getMaxLargoBanda()) {
                System.out.println("Largo de banda ("+jugada.getLargo()+") inválido. Debe ser entre " + this.configuracion.getMinLargoBanda() + " y " + this.configuracion.getMaxLargoBanda() + ".");
                return false;
            }
        } else { 
            if (jugada.getLargo() != configuracion.getLargoFijo()) {
                 System.out.println("Largo de banda debe ser fijo de " + configuracion.getLargoFijo() + ", se intentó " + jugada.getLargo());
                 return false;
            }
        }

        
        if (configuracion.isRequiereContacto() && movimientosRealizados > 0) {
            boolean contactoEncontrado = false;
            
            if (!tablero.getBandasQueUsanPunto(origenTablero).isEmpty()) {
                contactoEncontrado = true;
            }
            
            
            
            if (!contactoEncontrado) {
                 System.out.println("Jugada inválida: Se requiere contacto con una banda existente y el punto de origen no lo tiene.");
                 return false;
            }
        }
        
        
        Punto current = origenTablero;
        for (int i = 0; i < jugada.getLargo(); i++) {
            Punto next = calcularPuntoSiguiente(current, jugada.getDireccion());
            if (next == null || tablero.getPunto(next.getColumna(), next.getFila()) == null) {
                System.out.println("La banda se sale del tablero o pasa por un punto inválido en el segmento " + (i + 1) + ".");
                return false;
            }
            if (!Tablero.sonPuntosAdyacentes(tablero.getPunto(current.getColumna(), current.getFila()), tablero.getPunto(next.getColumna(), next.getFila()))) {
                 System.out.println("Segmento " + (i+1) + " ("+current+" a "+next+") no conecta puntos adyacentes.");
                 return false;
            }
            current = next;
        }

        return true;
    }

    
    // calcula el punto siguiente.
    private Punto calcularPuntoSiguiente(Punto actual, Direccion dir) {
        if (actual == null || dir == null) return null;
        int filaActual = actual.getFila();
        char colActual = actual.getColumna();
        int nuevaFila = filaActual;
        char nuevaCol = colActual;

        switch (dir.getCodigo()) { // Use getCodigo() and char literals
            case 'Q': // NOROESTE
                nuevaFila--;
                nuevaCol--;
                break;
            case 'E':  // NORESTE
                nuevaFila--;
                nuevaCol++;
                break;
            case 'D':     // ESTE
                nuevaCol += 2;
                break;
            case 'C':  // SURESTE
                nuevaFila++;
                nuevaCol++; 
                break;
            case 'Z': // SUROESTE
                nuevaFila++;
                nuevaCol--;
                break;
            case 'A':    // OESTE
                nuevaCol -= 2;
                break;
            default:
                // This case should ideally not be reached if parsearJugadaInput validates the char
                return null; 
        }
        
        
        try {
            return new Punto(nuevaFila, nuevaCol);
        } catch (IllegalArgumentException e) {
            
            return null; 
        }
    }

    // cambia el turno.
    private void cambiarTurno() {
        if (turnoActual.equals(jugadorBlanco)) {
            turnoActual = jugadorNegro;
        } else {
            turnoActual = jugadorBlanco;
        }
        System.out.println("Turno del jugador: " + turnoActual.getNombre());
    }

    // verifica fin de partida.
    // MODIFIED: This method now only checks conditions and sets the flag. It does not print.
    private boolean verificarFinPartida() {
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            partidaTerminada = true;
            return true;
        }
        int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
        if (totalTriangulosFormados >= MAX_TRIANGULOS_POSIBLES) {
            partidaTerminada = true;
            return true;
        }
        return false;
    }

    // NEW HELPER METHOD: Gets the reason text for a natural game end.
    private String getNaturalEndReason() {
        if (this.bandasColocadasEnPartida >= configuracion.getCantidadBandasFin()) {
            return "Partida terminada: Se alcanzó el límite de bandas colocadas.";
        }
        int totalTriangulosFormados = this.triangulosJugadorBlanco + this.triangulosJugadorNegro;
        if (totalTriangulosFormados >= MAX_TRIANGULOS_POSIBLES) {
            return "Partida terminada: Se formaron todos los triángulos posibles en el tablero.";
        }
        return null; // Should not be called if game didn't end naturally
    }

    // determina ganador final.
    // MODIFIED: Shows fireworks (if winner), then final board, then reason/summary.
    private void determinarGanadorFinal() {
        if (!partidaTerminada) return; 

        // Determine winner/loser first, as this decides if fireworks are shown
        Jugador determinedWinner = null;
        Jugador determinedLoser = null;

        if (jugadorAbandono != null) { 
            determinedWinner = (jugadorAbandono.equals(jugadorBlanco)) ? jugadorNegro : jugadorBlanco;
            determinedLoser = jugadorAbandono;
            // The message "El jugador X ha abandonado la partida." is printed by abandonarPartida()
        } else { 
            // Natural game end (not abandoned)
            if (triangulosJugadorBlanco > triangulosJugadorNegro) {
                determinedWinner = jugadorBlanco;
                determinedLoser = jugadorNegro;
            } else if (triangulosJugadorNegro > triangulosJugadorBlanco) {
                determinedWinner = jugadorNegro;
                determinedLoser = jugadorBlanco;
            } else {
                // Empate, determinedWinner remains null
            }
        }
        
        this.ganador = determinedWinner; // Set the class field 'ganador'

        // 1. Show fireworks IF there is a winner (BEFORE board and other messages)
        if (this.ganador != null) {
            mostrarAnimacionFuegosArtificiales(); 
        }

        // 2. Print the final board state
        System.out.println("\n" + this.tablero.toString() + "\n");

        // 3. Print the reason for natural game end (if not abandoned)
        if (jugadorAbandono == null) {
            String reason = getNaturalEndReason();
            if (reason != null) {
                System.out.println(reason);
            }
        }

        // 4. Announce winner/draw and update player stats
        if (this.ganador != null) {
            System.out.println("Partida finalizada. Ganador: " + this.ganador.getNombre());
            this.ganador.incrementarPartidasGanadas();
            this.ganador.incrementarRachaActual();
            this.ganador.actualizarRachaMaxima();
            if (determinedLoser != null) {
                determinedLoser.resetRachaActual();
            }
            System.out.println("¡Felicidades " + this.ganador.getNombre() + "!");
        } else if (jugadorAbandono == null) { // Empate natural (not abandoned)
            System.out.println("Partida finalizada. Es un empate!");
            if (jugadorBlanco != null) jugadorBlanco.resetRachaActual();
            if (jugadorNegro != null) jugadorNegro.resetRachaActual();
            System.out.println("¡Ha sido un empate!"); 
        }
        // Note: If it was an abandonment, "El jugador X ha abandonado..." was already printed.
        // The "Partida finalizada. Ganador: ..." will still print correctly for the winner by abandonment.

        // 5. Print final scores
        System.out.println("--- Puntuación Final ---");
        System.out.println(jugadorBlanco.getNombre() + " (Blanco): " + triangulosJugadorBlanco + " triángulos.");
        System.out.println(jugadorNegro.getNombre() + " (Negro): " + triangulosJugadorNegro + " triángulos.");
        
        // 6. Print end of game marker
        System.out.println("--- Fin de la Partida ---");
    }

    // Method to display fireworks animation
    private void mostrarAnimacionFuegosArtificiales() {
        // Ensure FireworksAnimation.java is in your project, in the 'model' package
        // Adjust width/height based on your console view in NetBeans
        // Adjust numberOfFireworks and totalDurationMillis as desired
        FireworksAnimation animator = new FireworksAnimation(70, 20); // (width, height)
        animator.play(5, 6000); // (numberOfFireworks, totalDurationMillis)
    }

    // abandona la partida.
    private void abandonarPartida(Jugador jugadorQueAbandona) {
        this.partidaTerminada = true;
        this.jugadorAbandono = jugadorQueAbandona;
        System.out.println("El jugador " + jugadorQueAbandona.getNombre() + " ha abandonado la partida.");
        determinarGanadorFinal(); // This will now print board and full summary
    }

    // muestra historial de jugadas.
    private void mostrarHistorial() {
        System.out.println("--- Historial de Jugadas ---");
        if (historialJugadas.isEmpty()) {
            System.out.println("No se han realizado jugadas aún.");
        } else {
            for (int i = 0; i < historialJugadas.size(); i++) {
                System.out.print(historialJugadas.get(i).toUpperCase());
                if(i < historialJugadas.size() - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println("");
        System.out.println("----------------------------");
    }

    // clase para jugada parseada.
    private static class ParsedJugada {
        private Punto origen; 
        private Direccion direccion;
        private int largo;

        public ParsedJugada(Punto origen, Direccion direccion, int largo) {
            this.origen = origen;
            this.direccion = direccion;
            this.largo = largo;
        }

        public Punto getOrigen() { return origen; }
        public void setOrigen(Punto origen) { this.origen = origen; } 
        public Direccion getDireccion() { return direccion; }
        public int getLargo() { return largo; }
    }
}