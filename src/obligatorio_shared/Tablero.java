package obligatorio_shared;

import java.util.ArrayList;
import java.util.List;
// import java.util.Collections; // Para devolver copias inmodificables si es necesario

public class Tablero {

    // Lista para almacenar todos los puntos válidos en el tablero.
    // En lugar de un Map, usamos una Lista y buscaremos los puntos cuando sea necesario.
    private final List<Punto> puntosDisponibles;

    // Lista para almacenar todas las bandas que se han colocado en el tablero.
    private final List<Banda> bandasColocadas;

    // Lista para almacenar todos los triángulos que han sido ganados.
    private final List<Triangulo> triangulosGanados;

    /**
     * Constructor para la clase Tablero.
     * Inicializa las listas de puntos, bandas y triángulos.
     * Llama a un método para crear y agregar todos los puntos válidos del tablero.
     */
    public Tablero() {
        this.puntosDisponibles = new ArrayList<>();
        this.bandasColocadas = new ArrayList<>();
        this.triangulosGanados = new ArrayList<>();
        this.inicializarPuntosDelTablero();
    }

    /**
     * Inicializa todos los puntos válidos del tablero y los agrega a la lista `puntosDisponibles`.
     * Los puntos se definen según la forma de diamante del tablero.
     */
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

    /**
     * Método de ayuda para agregar puntos de una fila específica a la lista `puntosDisponibles`.
     * @param fila El número de la fila.
     * @param columnas Un array de caracteres representando las columnas válidas para esa fila.
     */
    private void agregarPuntosFila(int fila, char[] columnas) {
        for (char columna : columnas) {
            // La clase Punto ya valida si la coordenada es válida según el diseño del tablero.
            // Si se intenta crear un Punto inválido, su constructor lanzará IllegalArgumentException.
            // Aquí asumimos que las coordenadas provistas son las correctas para el tablero diamante.
            this.puntosDisponibles.add(new Punto(fila, columna));
        }
    }

    /**
     * Obtiene un punto específico del tablero basado en su fila y columna.
     * Busca en la lista `puntosDisponibles`.
     * @param columna La columna del punto (ej. 'A').
     * @param fila La fila del punto (ej. 1).
     * @return El objeto Punto si se encuentra, o null si no existe un punto en esas coordenadas.
     */
    public Punto getPunto(char columna, int fila) {
        for (Punto p : this.puntosDisponibles) {
            if (p.getFila() == fila && p.getColumna() == Character.toUpperCase(columna)) {
                return p;
            }
        }
        return null; // No se encontró el punto
    }
    
    /**
     * Obtiene un punto específico del tablero basado en su coordenada en formato String (ej. "D1").
     * @param coordenada La coordenada del punto como String.
     * @return El objeto Punto si se encuentra y la coordenada es válida, o null en caso contrario.
     */
    public Punto getPunto(String coordenada) {
        if (coordenada == null || coordenada.length() < 2) {
            return null;
        }
        char col = Character.toUpperCase(coordenada.charAt(0));
        try {
            int fil = Integer.parseInt(coordenada.substring(1));
            return getPunto(col, fil);
        } catch (NumberFormatException e) {
            return null; // Formato de fila inválido
        }
    }


    /**
     * Agrega una banda a la lista de bandas colocadas en el tablero.
     * @param banda La banda a agregar.
     */
    public void addBanda(Banda banda) {
        if (banda != null) {
            this.bandasColocadas.add(banda);
        }
    }

    /**
     * Devuelve una lista de todas las bandas colocadas en el tablero.
     * Se podría devolver una copia para evitar modificaciones externas: Collections.unmodifiableList(this.bandasColocadas).
     * @return La lista de bandas.
     */
    public List<Banda> getBandas() {
        return this.bandasColocadas;
        // Para mayor seguridad: return Collections.unmodifiableList(this.bandasColocadas);
    }

