# TRIANGULOS

Se desea hacer un programa Java que permita jugar a “Triángulos”, un divertido juego para 2 jugadores que alternan sus turnos. Este juego está basado en “Triggle”.

El tablero consiste en un conjunto de “clavos” o puntos (representados en la siguiente imagen por “*”), donde se enganchan “bandas elásticas” en sentido horizontal (hacia el este o hacia el oeste) o diagonal (hacia el noreste, noroeste, sureste o suroeste), tomando un par de puntos como extremos. Las columnas llevan las letras de “A” a “M” y las filas se asumen de 1 a 7.
Ejemplo del tablero inicial:

A B C D E F G H I J K L M


      *   *   *   *

    *   *   *   *   *

  *   *   *   *   *   *

*   *   *   *   *   *   *

  *   *   *   *   *   *

    *   *   *   *   *

      *   *   *   *


A B C D E F G H I J K L M <-- Columnas


      *   *   *   *       <-- Fila 1: D1, F1, H1, J1

    *   *   *   *   *     <-- Fila 2: C2, E2, G2, I2, K2

  *   *   *   *   *   *   <-- Fila 3: B3, D3, F3, H3, J3, L3

*   *   *   *   *   *   * <-- Fila 4: A4, C4, E4, G4, I4, K4, M4

  *   *   *   *   *   *   <-- Fila 5: B5, D5, F5, H5, J5, L5

    *   *   *   *   *     <-- Fila 6: C6, E6, G6, I6, K6

      *   *   *   *       <-- Fila 7: D7, F7, H7, J7


Por ejemplo, si se coloca un banda desde la posición D1 hacia sureste (abajo a la derecha), con largo 3 se obtiene:

A B C D E F G H I J K L M


      *   *   *   *
       \
    *   *   *   *   *
         \
  *   *   *   *   *   *
           \
*   *   *   *   *   *   *

  *   *   *   *   *   *

    *   *   *   *   *

      *   *   *   *

Si luego de colocar una banda, se forma uno o varios triángulos de lado 1, esos triángulos son “ganados” por el jugador. Ejemplo: si en el siguiente tablero de la izquierda, el jugador Blanco indica desde H3, hacia el oeste con longitud 2, gana un triángulo y se marca con el cuadradito blanco:

A B C D E F G H I J K L M       A B C D E F G H I J K L M


      *   *   *   *                   *   *   *   *
       \                               \
    *   *   *   *   *               *   *   *   *   *
         \     /                         \     /
  *   *   *   *   *   *           *   *---*---*   *   *
           \ /                             \□/
*---*---*---*---*   *   *       *---*---*---*---*   *   *
           /                               /
  *   *   *   *   *   *           *   *   *   *   *   *

    *   *   *   *   *               *   *   *   *   *

      *   *   *   *                   *   *   *   *

El juego termina al ubicar una cantidad de bandas dadas y gana el jugador que tenga más triángulos ganados o es empate si es la misma cantidad.

Se pide implementar en Java un programa que debe ofrecer un menú en consola con este título:
    **Trabajo desarrollado por: NOMBRE y NUMERO DE LOS AUTORES**
y estas opciones:
1. **Registrar un jugador**: se indica nombre (único) y edad.
2. **Configurar la partida**: se configuran las opciones de las siguientes partidas (hasta que se cambie nuevamente). Se indica si es por defecto o con configuración especial. Al comienzo del sistema se asume todo configurado por defecto. En el caso de configuración especial se indica:
    1. Ubicación de nueva banda: Por defecto: se puede poner en cualquier lugar. A partir del 2do movimiento, se indica si se requiere colocar la nueva banda que haga contacto, esto es, utilice algún punto ya utilizado por otra banda previa, o no. Por ejemplo, en el siguiente tablero si se indica que hay contacto, no sería válido indicar poner una banda en M4 con dirección suroeste de longitud 2.

A B C D E F G H I J K L M


      *   *   *   *
       \
    *   *   *   *   *
         \     /
  *   *   *   *   *   *
           \ /
*---*---*---*---*   *   *
           /
  *   *   *   *   *   *

    *   *   *   *   *

      *   *   *   *

		Nota: es válido que una banda se ubique sobre otra banda anterior (en forma total o parcial). Los triángulos ya ganados no cambian de color.
    2. Largo de las bandas. Por defecto se asume fijo de largo 4. Se puede configurar como variado, y permite largos de 1 a 4, o sea, en ese caso al jugar se puede indicar largo 1, 2 3 ó 4 en forma indistinta.
    3. Cantidad de bandas. Por defecto se asume 10. Se puede configurar la cantidad que se desee. Al ubicarse en el tablero esa cantidad de bandas termina la partida.
	4. Cantidad de tableros en pantalla. Por defecto se asume 1. Se puede indicar hasta 4. En caso de indicar más de 1, siempre el tablero más reciente se ve a la derecha y se van desplazando hacia la izquierda. Ejemplo: si se indican ver 3 tableros, luego de 2 jugadas se ve así:

