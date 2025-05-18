//  * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 


package model;

// import model.Punto;
// import model.Jugador;
// import model.Banda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tablero {

    private final List<Punto> puntosDisponibles;
    private final List<Banda> bandasColocadas;
    private final List<Triangulo> triangulosGanados;

    // Constructor
    public Tablero() {
        this.puntosDisponibles = new ArrayList<>();
        this.bandasColocadas = new ArrayList<>();
        this.triangulosGanados = new ArrayList<>();
        this.inicializarPuntosDelTablero();
    }

    // NUEVO: Constructor de copia
    public Tablero(Tablero original) {
        this(); // Llama al constructor por defecto para inicializar puntos y listas vacías

        // Copia las bandas. Asumimos que los objetos Banda son inmutables una vez creados.
        // Si fueran mutables, necesitarías crear nuevas instancias de Banda aquí.
        for (Banda banda : original.bandasColocadas) {
            this.bandasColocadas.add(banda);
        }

        // Copia los triángulos ganados. Similar a las bandas.
        for (Triangulo triangulo : original.triangulosGanados) {
            this.triangulosGanados.add(triangulo);
        }
    }

    // Inicializa los puntos
    private void inicializarPuntosDelTablero() {
        agregarPuntosFila(1, new char[]{'D', 'F', 'H', 'J'});
        agregarPuntosFila(2, new char[]{'C', 'E', 'G', 'I', 'K'});
        agregarPuntosFila(3, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        agregarPuntosFila(4, new char[]{'A', 'C', 'E', 'G', 'I', 'K', 'M'});
        agregarPuntosFila(5, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        agregarPuntosFila(6, new char[]{'C', 'E', 'G', 'I', 'K'});
        agregarPuntosFila(7, new char[]{'D', 'F', 'H', 'J'});
    }

    // Agrega puntos a una fila
    private void agregarPuntosFila(int fila, char[] columnas) {
        for (char columna : columnas) {
            this.puntosDisponibles.add(new Punto(fila, columna));
        }
    }

    // Devuelve punto por coordenadas
    public Punto getPunto(char columna, int fila) {
        for (Punto p : this.puntosDisponibles) {
            if (p.getFila() == fila && p.getColumna() == Character.toUpperCase(columna)) {
                return p;
            }
        }
        return null;
    }
    
    // Devuelve punto por string
    public Punto getPunto(String coordenada) {
        if (coordenada == null || coordenada.length() < 2) {
            return null;
        }
        char col = Character.toUpperCase(coordenada.charAt(0));
        try {
            int fil = Integer.parseInt(coordenada.substring(1));
            return getPunto(col, fil);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Agrega banda al tablero
    public void addBanda(Banda banda) {
        if (banda != null) {
            this.bandasColocadas.add(banda);
        }
    }

    // Devuelve todas las bandas
    public List<Banda> getBandas() {
        return this.bandasColocadas;
    }

    // Agrega triángulo ganado
    public void addTrianguloGanado(Triangulo triangulo) {
        if (triangulo != null) {
            this.triangulosGanados.add(triangulo);
        }
    }

    // Devuelve triángulos ganados
    public List<Triangulo> getTriangulosGanados() {
        return this.triangulosGanados;
    }

    // Triángulos ganados por jugador
    public List<Triangulo> getTriangulosGanadosPor(Jugador jugador) {
        List<Triangulo> triangulosDelJugador = new ArrayList<>();
        if (jugador != null) {
            for (Triangulo t : this.triangulosGanados) {
                if (jugador.equals(t.getJugadorGanador())) {
                    triangulosDelJugador.add(t);
                }
            }
        }
        return triangulosDelJugador;
    }
    
    // Bandas que usan un punto
    public List<Banda> getBandasQueUsanPunto(Punto punto) {
        List<Banda> bandasDelPunto = new ArrayList<>();
        if (punto != null) {
            for (Banda b : this.bandasColocadas) {
                if (b.getPuntoA().equals(punto) || b.getPuntoB().equals(punto)) {
                    bandasDelPunto.add(b);
                }
            }
        }
        return bandasDelPunto;
    }

    // Verifica si puntos adyacentes
    public static boolean sonPuntosAdyacentes(Punto p1, Punto p2) {
        if (p1 == null || p2 == null || p1.equals(p2)) {
            return false;
        }
        int diffFilas = Math.abs(p1.getFila() - p2.getFila());
        int diffCols = Math.abs(p1.getColumna() - p2.getColumna());

        boolean mismaFilaAdy = (diffFilas == 0 && diffCols == 2);
        boolean filaAdyacenteAdy = (diffFilas == 1 && diffCols == 1);

        return mismaFilaAdy || filaAdyacenteAdy;
    }

    // Devuelve puntos adyacentes
    public List<Punto> getPuntosAdyacentes(Punto punto) {
        List<Punto> adyacentes = new ArrayList<>();
        if (punto == null) {
            return adyacentes;
        }
        for (Punto p : this.puntosDisponibles) {
            if (sonPuntosAdyacentes(punto, p)) {
                adyacentes.add(p);
            }
        }
        return adyacentes;
    }

    // Representación textual del tablero
    @Override
    public String toString() {
        final int numFilasOriginal = 7;
        // if (numFilasOriginal <= 0) {
        //     return "";
        // }
        final int numDisplayFilas = numFilasOriginal * 2 - 1;
        final int numColsLetras = 13;
        final int anchoDisplay = numColsLetras * 2 -1;
        
        char[][] displayGrid = new char[numDisplayFilas][anchoDisplay];

        for (int i = 0; i < numDisplayFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                displayGrid[i][j] = ' ';
            }
        }

        for (Punto p : this.puntosDisponibles) {
            int displayFila = (p.getFila() - 1) * 2;
            int displayCol = (p.getColumna() - 'A') * 2; 
            if(displayFila >= 0 && displayFila < numDisplayFilas && displayCol >=0 && displayCol < anchoDisplay){
                 displayGrid[displayFila][displayCol] = '*'; 
            }
        }
        
        for (Banda banda : this.bandasColocadas) {
            Punto pA = banda.getPuntoA(); 
            Punto pB = banda.getPuntoB(); 

            int origFilaA = pA.getFila() - 1;
            int displayColA = (pA.getColumna() - 'A') * 2;
            int displayColB = (pB.getColumna() - 'A') * 2;
            int targetBandCol = (displayColA + displayColB) / 2;

            if (pA.getFila() == pB.getFila()) {
                int targetBandRow = origFilaA * 2;
                if (targetBandRow >= 0 && targetBandRow < numDisplayFilas &&
                    targetBandCol - 1 >= 0 && targetBandCol + 1 < anchoDisplay) {
                    displayGrid[targetBandRow][targetBandCol - 1] = '-';
                    displayGrid[targetBandRow][targetBandCol] = '-';
                    displayGrid[targetBandRow][targetBandCol + 1] = '-';
                }
            } else {
                int targetBandRow = origFilaA * 2 + 1;
                char bandChar = (pB.getColumna() < pA.getColumna()) ? '/' : '\\';
                if (targetBandRow >= 0 && targetBandRow < numDisplayFilas &&
                    targetBandCol >= 0 && targetBandCol < anchoDisplay) {
                    displayGrid[targetBandRow][targetBandCol] = bandChar;
                }
            }
        }
        
        for (Triangulo t : this.triangulosGanados) {
            Punto p1 = t.getPunto1();
            Punto p2 = t.getPunto2();
            Punto p3 = t.getPunto3();
            
            char simboloTri;
            if (t.getJugadorGanador() != null) {
                if (t.isWhitePlayer()) {
                    simboloTri = '□';  // White square for white player
                } else {
                    simboloTri = '■';  // Black square for black player
                }
            } else {
                simboloTri = '?'; // No debería ocurrir si la lógica es correcta
            }

            Punto[] puntosDelTriangulo = {p1, p2, p3};
            // Ordenar los puntos: primero por fila, luego por columna.
            // Esto ayuda a identificar la orientación del triángulo (hacia arriba o hacia abajo).
            Arrays.sort(puntosDelTriangulo, Comparator.comparingInt(Punto::getFila)
                                              .thenComparingInt(Punto::getColumna));

            Punto ptA = puntosDelTriangulo[0]; // El punto más arriba (o más a la izquierda si están en la misma fila)
            Punto ptB = puntosDelTriangulo[1]; // Punto intermedio
            Punto ptC = puntosDelTriangulo[2]; // El punto más abajo (o más a la derecha si están en la misma fila)

            int symbolDisplayRow = -1;
            int symbolDisplayCol = -1;

            // Verificar si es un triángulo que apunta hacia ARRIBA
            // (ptA es el pico superior, ptB y ptC forman la base horizontal inferior)
            if (ptA.getFila() < ptB.getFila() && 
                ptB.getFila() == ptC.getFila() && 
                ptC.getColumna() - ptB.getColumna() == 2 && // Base tiene ancho 2 (ej. F, H)
                ptA.getColumna() == ptB.getColumna() + 1) {  // Pico está centrado sobre la base
                
                symbolDisplayRow = (ptA.getFila() - 1) * 2 + 1; // Fila impar debajo del pico
                symbolDisplayCol = (ptA.getColumna() - 'A') * 2; // Columna par del pico
            } 
            // Verificar si es un triángulo que apunta hacia ABAJO
            // (ptC es el pico inferior, ptA y ptB forman la base horizontal superior)
            else if (ptC.getFila() > ptA.getFila() && 
                     ptA.getFila() == ptB.getFila() &&
                     ptB.getColumna() - ptA.getColumna() == 2 && // Base tiene ancho 2
                     ptC.getColumna() == ptA.getColumna() + 1) {  // Pico está centrado bajo la base
                
                symbolDisplayRow = (ptC.getFila() - 1) * 2 - 1; // Fila impar encima del pico
                symbolDisplayCol = (ptC.getColumna() - 'A') * 2; // Columna par del pico
            }

            if (symbolDisplayRow != -1 && symbolDisplayCol != -1 &&
                symbolDisplayRow >= 0 && symbolDisplayRow < numDisplayFilas &&
                symbolDisplayCol >= 0 && symbolDisplayCol < anchoDisplay) {
                
                if (displayGrid[symbolDisplayRow][symbolDisplayCol] == ' ') {
                    displayGrid[symbolDisplayRow][symbolDisplayCol] = simboloTri;
                } else {
                    // Si el lugar calculado no está vacío (inesperado para triángulos unitarios bien formados),
                    // se podría imprimir un error o intentar un fallback muy simple,
                    // pero es preferible no dibujar si no se encuentra un buen lugar.
                    // System.err.println("Advertencia: La celda para el símbolo del triángulo " + t + " no estaba vacía: (" + symbolDisplayRow + "," + symbolDisplayCol + ") contiene '" + displayGrid[symbolDisplayRow][symbolDisplayCol] + "'");
                }
            } else {
                // Si no se pudo determinar la posición (triángulo no estándar o error de cálculo)
                // System.err.println("Advertencia: No se pudo determinar la posición para el símbolo del triángulo: " + t);
            }
        }    

        StringBuilder sb = new StringBuilder();
        // sb.append("  ");
        for (char c = 'A'; c < 'A' + numColsLetras; c++) {
            sb.append(c);
            if (c < 'A' + numColsLetras - 1) {
                sb.append(" ");
            }
        }
        sb.append("\n");
        sb.append("\n");
        
        for (int i = 0; i < numDisplayFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                sb.append(displayGrid[i][j]);
            }
            sb.append("\n"); 
        }
        return sb.toString();
    }
}