    /**
     * Agrega un triángulo ganado a la lista de triángulos.
     * @param triangulo El triángulo ganado a agregar.
     */
    public void addTrianguloGanado(Triangulo triangulo) {
        if (triangulo != null) {
            this.triangulosGanados.add(triangulo);
        }
    }

    /**
     * Devuelve una lista de todos los triángulos ganados en el tablero.
     * @return La lista de triángulos ganados.
     */
    public List<Triangulo> getTriangulosGanados() {
        return this.triangulosGanados;
        // Para mayor seguridad: return Collections.unmodifiableList(this.triangulosGanados);
    }

    /**
     * Obtiene una lista de los triángulos ganados por un jugador específico.
     * @param jugador El jugador cuyos triángulos ganados se quieren obtener.
     * @return Una lista de triángulos ganados por el jugador.
     */
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
    
    /**
     * Obtiene una lista de las bandas que conectan con un punto específico.
     * @param punto El punto para el cual se buscan las bandas.
     * @return Una lista de bandas que utilizan el punto dado.
     */
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

    /**
     * Verifica si dos puntos son adyacentes en el tablero diamante.
     * Esta lógica es crucial para validar la colocación de bandas.
     * Se replica o se hace accesible desde la clase Banda.
     * @param p1 Un punto.
     * @param p2 Otro punto.
     * @return true si son adyacentes, false en caso contrario.
     */
    public static boolean sonPuntosAdyacentes(Punto p1, Punto p2) {
        if (p1 == null || p2 == null || p1.equals(p2)) {
            return false;
        }
        int diffFilas = Math.abs(p1.getFila() - p2.getFila());
        int diffCols = Math.abs(p1.getColumna() - p2.getColumna());

        // Condición 1: Misma fila, 2 columnas de diferencia (ej: A4-C4)
        boolean mismaFilaAdy = (diffFilas == 0 && diffCols == 2);

        // Condición 2: Fila adyacente, 1 columna de diferencia (ej: D1-C2)
        boolean filaAdyacenteAdy = (diffFilas == 1 && diffCols == 1);

        return mismaFilaAdy || filaAdyacenteAdy;
    }

    /**
     * Obtiene una lista de puntos que son adyacentes a un punto dado.
     * @param punto El punto de referencia.
     * @return Una lista de puntos adyacentes.
     */
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