A B C D E F G H I J K L M       A B C D E F G H I J K L M       A B C D E F G H I J K L M


      *   *   *   *                   *   *   *   *                   *   *   *   *
                                       \                               \
    *   *   *   *   *               *   *   *   *   *               *   *   *   *   *
                                         \                               \
  *   *   *   *   *   *           *   *   *   *   *   *           *   *   *   *   *   *
                                           \                               \
*   *   *   *   *   *   *       *   *   *   *   *   *   *       *---*---*---*---*   *   *

  *   *   *   *   *   *           *   *   *   *   *   *           *   *   *   *   *   *

    *   *   *   *   *               *   *   *   *   *               *   *   *   *   *

      *   *   *   *                   *   *   *   *                   *   *   *   *

y al ingresar la siguiente jugada se ve:

A B C D E F G H I J K L M       A B C D E F G H I J K L M       A B C D E F G H I J K L M


      *   *   *   *                   *   *   *   *                   *   *   *   *
       \                               \                               \
    *   *   *   *   *               *   *   *   *   *               *   *   *   *   *
         \                               \                               \     /
  *   *   *   *   *   *           *   *   *   *   *   *           *   *   *   *   *   *
           \                               \                               \ /
*   *   *   *   *   *   *       *---*---*---*---*   *   *       *---*---*---*---*   *   *

  *   *   *   *   *   *           *   *   *   *   *   *           *   *   *   *   *   *

    *   *   *   *   *               *   *   *   *   *               *   *   *   *   *

      *   *   *   *                   *   *   *   *                   *   *   *   *

3. **Comienzo de partida**: se muestra la lista de jugadores ordenada alfabéticamente y numerada y se eligen por su número dos jugadores diferentes. Se utiliza la configuración establecida (si no se indicó especial, se usa la por defecto). Siempre se indica en pantalla de quién es el turno (jugador Blanco o Negro). La jugada se indica:

    **LetraFilaDirecciónCantidad (ejemplos: D1C3, a4D4, I2Z3, K2z)**
	Letra: es de “A” a “M”.
	Fila: 1 a 7
	Dirección: “Q” es noroeste, “E” es noreste, “D” es este, “C” es sureste, “Z” es suroeste, “A" es oeste.
	Cantidad: es el largo de la banda. Si se omite se asume 4.

Luego de cada jugada, mostrar el tablero. Si la jugada es incorrecta, se reingresa.
No se puede pasar el turno.
Si se ingresa “X” termina la partida en el momento y pierde el jugador.
Si se ingresa “H” se muestra la lista de jugadas realizadas. Ejemplo:
	D1C3, A4D4, I2Z3, D3D2, F1Z4

Siempre se ve en pantalla la cantidad de triángulos obtenidos por el jugador Blanco y por el Negro.
Ejemplo:
	Cantidad Blanco : 1
	Cantidad Negro  : 1

El programa controla el fin de la partida. Al terminar una partida, si hay ganador mostrar efecto de animación con desplazamiento de “fuegos artificiales” con colores en consola por un breve tiempo (se sugiere utilizar en la consola el font Courier New)

Notas:
Asegurarse que se muestren correctamente caracteres especiales o internacionales en la consola.
Triángulo ganado por blanco: "□"
Triángulo ganado por negro: "■"
Se sugiere agregar:
	System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
Tiene como objetivo configurar la salida estándar (System.out) para usar codificación UTF-8, asegurando que se
impriman bien esos caracteres.

En todo el programa se considera indiferente mayúscula o minúscula.

4. Mostrar ranking y racha. Debe mostrarse el ranking de los jugadores ordenados por cantidad de partidas ganadas en forma decreciente e informar cuál es el/los jugador/es que tiene/n la racha ganadora más larga (racha: cantidad de partidas ganadas consecutivas).
5. Terminar el programa.

## Entrega
La entrega consiste de un archivo zip de hasta 40MB que contenga: 

1. carpeta del código. Dentro debe estar el proyecto completo en NetBeans, incluyendo todos los fuentes Java. 

    **IMPORTANTE**
    1. Al comienzo de cada clase del código fuente debe estar el nombre de los autores.
	2. Se tendrá especial consideración acerca de la calidad del código (reusabilidad, lógica, estilo de codificación, uso de Java, etc.).
	3. La interfaz debe ser lo más similar posible a la presentada. Deben respetarse estrictamente los formatos indicados (por ejemplo, para el ingreso de la jugada).
	4. Deben realizarse todas las validaciones.
	5. El proyecto debe ser ANT (no MAVEN ni otras opciones).
	6. El código NO debe estar comprimido

2. UNICO pdf que contenga:
	1. Carátula con foto académica, nombre y número de estudiante de los 2 integrantes del equipo. Las 2 personas deben pertenecer al mismo grupo de clase.
	2. Representación en UML exclusivamente de las clases del dominio del problema, con todos los métodos y atributos. No incluir la clase de Prueba ni interfaz