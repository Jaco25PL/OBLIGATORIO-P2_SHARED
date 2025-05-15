/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra ######] // Reemplazar con datos reales
 */

package model;

import model.Punto;
import model.Jugador;
import model.Banda;
import java.util.ArrayList;
import java.util.List;
// import java.util.Collections; // Para devolver copias inmodificables si es necesario

public class Tablero {

    private final List<Punto> puntosDisponibles;
    private final List<Banda> bandasColocadas;
    private final List<Triangulo> triangulosGanados;

    // constructor del tablero.
    public Tablero() {
        this.puntosDisponibles = new ArrayList<>();
        this.bandasColocadas = new ArrayList<>();
        this.triangulosGanados = new ArrayList<>();
        this.inicializarPuntosDelTablero();
    }

    // inicializa puntos del tablero.
    private void inicializarPuntosDelTablero() {
        // Fila 1: D, F, H, J
        agregarPuntosFila(1, new char[]{'D', 'F', 'H', 'J'});
        // Fila 2: C, E, G, I, K
        agregarPuntosFila(2, new char[]{'C', 'E', 'G', 'I', 'K'});
        // Fila 3: B, D, F, H, J, L
        agregarPuntosFila(3, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        // Fila 4: A, C, E, G, I, K, M
        agregarPuntosFila(4, new char[]{'A', 'C', 'E', 'G', 'I', 'K', 'M'});
        // Fila 5: B, D, F, H, J, L (simétrica a la fila 3)
        agregarPuntosFila(5, new char[]{'B', 'D', 'F', 'H', 'J', 'L'});
        // Fila 6: C, E, G, I, K (simétrica a la fila 2)
        agregarPuntosFila(6, new char[]{'C', 'E', 'G', 'I', 'K'});
        // Fila 7: D, F, H, J (simétrica a la fila 1)
        agregarPuntosFila(7, new char[]{'D', 'F', 'H', 'J'});
    }

    // agrega puntos de una fila.
    private void agregarPuntosFila(int fila, char[] columnas) {
        for (char columna : columnas) {
            this.puntosDisponibles.add(new Punto(fila, columna));
        }
    }

    // obtiene punto por coordenadas.
    public Punto getPunto(char columna, int fila) {
        for (Punto p : this.puntosDisponibles) {
            if (p.getFila() == fila && p.getColumna() == Character.toUpperCase(columna)) {
                return p;
            }
        }
        return null; 
    }
    
    // obtiene punto por string.
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

    // agrega banda al tablero.
    public void addBanda(Banda banda) {
        if (banda != null) {
            this.bandasColocadas.add(banda);
        }
    }

    // obtiene todas las bandas.
    public List<Banda> getBandas() {
        return this.bandasColocadas;
    }

    // agrega triángulo ganado.
    public void addTrianguloGanado(Triangulo triangulo) {
        if (triangulo != null) {
            this.triangulosGanados.add(triangulo);
        }
    }

    // obtiene triángulos ganados.
    public List<Triangulo> getTriangulosGanados() {
        return this.triangulosGanados;
    }

    // obtiene triángulos de jugador.
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
    
    // obtiene bandas de un punto.
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

    // verifica si puntos adyacentes.
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

    // obtiene puntos adyacentes.
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

    // devuelve representación textual tablero.
    @Override
    public String toString() {
        final int numFilas = 7;
        final int numColsLetras = 13; 
        final int anchoDisplay = numColsLetras * 2 -1; 
        
        char[][] displayGrid = new char[numFilas][anchoDisplay];

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                displayGrid[i][j] = ' ';
            }
        }

        for (Punto p : this.puntosDisponibles) {
            int displayFila = p.getFila() - 1;
            int displayCol = (p.getColumna() - 'A') * 2; 
            if(displayFila >= 0 && displayFila < numFilas && displayCol >=0 && displayCol < anchoDisplay){
                 displayGrid[displayFila][displayCol] = '*'; 
            }
        }
        
        for (Banda banda : this.bandasColocadas) {
            Punto pA = banda.getPuntoA(); 
            Punto pB = banda.getPuntoB(); 

            int filaA = pA.getFila() - 1;
            int colAIdx = pA.getColumna() - 'A';
            int displayColA = colAIdx * 2;

            int filaB = pB.getFila() - 1;
            int colBIdx = pB.getColumna() - 'A';

            if (filaA == filaB) { 
                if (displayColA + 1 < anchoDisplay) {
                    displayGrid[filaA][displayColA + 1] = '-';
                }
            } else { 
                if (colBIdx < colAIdx) { 
                     if (displayColA - 1 >= 0) {
                        displayGrid[filaA][displayColA - 1] = '/';
                    }
                } else { 
                    if (displayColA + 1 < anchoDisplay) {
                        displayGrid[filaA][displayColA + 1] = '\\';
                    }
                }
            }
        }
        
        for (Triangulo t : this.triangulosGanados) {
            Punto p1 = t.getPunto1();
            Punto p2 = t.getPunto2();
            Punto p3 = t.getPunto3();
            
            char simboloTri = 'T'; 
            // if (t.getJugadorGanador() != null) {
            // }

            int r1=p1.getFila(), r2=p2.getFila(), r3=p3.getFila();
            char c1=p1.getColumna(), c2=p2.getColumna(), c3=p3.getColumna();
            int targetFilaDisplay = -1, targetColDisplay = -1;

            if (r1==r2) { 
                targetFilaDisplay = r1-1;
                targetColDisplay = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + 1; 
            } else if (r1==r3) { 
                targetFilaDisplay = r1-1;
                targetColDisplay = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 + 1;
            } else if (r2==r3) { 
                targetFilaDisplay = r2-1;
                targetColDisplay = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 + 1;
            }
            
            if(targetFilaDisplay != -1 && targetColDisplay != -1 &&
               targetFilaDisplay >= 0 && targetFilaDisplay < numFilas &&
               targetColDisplay >=0 && targetColDisplay < anchoDisplay) {
                if (displayGrid[targetFilaDisplay][targetColDisplay] == ' ') { 
                     displayGrid[targetFilaDisplay][targetColDisplay] = simboloTri;
                } else { 
                    Punto pico = null;
                    if(r1 != r2 && r1 != r3) pico = p1;
                    else if (r2 != r1 && r2 != r3) pico = p2;
                    else pico = p3; 
                    if (pico != null) {
                        displayGrid[pico.getFila()-1][(pico.getColumna()-'A')*2] = simboloTri; 
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("  A B C D E F G H I J K L M\n"); // Ajuste para alinear con números de fila
        
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                sb.append(displayGrid[i][j]);
            }
            sb.append("\n"); 

            if (i < numFilas - 1) { 
                sb.append("  \n"); // Línea vacía con espaciado para mantener alineación
            }
        }
        return sb.toString();
    }
}