    @Override
    public String toString() {
        // Define las dimensiones de la grilla de visualización.
        // Columnas de 'A' (0) a 'M' (12). Son 13 letras.
        // Para mostrar puntos y bandas horizontales entre ellos, necesitamos más espacio.
        // Ej: A-B-C -> P H P H P (P=Punto, H=Banda Horizontal). (13*2 - 1 = 25 caracteres de ancho)
        final int numFilas = 7;
        final int numColsLetras = 13; // A-M
        final int anchoDisplay = numColsLetras * 2 -1; // Para puntos y espacios/bandas horizontales
        
        char[][] displayGrid = new char[numFilas][anchoDisplay];

        // 1. Inicializar la grilla con espacios.
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < anchoDisplay; j++) {
                displayGrid[i][j] = ' ';
            }
        }

        // 2. Dibujar los puntos disponibles.
        for (Punto p : this.puntosDisponibles) {
            int displayFila = p.getFila() - 1;
            int displayCol = (p.getColumna() - 'A') * 2; // Cada letra ocupa una posición par.
            if(displayFila >= 0 && displayFila < numFilas && displayCol >=0 && displayCol < anchoDisplay){
                 displayGrid[displayFila][displayCol] = '*'; // Símbolo para un punto.
            }
        }
        
        // 3. Dibujar las bandas colocadas.
        for (Banda banda : this.bandasColocadas) {
            Punto pA = banda.getPuntoA(); // Punto "menor"
            Punto pB = banda.getPuntoB(); // Punto "mayor"

            int filaA = pA.getFila() - 1;
            int colAIdx = pA.getColumna() - 'A';
            int displayColA = colAIdx * 2;

            int filaB = pB.getFila() - 1;
            int colBIdx = pB.getColumna() - 'A';
            // int displayColB = colBIdx * 2; // No siempre necesario

            if (filaA == filaB) { // Banda horizontal
                // La banda está entre pA y pB, en la posición impar.
                if (displayColA + 1 < anchoDisplay) {
                    displayGrid[filaA][displayColA + 1] = '-';
                }
            } else { // Banda diagonal (pA está arriba de pB o en la misma fila y a la izquierda)
                     // Como pA es el "menor", filaA <= filaB. Si no son iguales, filaA < filaB.
                if (colBIdx < colAIdx) { // pB está a la izquierda de pA (ej. pA=(2,D), pB=(3,C)) -> Banda '/'
                    // La banda '/' se dibuja en la fila de pA, una posición a la izquierda de pA.
                     if (displayColA - 1 >= 0) {
                        displayGrid[filaA][displayColA - 1] = '/';
                    }
                } else { // pB está a la derecha de pA (ej. pA=(2,C), pB=(3,D)) -> Banda '\'
                    // La banda '\' se dibuja en la fila de pA, una posición a la derecha de pA.
                    if (displayColA + 1 < anchoDisplay) {
                        displayGrid[filaA][displayColA + 1] = '\\';
                    }
                }
            }
        }
        
        // 4. Dibujar los triángulos ganados (marcar el centro).
        // Esto es una simplificación. El "centro" exacto puede variar.
        // Usaremos la posición de uno de los puntos del triángulo como placeholder,
        // o un espacio entre puntos si es un punto no existente (ej. E1 para D1-F1-E2).
        for (Triangulo t : this.triangulosGanados) {
            // Identificar el punto "central" o una coordenada representativa.
            // Para un triángulo (p1, p2, p3), el centro visual podría ser:
            // - Si dos puntos están en una fila R y uno en R+1 (o R-1) en columna C_mid:
            //   El símbolo podría ir en (R, C_mid_display_col)
            // Ejemplo: D1, F1, E2. Puntos en fila 1: D,F. Punto en fila 2: E.
            // El "centro" podría ser la posición (fila 1, columna E).
            // Punto E1 no existe, así que ese espacio en la grilla está libre.
            Punto p1 = t.getPunto1();
            Punto p2 = t.getPunto2();
            Punto p3 = t.getPunto3();
            
            // Heurística simple: tomar el promedio de coordenadas (aproximado para el display)
            // y usar el caracter del jugador (o 'T' genérico).
            // Esta es una heurística muy básica y puede necesitar refinamiento.
            // int avgFila = (p1.getFila() + p2.getFila() + p3.getFila()) / 3;
            // int avgColChar = (p1.getColumna() + p2.getColumna() + p3.getColumna()) / 3; // Promedio de ASCII
            
            // int displayFilaTri = p1.getFila() - 1;
            // La columna del display para el triángulo es más compleja.
            // Intentaremos colocarlo en un espacio "vacío" cercano al centroide.
            // Por simplicidad, si D1,F1,E2 -> centro en (Fila 1, Col E)
            // (1,D), (1,F), (2,E). Fila 1, Col E.
            // (displayFila=0, displayCol=('E'-'A')*2 = 8)
            // Este es un placeholder, la lógica real puede ser más compleja.
            // Por ahora, marcaremos el primer punto del triángulo con 'T' si no hay banda.
            
            // int triDisplayFila = p1.getFila() - 1;
            // int triDisplayCol = (p1.getColumna() - 'A') * 2;
            
            // Si el jugador es conocido y tiene un símbolo asociado (ej. □ o ■)
            // char simboloTri = (t.getJugadorGanador() != null) ? t.getJugadorGanador().getSimbolo() : 'T';
            char simboloTri = 'T'; // Placeholder. Debería ser '□' o '■' según ConsignaObligatorio.md y el jugador.
            if (t.getJugadorGanador() != null) {
                 // Aquí se podría tener una lógica para obtener '□' o '■'
                 // basado en el jugador. Por ejemplo, si Jugador tiene un método getSimboloTriangulo().
                 // String alias = t.getJugadorGanador().getUsername(); 
                 // simboloTri = alias.isEmpty() ? 'T' : alias.charAt(0); // Ejemplo simple
                 // O mejor: simboloTri = t.getJugadorGanador().getSimboloTriangulo();
            }


            // Intentar colocar el símbolo del triángulo en una posición representativa.
            // Para (D1,F1,E2), el centro es (Fila 1, Col E). Punto E1 no existe.
            // Para (C2,E2,D1), el centro es (Fila 2, Col D). Punto D2 no existe.
            // Buscamos el punto que está solo en su fila o el punto medio de la base.
            // Esta es una lógica simplificada para el centro del triángulo.
            int r1=p1.getFila(), r2=p2.getFila(), r3=p3.getFila();
            char c1=p1.getColumna(), c2=p2.getColumna(), c3=p3.getColumna();
            int targetFilaDisplay = -1, targetColDisplay = -1;

            if (r1==r2) { // p1,p2 en la misma fila, p3 en otra
                targetFilaDisplay = r1-1;
                targetColDisplay = ( (c1-'A')*2 + (c2-'A')*2 ) / 2 + 1; // Espacio entre p1 y p2
            } else if (r1==r3) { // p1,p3 en la misma fila, p2 en otra
                targetFilaDisplay = r1-1;
                targetColDisplay = ( (c1-'A')*2 + (c3-'A')*2 ) / 2 + 1;
            } else if (r2==r3) { // p2,p3 en la misma fila, p1 en otra
                targetFilaDisplay = r2-1;
                targetColDisplay = ( (c2-'A')*2 + (c3-'A')*2 ) / 2 + 1;
            }
            
            if(targetFilaDisplay != -1 && targetColDisplay != -1 &&
               targetFilaDisplay >= 0 && targetFilaDisplay < numFilas &&
               targetColDisplay >=0 && targetColDisplay < anchoDisplay) {
                if (displayGrid[targetFilaDisplay][targetColDisplay] == ' ') { // Solo si es un espacio vacío
                     displayGrid[targetFilaDisplay][targetColDisplay] = simboloTri;
                } else { 
                    // Si el espacio preferido está ocupado (ej. por una banda),
                    // intentamos colocarlo en el punto "pico" del triángulo.
                    Punto pico = null;
                    if(r1 != r2 && r1 != r3) pico = p1;
                    else if (r2 != r1 && r2 != r3) pico = p2;
                    else pico = p3; // p3 es el pico si r1==r2, o si r1,r2,r3 son distintos (no debería pasar para triángulo válido)
                    if (pico != null) {
                        // Asegurarse de que el pico es el correcto si los tres puntos están en filas distintas (no es un triángulo elemental)
                        // Esta lógica de "pico" puede necesitar revisión para casos más complejos de triángulos.
                        // Para triángulos elementales (dos en una fila, uno en la adyacente), esta heurística es más simple.
                        displayGrid[pico.getFila()-1][(pico.getColumna()-'A')*2] = simboloTri; // Sobrescribe el '*' del punto
                    }
                }
            }
        }


        // 5. Construir el String final con encabezados de columna.
        StringBuilder sb = new StringBuilder();
        // Encabezado de columnas (sin espacios iniciales extra, la indentación del tablero la da la grilla)
        sb.append("A B C D E F G H I J K L M\n"); 
        sb.append("\n"); // Primera línea vacía después del encabezado
        sb.append("\n"); // Segunda línea vacía después del encabezado

        for (int i = 0; i < numFilas; i++) {
            // Imprimir la fila de la grilla tal como está (contiene los espacios para la forma de diamante)
            for (int j = 0; j < anchoDisplay; j++) {
                sb.append(displayGrid[i][j]);
            }
            sb.append("\n"); // Termina la línea de la grilla

            if (i < numFilas - 1) { // Si no es la última fila de la grilla
                sb.append("\n"); // Agregar una línea vacía entre las filas de la grilla
            }
        }
        // No hay borde inferior

        return sb.toString();
    }
}
