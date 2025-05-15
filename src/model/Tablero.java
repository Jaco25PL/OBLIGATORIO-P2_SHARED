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
        final int numFilasOriginal = 7; // Original number of rows with points
        if (numFilasOriginal <= 0) {
            return ""; // Or some other representation for an empty/invalid board
        }
        final int numDisplayFilas = numFilasOriginal * 2 - 1; // Total rows in the new display grid
        final int numColsLetras = 13; 
        final int anchoDisplay = numColsLetras * 2 -1; 
        
        char[][] displayGrid = new char[numDisplayFilas][anchoDisplay];

        // Initialize grid with spaces
        for (int i = 0; i < numDisplayFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                displayGrid[i][j] = ' ';
            }
        }

        // Place points (*)
        for (Punto p : this.puntosDisponibles) {
            int displayFila = (p.getFila() - 1) * 2; // Points go on even rows of the new grid
            int displayCol = (p.getColumna() - 'A') * 2; 
            if(displayFila >= 0 && displayFila < numDisplayFilas && displayCol >=0 && displayCol < anchoDisplay){
                 displayGrid[displayFila][displayCol] = '*'; 
            }
        }
        
        // Place bands (-, /, \)
        for (Banda banda : this.bandasColocadas) {
            Punto pA = banda.getPuntoA(); 
            Punto pB = banda.getPuntoB(); 

            int origFilaA = pA.getFila() - 1; // Original 0-indexed row for pA
            int displayColA = (pA.getColumna() - 'A') * 2;

            // int origFilaB = pB.getFila() - 1; // Original 0-indexed row for pB
            int displayColB = (pB.getColumna() - 'A') * 2;

            int targetBandCol = (displayColA + displayColB) / 2; // Column for the middle of the band character(s)

            if (pA.getFila() == pB.getFila()) { // Horizontal band
                int targetBandRow = origFilaA * 2; // Same (even) row as the points
                // Ensure the middle, left, and right positions for hyphens are valid
                if (targetBandRow >= 0 && targetBandRow < numDisplayFilas &&
                    targetBandCol - 1 >= 0 && targetBandCol + 1 < anchoDisplay) {
                    displayGrid[targetBandRow][targetBandCol - 1] = '-';
                    displayGrid[targetBandRow][targetBandCol] = '-';
                    displayGrid[targetBandRow][targetBandCol + 1] = '-';
                }
            } else { // Diagonal band
                // pA is the upper point due to Banda constructor normalization
                int targetBandRow = origFilaA * 2 + 1; // Odd row, between point rows
                char bandChar = (pB.getColumna() < pA.getColumna()) ? '/' : '\\'; // '/' for NW-SE, '\' for NE-SW

                if (targetBandRow >= 0 && targetBandRow < numDisplayFilas &&
                    targetBandCol >= 0 && targetBandCol < anchoDisplay) {
                    displayGrid[targetBandRow][targetBandCol] = bandChar;
                }
            }
        }
        
        // Place triangles (T)
        for (Triangulo t : this.triangulosGanados) {
            Punto p1 = t.getPunto1();
            Punto p2 = t.getPunto2();
            Punto p3 = t.getPunto3();
            
            char simboloTri = 'T'; 
            // if (t.getJugadorGanador() != null) {
            //     simboloTri = (t.getJugadorGanador().getNombre().equals("Blanco")) ? '□' : '■'; // Example
            // }

            int r1=p1.getFila(), r2=p2.getFila(), r3=p3.getFila();
            char c1=p1.getColumna(), c2=p2.getColumna(), c3=p3.getColumna();
            
            int targetFilaDisplayOriginal = -1; // Original row index for T
            int targetColDisplayOriginal = -1;  // Original column index for T (usually an odd column for between points)

            // This logic for T placement might need review based on desired appearance
            if (r1==r2) { // Base P1-P2 is horizontal
                targetFilaDisplayOriginal = r1-1;
                targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 ; // Column of midpoint
            } else if (r1==r3) { // Base P1-P3 is horizontal
                targetFilaDisplayOriginal = r1-1;
                targetColDisplayOriginal = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 ;
            } else if (r2==r3) { // Base P2-P3 is horizontal
                targetFilaDisplayOriginal = r2-1;
                targetColDisplayOriginal = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 ;
            }
            // If T is placed on a horizontal band segment, its row is an even new grid row.
            // If T is placed in the "center" of three points, its row might be an odd new grid row.
            // The existing logic for T seems to place it based on a horizontal base.
            // Let's assume for now it's placed on the same row as a horizontal band segment.
            // The original targetColDisplay was: ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + 1;
            // This put it in the space *after* the midpoint.
            // If it should be *on* the midpoint (like a horizontal band), it's ( (c1-'A')*2 + (c2-'A')*2 ) / 2;

            if(targetFilaDisplayOriginal != -1 && targetColDisplayOriginal != -1) {
                int finalTargetFila = targetFilaDisplayOriginal * 2; // Map to even row in new grid
                int finalTargetCol = targetColDisplayOriginal; // This should be an even column if on a point, odd if between
                                                               // The example from consigna shows T in the middle of 3 points.
                                                               // For D1-F1-E2, T is at E2's row, between D1-F1.
                                                               // This part of T placement is complex and might need more refinement
                                                               // based on exact desired visual.
                                                               // For simplicity, let's use the original logic for column, mapped to new row.
                targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + ((r1==r2 || r1==r3 || r2==r3) ? 1:0) ; // A guess to keep it similar
                                                                                                                // This is complex, the original T logic was:
                if (r1==r2) { targetFilaDisplayOriginal = r1-1; targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + 1; }
                else if (r1==r3) { targetFilaDisplayOriginal = r1-1; targetColDisplayOriginal = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 + 1; }
                else if (r2==r3) { targetFilaDisplayOriginal = r2-1; targetColDisplayOriginal = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 + 1; }


                if(targetFilaDisplayOriginal != -1 &&
                   finalTargetFila >= 0 && finalTargetFila < numDisplayFilas &&
                   targetColDisplayOriginal >=0 && targetColDisplayOriginal < anchoDisplay) {
                    if (displayGrid[finalTargetFila][targetColDisplayOriginal] == ' ') { 
                         displayGrid[finalTargetFila][targetColDisplayOriginal] = simboloTri;
                    } else { 
                        // Fallback for T if preferred spot is taken (original logic)
                        Punto pico = null;
                        if(r1 != r2 && r1 != r3) pico = p1;
                        else if (r2 != r1 && r2 != r3) pico = p2;
                        else pico = p3; 
                        if (pico != null) {
                            // Place on the point itself
                            displayGrid[(pico.getFila()-1)*2][(pico.getColumna()-'A')*2] = simboloTri; 
                        }
                    }
                }
            }
        }    

        StringBuilder sb = new StringBuilder();
        // Print column headers
        sb.append("  "); // Initial spacing for row numbers (if any)
        for (char c = 'A'; c < 'A' + numColsLetras; c++) {
            sb.append(c);
            if (c < 'A' + numColsLetras - 1) {
                sb.append(" "); // Space between column letters
            }
        }
        sb.append("\n");
        sb.append("\n");
        
        // Print the grid
        for (int i = 0; i < numDisplayFilas; i++) {
            // Optionally print row numbers for point rows
            //if (i % 2 == 0) {
            //    sb.append(String.format("%1d ", (i/2) + 1)); // Row number for point rows
            //} else {
            //    sb.append("  "); // Indent for band rows
            //}
            for (int j = 0; j < anchoDisplay; j++) {
                sb.append(displayGrid[i][j]);
            }
            sb.append("\n"); 
            // The extra sb.append("  \n"); is removed as intermediate rows are now part of displayGrid
        }
        return sb.toString();
    }
}