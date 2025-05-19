//  * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 


package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Tablero {

    private List<Punto> puntosDisponibles;
    private List<Banda> bandasColocadas;
    private List<Triangulo> triangulosGanados;

    // Constructor del tablero.
    public Tablero() {
        this.puntosDisponibles = new ArrayList<>();
        this.bandasColocadas = new ArrayList<>();
        this.triangulosGanados = new ArrayList<>();
        this.inicializarPuntosDelTablero();
    }

    // Constructor de copia.
    public Tablero(Tablero original) {
        this();
        for (Banda banda : original.bandasColocadas) {
            this.bandasColocadas.add(banda);
        }
        for (Triangulo triangulo : original.triangulosGanados) {
            this.triangulosGanados.add(triangulo);
        }
    }

    // Inicializa los puntos.
    private void inicializarPuntosDelTablero() {
        agregarPuntosFila(1, new char[]{'D', 'F', 'H', 'J'});
        agregarPuntosFila(2, new char[]{'C', 'E', 'G', 'I', 'K'});
        agregarPuntosFila(3, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        agregarPuntosFila(4, new char[]{'A', 'C', 'E', 'G', 'I', 'K', 'M'});
        agregarPuntosFila(5, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        agregarPuntosFila(6, new char[]{'C', 'E', 'G', 'I', 'K'});
        agregarPuntosFila(7, new char[]{'D', 'F', 'H', 'J'});
    }

    // Agrega puntos a fila.
    private void agregarPuntosFila(int fila, char[] columnas) {
        for (char columna : columnas) {
            this.puntosDisponibles.add(new Punto(fila, columna));
        }
    }

    // Devuelve punto por coordenadas.
    public Punto getPunto(char columna, int fila) {
        Punto puntoEncontrado = null;
        char colMayuscula = Character.toUpperCase(columna);
        boolean encontrado = false; 
        for (int i = 0; i < this.puntosDisponibles.size() && !encontrado; i++) {
            Punto p = this.puntosDisponibles.get(i);
            if (p.getFila() == fila && p.getColumna() == colMayuscula) {
                puntoEncontrado = p;
                encontrado = true;
            }
        }
        return puntoEncontrado;
    }

    // Devuelve punto por string.
    public Punto getPunto(String coordenada) {
        Punto puntoResultado = null;
        if (coordenada != null && coordenada.length() >= 2) {
            char col = Character.toUpperCase(coordenada.charAt(0));
            try {
                int fil = Integer.parseInt(coordenada.substring(1));
                puntoResultado = getPunto(col, fil);
            } catch (NumberFormatException e) {
                
            }
        }
        return puntoResultado;
    }

    // Agrega banda al tablero.
    public void addBanda(Banda banda) {
        if (banda != null) {
            this.bandasColocadas.add(banda);
        }
    }

    // Devuelve todas las bandas.
    public List<Banda> getBandas() {
        return this.bandasColocadas;
    }

    // Agrega triángulo ganado.
    public void addTrianguloGanado(Triangulo triangulo) {
        if (triangulo != null) {
            this.triangulosGanados.add(triangulo);
        }
    }

    // Devuelve triángulos ganados.
    public List<Triangulo> getTriangulosGanados() {
        return this.triangulosGanados;
    }

    // Triángulos ganados por jugador.
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

    // Bandas que usan punto.
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

    // Verifica si puntos adyacentes.
    public static boolean sonPuntosAdyacentes(Punto p1, Punto p2) {
        boolean adyacentes = false;
        if (p1 != null && p2 != null && !p1.equals(p2)) {
            int diffFilas = Math.abs(p1.getFila() - p2.getFila());
            int diffCols = Math.abs(p1.getColumna() - p2.getColumna());

            boolean mismaFilaAdy = (diffFilas == 0 && diffCols == 2);
            boolean filaAdyacenteAdy = (diffFilas == 1 && diffCols == 1);

            adyacentes = mismaFilaAdy || filaAdyacenteAdy;
        }
        return adyacentes;
    }

    // Devuelve puntos adyacentes.
    public List<Punto> getPuntosAdyacentes(Punto punto) {
        List<Punto> adyacentes = new ArrayList<>();
        if (punto != null) {
            for (Punto p : this.puntosDisponibles) {
                if (sonPuntosAdyacentes(punto, p)) {
                    adyacentes.add(p);
                }
            }
        }
        return adyacentes;
    }

    // Representación textual del tablero.
    @Override
    public String toString() {
        final int numFilasOriginal = 7;
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

            if (pA.getFila() == pB.getFila()) { // Banda horizontal
                int targetBandRow = origFilaA * 2;
                if (targetBandRow >= 0 && targetBandRow < numDisplayFilas &&
                    targetBandCol - 1 >= 0 && targetBandCol + 1 < anchoDisplay) {
                    displayGrid[targetBandRow][targetBandCol - 1] = '-';
                    displayGrid[targetBandRow][targetBandCol] = '-';
                    displayGrid[targetBandRow][targetBandCol + 1] = '-';
                }
            } else { // Banda diagonal
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
            
            char simboloTri = t.isWhitePlayer() ? '□' : '■';

            Punto[] puntosDelTriangulo = {p1, p2, p3};
            Arrays.sort(puntosDelTriangulo, Comparator.comparingInt(Punto::getFila)
                                              .thenComparingInt(Punto::getColumna));

            Punto ptA = puntosDelTriangulo[0];
            Punto ptB = puntosDelTriangulo[1];
            Punto ptC = puntosDelTriangulo[2];

            int symbolDisplayRow = -1;
            int symbolDisplayCol = -1;

            if (ptA.getFila() < ptB.getFila() && ptB.getFila() == ptC.getFila() &&
                ptC.getColumna() - ptB.getColumna() == 2 && ptA.getColumna() == ptB.getColumna() + 1) {
                symbolDisplayRow = (ptA.getFila() - 1) * 2 + 1;
                symbolDisplayCol = (ptA.getColumna() - 'A') * 2;
            } else if (ptC.getFila() > ptA.getFila() && ptA.getFila() == ptB.getFila() &&
                     ptB.getColumna() - ptA.getColumna() == 2 && ptC.getColumna() == ptA.getColumna() + 1) {
                symbolDisplayRow = (ptC.getFila() - 1) * 2 - 1;
                symbolDisplayCol = (ptC.getColumna() - 'A') * 2;
            }

            if (symbolDisplayRow != -1 && symbolDisplayCol != -1 &&
                symbolDisplayRow >= 0 && symbolDisplayRow < numDisplayFilas &&
                symbolDisplayCol >= 0 && symbolDisplayCol < anchoDisplay) {
                if (displayGrid[symbolDisplayRow][symbolDisplayCol] == ' ') {
                    displayGrid[symbolDisplayRow][symbolDisplayCol] = simboloTri;
                }
            }
        }

        String resultString = "";
        for (char c = 'A'; c < 'A' + numColsLetras; c++) {
            resultString += c; 
            if (c < 'A' + numColsLetras - 1) {
                resultString += " ";
            }
        }
        resultString += "\n\n";
        
        for (int i = 0; i < numDisplayFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                resultString += displayGrid[i][j]; 
            }
            resultString += "\n";
        }
        return resultString;
    }
}