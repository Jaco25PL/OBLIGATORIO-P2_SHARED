package obligatorio_shared;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Tablero tablero;
    private ConfiguracionPartida configuracion;
    private Jugador turnoActual;
    private int contadorBandasJugadas; // Renombrado y lógica de incremento cambiada
    private int triangulosJugadorBlanco;
    private int triangulosJugadorNegro;
    private List<String> historialJugadas;
    private boolean partidaTerminada;
    private Jugador ganador;
    private Jugador jugadorAbandono;
    private int movimientosRealizados; // Para la regla "requiereContacto"

    public Partida(Jugador jugadorBlanco, Jugador jugadorNegro, ConfiguracionPartida configuracion) {
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
        this.configuracion = configuracion;
        this.tablero = new Tablero(); // Cada partida tiene su propio tablero
        this.turnoActual = jugadorBlanco; // Por defecto, el jugador Blanco comienza
        this.contadorBandasJugadas = 0; // Inicializar el contador de bandas jugadas
        this.triangulosJugadorBlanco = 0;
        this.triangulosJugadorNegro = 0;
        this.historialJugadas = new ArrayList<>();
        this.partidaTerminada = false;
        this.ganador = null;
        this.jugadorAbandono = null;
        this.movimientosRealizados = 0;
    }

    // --- Getters (y Setters si son necesarios y seguros) ---
    public Jugador getTurnoActual() {
        return turnoActual;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getTriangulosJugadorBlanco() {
        return triangulosJugadorBlanco;
    }

    public int getTriangulosJugadorNegro() {
        return triangulosJugadorNegro;
    }

    public int getContadorBandasJugadas() {
        return contadorBandasJugadas;
    }

    public List<String> getHistorialJugadas() {
        return new ArrayList<>(historialJugadas); // Devuelve copia para inmutabilidad externa
    }
    
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public Jugador getJugadorAbandono() {
        return jugadorAbandono;
    }


    /**
     * Procesa la jugada ingresada por el usuario.
     * Maneja comandos especiales ('X', 'H') y la lógica de colocación de bandas.
     * @param inputJugada El string de la jugada (ej. "D1C3", "X", "H").
     * @return true si la jugada fue procesada (aunque sea inválida y se pida reingreso), 
     *         false si es un comando que no avanza el turno pero es válido (ej. 'H').
     *         Lanza excepciones o devuelve códigos de error para jugadas inválidas.
     *         (El retorno exacto puede ajustarse según el diseño del bucle principal del juego)
     */
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
            return false; // No avanza el turno, solo muestra info
        }

        // 1. Parsear la jugada
        ParsedJugada jugada = parsearJugadaInput(inputUpper);
        if (jugada == null) {
            System.out.println("Formato de jugada incorrecto. Reingrese.");
            return true; // Indica que se debe pedir reingreso
        }

        // 2. Validar la jugada (lógica de negocio)
        if (!validarLogicaJugada(jugada)) {
            // El método validarLogicaJugada ya debería imprimir el mensaje de error específico.
            return true; // Indica que se debe pedir reingreso
        }
        
        // 3. Ejecutar la jugada: colocar las bandas segmento por segmento
        Punto puntoActual = jugada.getOrigen();
        List<Banda> segmentosColocadosEstaJugada = new ArrayList<>();

        for (int i = 0; i < jugada.getLargo(); i++) {
            Punto puntoSiguiente = calcularPuntoSiguiente(puntoActual, jugada.getDireccion());

            if (puntoSiguiente == null || tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila()) == null) {
                // Esto no debería ocurrir si validarLogicaJugada fue exhaustiva, pero es una salvaguarda.
                System.out.println("Error: Movimiento fuera del tablero o a punto inválido en el segmento " + (i+1));
                // Aquí se podría necesitar revertir segmentos ya colocados en esta jugada si es transaccional.
                // Por simplicidad, asumimos que validarLogicaJugada previene esto.
                return true; 
            }
            
            // Asegurarse de que los puntos para la banda sean los objetos del tablero
            Punto pA = tablero.getPunto(puntoActual.getColumna(), puntoActual.getFila());
            Punto pB = tablero.getPunto(puntoSiguiente.getColumna(), puntoSiguiente.getFila());

            if (pA == null || pB == null || !Tablero.sonPuntosAdyacentes(pA, pB)) {
                System.out.println("Error: Segmento inválido " + pA + " a " + pB + ". No son adyacentes o no existen.");
                return true; // This exits the method, thus the loop.
            }

            Banda nuevoSegmento = new Banda(pA, pB, turnoActual);
            
            // Verificar si esta banda (segmento) ya existe. La consigna dice "es válido que una banda se ubique sobre otra".
            // Si se quisiera evitar superposición exacta del mismo jugador, se chequearía aquí.
            // if (tablero.getBandas().contains(nuevoSegmento)) { /* ...manejar superposición... */ }

            tablero.addBanda(nuevoSegmento);
            segmentosColocadosEstaJugada.add(nuevoSegmento);

            // 4. Detectar y asignar triángulos para CADA segmento
            // Se asume que Tablero tiene un método para esto.
            int nuevosTriangulos = simularDeteccionTriangulos(nuevoSegmento); // Placeholder
            if (turnoActual.equals(jugadorBlanco)) {
                triangulosJugadorBlanco += nuevosTriangulos;
            } else {
                triangulosJugadorNegro += nuevosTriangulos;
            }

            puntoActual = puntoSiguiente; // Avanzar al siguiente punto para el próximo segmento
        }
        
        this.contadorBandasJugadas++; // Incrementar una vez por jugada de banda exitosa
        historialJugadas.add(inputJugada); // Guardar la jugada original
        movimientosRealizados++;

        // 5. Verificar fin de partida
        if (verificarFinPartida()) {
            determinarGanadorFinal();
            // Aquí se podría llamar a la animación, etc.
        } else {
            cambiarTurno();
        }
        return true; // Jugada procesada, turno podría haber cambiado o partida terminado
    }

    private int simularDeteccionTriangulos(Banda banda) {
        // Placeholder: Lógica real en Tablero.java
        // System.out.println("Simulando detección de triángulos para la banda: " + banda);
        return 0; // Asumir 0 por ahora
    }


    private ParsedJugada parsearJugadaInput(String input) {
        if (input == null || input.length() < 2) return null;

        char colChar = input.charAt(0);
        int fila;
        Direccion dir;
        int largo = configuracion.isLargoBandasVariable() ? 0 : configuracion.getLargoFijo(); // Default si se omite

        int dirIndex;
        if (Character.isDigit(input.charAt(1))) { // Formato LetraFila...
            if (input.length() < 3) return null; // Necesita al menos LetraFilaDir
            try {
                // Puede ser LFRD o LFRDC (R=Row, D=Direction, C=Count)
                // o LFRRD o LFRRDC (RR = Row con dos digitos)
                // La consigna dice Fila: 1 a 7, así que un dígito.
                fila = Integer.parseInt(input.substring(1, 2));
                dirIndex = 2;
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null; // Formato inválido
        }
        
        if (input.length() <= dirIndex) return null; // No hay caracter para direccion
        dir = Direccion.fromChar(input.charAt(dirIndex));
        if (dir == null) return null;

        if (input.length() > dirIndex + 1) { // Hay algo después de la dirección, podría ser el largo
            try {
                largo = Integer.parseInt(input.substring(dirIndex + 1));
            } catch (NumberFormatException e) {
                // Si no es un número, y el largo por defecto era 0 (variable sin especificar), es error.
                // O si el formato es incorrecto.
                // La consigna dice "Si se omite se asume 4".
                // Si hay algo pero no es número, es error.
                System.out.println("Largo de banda inválido.");
                return null;
            }
        } else {
            // No hay nada después de la dirección, usar largo por defecto de la consigna (4)
            // si la configuración no es de largo fijo.
            // Si es largo fijo, 'largo' ya tiene ese valor.
            // Si es variable y se omite, la consigna dice "se asume 4".
            if (configuracion.isLargoBandasVariable()) {
                 largo = 4; // Default de la consigna si se omite en modo variable
            }
            // Si no es variable, 'largo' ya tiene el valor fijo.
        }


        Punto origen = new Punto(fila, colChar); // Se crea temporalmente, se buscará en tablero luego
        return new ParsedJugada(origen, dir, largo);
    }

    private boolean validarLogicaJugada(ParsedJugada jugada) {
        Punto origenTablero = tablero.getPunto(jugada.getOrigen().getColumna(), jugada.getOrigen().getFila());
        if (origenTablero == null) {
            System.out.println("Punto de origen inválido o fuera del tablero.");
            return false;
        }
        jugada.setOrigen(origenTablero); // Usar el objeto Punto del tablero

        // Validar largo
        if (configuracion.isLargoBandasVariable()) {
            if (jugada.getLargo() < ConfiguracionPartida.MIN_LARGO_BANDA || jugada.getLargo() > ConfiguracionPartida.MAX_LARGO_BANDA) {
                System.out.println("Largo de banda ("+jugada.getLargo()+") inválido. Debe ser entre " + ConfiguracionPartida.MIN_LARGO_BANDA + " y " + ConfiguracionPartida.MAX_LARGO_BANDA + ".");
                return false;
            }
        } else { // Largo fijo
            if (jugada.getLargo() != configuracion.getLargoFijo()) {
                // Si el input especificó un largo, y es distinto al fijo, es error.
                // Si el input omitió el largo, parsearJugadaInput ya lo puso al fijo.
                // Esta validación es más para el caso donde el input SÍ proveyó un largo.
                // El parser ya debería haber asignado el largo fijo si se omitió.
                // Si el parser asignó un largo explícito del input, y no es el fijo, es un problema.
                 System.out.println("Largo de banda debe ser fijo de " + configuracion.getLargoFijo() + ", se intentó " + jugada.getLargo());
                 return false;
            }
        }

        // Validar "Requiere Contacto"
        if (configuracion.isRequiereContacto() && movimientosRealizados > 0) {
            boolean contactoEncontrado = false;
            // Verificar si el punto de origen ya tiene una banda
            if (!tablero.getBandasQueUsanPunto(origenTablero).isEmpty()) {
                contactoEncontrado = true;
            }
            // Si no, se necesitaría verificar si alguno de los puntos intermedios o finales de la
            // nueva banda propuesta tocaría una banda existente. Esta lógica puede ser compleja.
            // Por ahora, una simplificación: el punto de origen debe tener contacto.
            if (!contactoEncontrado) {
                 System.out.println("Jugada inválida: Se requiere contacto con una banda existente y el punto de origen no lo tiene.");
                 return false;
            }
        }
        
        // Validar que la banda entera quepa en el tablero y no pase por puntos inválidos
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

    /**
     * Calcula el punto siguiente dado un punto actual y una dirección.
     * No verifica si el punto resultante es válido en el tablero.
     */
    private Punto calcularPuntoSiguiente(Punto actual, Direccion dir) {
        if (actual == null || dir == null) return null;
        int filaActual = actual.getFila();
        char colActual = actual.getColumna();
        int nuevaFila = filaActual;
        char nuevaCol = colActual;

        switch (dir) {
            case NOROESTE: // Q
                nuevaFila--;
                nuevaCol--;
                break;
            case NORESTE:  // E
                nuevaFila--;
                nuevaCol++;
                break;
            case ESTE:     // D
                nuevaCol += 2;
                break;
            case SURESTE:  // C
                nuevaFila++;
                nuevaCol++; // Ensure this is '++' and not '--'
                break;
            case SUROESTE: // Z
                nuevaFila++;
                nuevaCol--;
                break;
            case OESTE:    // A
                nuevaCol -= 2;
                break;
        }
        // La validación de si (nuevaFila, nuevaCol) es un Punto existente en el tablero
        // se hará obteniéndolo del Tablero.
        try {
            return new Punto(nuevaFila, nuevaCol);
        } catch (IllegalArgumentException e) {
            // El punto calculado está fuera del diseño del tablero diamante o es inválido (ej. 'N' o fila 0)
            return null; 
        }
    }


    private void cambiarTurno() {
        if (turnoActual.equals(jugadorBlanco)) {
            turnoActual = jugadorNegro;
        } else {
            turnoActual = jugadorBlanco;
        }
    }

    private boolean verificarFinPartida() {
        if (this.contadorBandasJugadas >= configuracion.getCantidadBandasFin()) {
            partidaTerminada = true;
            return true;
        }
        return false;
    }

    private void determinarGanadorFinal() {
        if (!partidaTerminada) return; // Solo si ha terminado por bandas
        if (jugadorAbandono != null) { // Si terminó por abandono, el otro es ganador
            ganador = (jugadorAbandono.equals(jugadorBlanco)) ? jugadorNegro : jugadorBlanco;
        } else { // Termina por cantidad de bandas
            if (triangulosJugadorBlanco > triangulosJugadorNegro) {
                ganador = jugadorBlanco;
            } else if (triangulosJugadorNegro > triangulosJugadorBlanco) {
                ganador = jugadorNegro;
            } else {
                ganador = null; // Empate
            }
        }
        
        // Actualizar estadísticas de jugadores
        if (ganador != null) {
            ganador.incrementarPartidasGanadas();
            ganador.incrementarRachaActual();
            ganador.actualizarRachaMaxima();
            Jugador perdedor = ganador.equals(jugadorBlanco) ? jugadorNegro : jugadorBlanco;
            perdedor.resetRachaActual();
            System.out.println("Partida finalizada. Ganador: " + ganador.getNombre());
            // Aquí se llamaría a la animación
        } else if (jugadorAbandono == null) { // Empate sin abandono
            jugadorBlanco.resetRachaActual();
            jugadorNegro.resetRachaActual();
            System.out.println("Partida finalizada. Es un empate!");
        }
        // Si hubo abandono, el que abandonó ya reseteó su racha (o debería)
        if (jugadorAbandono != null) {
             jugadorAbandono.resetRachaActual();
        }


        System.out.println("--- Fin de la Partida ---");
        System.out.println(jugadorBlanco.getNombre() + " (Blanco): " + triangulosJugadorBlanco + " triángulos.");
        System.out.println(jugadorNegro.getNombre() + " (Negro): " + triangulosJugadorNegro + " triángulos.");
        if (ganador != null) {
            System.out.println("¡Felicidades " + ganador.getNombre() + "!");
        } else if (jugadorAbandono == null) {
            System.out.println("¡Ha sido un empate!");
        }
    }

    private void abandonarPartida(Jugador jugadorQueAbandona) {
        this.partidaTerminada = true;
        this.jugadorAbandono = jugadorQueAbandona;
        System.out.println("El jugador " + jugadorQueAbandona.getNombre() + " ha abandonado la partida.");
        determinarGanadorFinal(); // Esto establecerá al otro jugador como ganador y actualizará stats
    }

    private void mostrarHistorial() {
        System.out.println("--- Historial de Jugadas ---");
        if (historialJugadas.isEmpty()) {
            System.out.println("No se han realizado jugadas aún.");
        } else {
            for (int i = 0; i < historialJugadas.size(); i++) {
                System.out.println((i + 1) + ". " + historialJugadas.get(i));
            }
        }
        System.out.println("----------------------------");
    }

    // Clase interna para ayudar a parsear la jugada
    private static class ParsedJugada {
        private Punto origen; // Este será el objeto Punto del Tablero después de la validación
        private Direccion direccion;
        private int largo;

        public ParsedJugada(Punto origen, Direccion direccion, int largo) {
            this.origen = origen;
            this.direccion = direccion;
            this.largo = largo;
        }

        public Punto getOrigen() { return origen; }
        public void setOrigen(Punto origen) { this.origen = origen; } // Para actualizar con el Punto del Tablero
        public Direccion getDireccion() { return direccion; }
        public int getLargo() { return largo; }
    }
}
