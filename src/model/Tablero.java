package model;

// import model.Punto;
// import model.Jugador;
// import model.Banda;
import java.util.ArrayList;
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
            
            char simboloTri = 'T'; 

            int r1=p1.getFila(), r2=p2.getFila(), r3=p3.getFila();
            char c1=p1.getColumna(), c2=p2.getColumna(), c3=p3.getColumna();
            
            int targetFilaDisplayOriginal = -1;
            int targetColDisplayOriginal = -1;

            if (r1==r2) {
                targetFilaDisplayOriginal = r1-1;
                targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 ;
            } else if (r1==r3) {
                targetFilaDisplayOriginal = r1-1;
                targetColDisplayOriginal = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 ;
            } else if (r2==r3) {
                targetFilaDisplayOriginal = r2-1;
                targetColDisplayOriginal = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 ;
            }

            if(targetFilaDisplayOriginal != -1 && targetColDisplayOriginal != -1) {
                int finalTargetFila = targetFilaDisplayOriginal * 2;
                // int finalTargetCol = targetColDisplayOriginal;
                targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + ((r1==r2 || r1==r3 || r2==r3) ? 1:0) ;
                if (r1==r2) { targetFilaDisplayOriginal = r1-1; targetColDisplayOriginal = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + 1; }
                else if (r1==r3) { targetFilaDisplayOriginal = r1-1; targetColDisplayOriginal = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 + 1; }
                else if (r2==r3) { targetFilaDisplayOriginal = r2-1; targetColDisplayOriginal = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 + 1; }

                if(targetFilaDisplayOriginal != -1 &&
                   finalTargetFila >= 0 && finalTargetFila < numDisplayFilas &&
                   targetColDisplayOriginal >=0 && targetColDisplayOriginal < anchoDisplay) {
                    if (displayGrid[finalTargetFila][targetColDisplayOriginal] == ' ') { 
                         displayGrid[finalTargetFila][targetColDisplayOriginal] = simboloTri;
                    } else { 
                        Punto pico = null;
                        if(r1 != r2 && r1 != r3) pico = p1;
                        else if (r2 != r1 && r2 != r3) pico = p2;
                        else pico = p3; 
                        if (pico != null) {
                            displayGrid[(pico.getFila()-1)*2][(pico.getColumna()-'A')*2] = simboloTri; 
                        }
                    }
                }
            }
        }    

        StringBuilder sb = new StringBuilder();
        sb.append("  ");
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