**Roadmap / Plan de Acción** paso a paso para abordar el obligatorio de Programación 2 "Triángulos":

**Fase 0: Preparación y Diseño Inicial (Antes de escribir mucho código)**

1.  **Entender a Fondo:** Lee la consigna completa *varias veces*. Asegúrate de entender *cada* regla del juego, cada opción de configuración y cada requisito de entrega. Anota cualquier duda para preguntarle al docente.
2.  **Configuración del Entorno:**
    * Crea un nuevo proyecto Java ANT en NetBeans.
    * Asegúrate de que tu NetBeans y JDK estén configurados correctamente.
    * Agrega la línea `System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));` en tu clase principal (la que tenga el `main`) para asegurar la correcta visualización de caracteres especiales (□, ■).
3.  **Diseño Inicial (Clases del Dominio - UML):**
    * Piensa en las "cosas" principales del problema: `Jugador`, `Partida`, `Tablero`, `Punto` (o Clavo), `Banda` (o Segmento), `Triangulo` (quizás?), `ConfiguracionPartida`.
    * Empieza a esbozar el diagrama UML *solo con estas clases del dominio*. ¿Qué atributos necesita cada una? (Ej: `Jugador` necesita `nombre`, `edad`, `partidasGanadas`, `rachaActual`, `rachaMaxima`). ¿Qué relaciones hay entre ellas? (Ej: Una `Partida` tiene dos `Jugadores`, tiene un `Tablero`, usa una `ConfiguracionPartida`).
    * *No te preocupes por los métodos aún*, enfócate en los datos que necesitas almacenar. Este UML evolucionará, pero tener una idea inicial es crucial.

**Fase 1: Representación del Tablero y Elementos Básicos**

4.  **Clase `Punto` (o `Clavo`):** Define cómo representar un punto en el tablero. Probablemente necesites coordenadas (¿quizás fila y columna? O ¿x, y?). Podrías necesitar saber si un punto está "activo" o es parte de una banda.
5.  **Clase `Banda`:** ¿Qué define una banda? Necesita un punto de inicio, una dirección (`enum` puede ser ideal aquí: NOROESTE, NORESTE, ESTE, SURESTE, SUROESTE, OESTE), una longitud y el jugador que la colocó.
6.  **Clase `Tablero`:**
    * ¿Cómo almacenar los puntos? ¿Una matriz 2D? ¿Una lista? Considera la forma hexagonal/triangular del tablero. Mapear las coordenadas (A1, B2, etc.) a una estructura de datos interna será un desafío clave.
    * ¿Cómo almacenar las bandas colocadas? Una `ArrayList<Banda>` podría funcionar.
    * ¿Cómo almacenar los triángulos ganados? Quizás necesitas una forma de marcar qué jugador ganó qué triángulo. Podrías tener una estructura que almacene los triángulos (definidos por sus 3 puntos) y a qué jugador pertenecen.
    * Implementa un método básico para *mostrar* el tablero en consola, inicialmente solo con los puntos ('*'). Inspírate en el formato de la letra.

**Fase 2: Lógica Central del Juego (Sin Menús aún)**

7.  **Colocar Banda:** En la clase `Tablero` (o una clase `ControladorJuego`), implementa la lógica para añadir una `Banda`. Esto implica:
    * Validar que la banda esté dentro de los límites del tablero.
    * Validar la longitud (según configuración, empieza con largo fijo 4 por defecto).
    * Validar si se requiere "contacto" (según configuración, empieza sin requerirlo).
    * Actualizar el estado del tablero para reflejar la nueva banda.
8.  **Mostrar Bandas en el Tablero:** Modifica el método de mostrar tablero para que dibuje las bandas (\, /, ---) sobre los puntos. Este es otro desafío visual importante.
9.  **Detección de Triángulos:** ¡Esta es la parte más compleja de la lógica!
    * Después de colocar una banda, necesitas verificar si se formaron *nuevos* triángulos de lado 1.
    * ¿Cómo definir un triángulo? Son 3 puntos adyacentes conectados por 3 bandas.
    * Piensa en los patrones: ¿qué puntos y bandas forman un triángulo mínimo?
    * Implementa la lógica para buscar estos patrones alrededor de la *nueva* banda colocada.
    * Marca los triángulos encontrados como "ganados" por el jugador actual (actualiza la estructura de datos de triángulos ganados). Asegúrate de no sobrescribir triángulos ya ganados.
10. **Mostrar Triángulos Ganados:** Actualiza el método de mostrar tablero para que dibuje los símbolos '□' o '■' en el centro de los triángulos ganados.

**Fase 3: Estructura de la Partida y Turnos**

11. **Clase `Jugador`:** Implementa la clase `Jugador` con nombre, edad y los atributos necesarios para el ranking (ganadas, racha).
12. **Clase `Sistema` (o `InterfazConsola`, `GestorJuego`):** Esta clase contendrá la lógica principal de la aplicación (el `main` y la gestión de menús).
    * Necesitará almacenar la lista de jugadores registrados (`ArrayList<Jugador>`).
    * Necesitará almacenar la configuración actual de la partida.
13. **Clase `Partida`:**
    * Almacena los dos `Jugador`es.
    * Almacena el `Tablero` actual.
    * Almacena la `ConfiguracionPartida` utilizada.
    * Lleva la cuenta de los turnos y qué jugador tiene el turno actual.
    * Lleva la cuenta de los triángulos ganados por cada jugador en *esta* partida.
    * Almacena el historial de jugadas (`ArrayList<String>`).
14. **Flujo Básico de Partida:** Implementa un método en `Sistema` o `Partida` que simule una partida simple:
    * Alterna turnos entre los dos jugadores.
    * Pide la jugada al jugador actual (formato LetraFilaDireccionCantidad). *Inicialmente, no te preocupes mucho por la validación perfecta del input, enfócate en que la lógica funcione*.
    * Llama a la lógica de colocar banda y detectar triángulos.
    * Muestra el tablero actualizado.
    * Verifica la condición de fin de partida (cantidad de bandas colocadas).
    * Determina el ganador.

**Fase 4: Menú Principal e Interacción con el Usuario**

15. **Menú Principal:** Implementa el menú en consola en tu clase `Sistema` con las opciones a, b, c, d, e. Usa un bucle y un `switch` o `if-else` para manejar la selección del usuario.
16. **Opción (a) Registrar Jugador:**
    * Pide nombre y edad.
    * Valida que el nombre sea único.
    * Crea un objeto `Jugador` y añádelo a la lista de jugadores en `Sistema`.
17. **Opción (b) Configurar Partida:**
    * Crea una clase `ConfiguracionPartida` para almacenar los 4 parámetros configurables (contacto, largo bandas, cantidad bandas, cantidad tableros).
    * Implementa la lógica para preguntar al usuario si quiere configuración por defecto o especial.
    * Si es especial, pide cada uno de los valores y valida que sean correctos.
    * Almacena esta configuración (probablemente en la clase `Sistema`) para que la use la próxima partida.
18. **Opción (c) Comenzar Partida:**
    * Muestra la lista de jugadores registrados, ordenados alfabéticamente y numerados.
    * Pide al usuario que elija dos números de jugador diferentes. Valida la entrada.
    * Crea un nuevo objeto `Partida` con los jugadores seleccionados y la configuración actual.
    * Inicia el bucle principal de la partida (el que desarrollaste en la Fase 3).
    * **Refinamiento de Input:** Ahora implementa la validación *robusta* de la entrada de la jugada (formato, coordenadas válidas, dirección válida, longitud válida según configuración). Si es inválida, pide reingresar.
    * **Comandos 'H' y 'X':** Dentro del bucle de la partida, verifica si el input es 'H' o 'X' y actúa en consecuencia (mostrar historial o terminar partida y asignar derrota).
    * **Múltiples Tableros:** Implementa la lógica para mostrar 1, 2, 3 o 4 tableros según la configuración. Necesitarás almacenar los estados anteriores del tablero (quizás copias del objeto `Tablero` o una forma de reconstruirlo a partir del historial) y mostrarlos lado a lado.
    * **Fin de Partida:** Al terminar (por bandas o por 'X'), actualiza las estadísticas de los jugadores (ganadas, racha).
    * **Animación:** Implementa una función simple que imprima caracteres y/o use secuencias de escape ANSI (si la consola lo soporta) para simular los "fuegos artificiales". Puedes usar `Thread.sleep()` para pausar brevemente entre "frames" de la animación.

**Fase 5: Ranking y Estadísticas**

19. **Opción (d) Mostrar Ranking y Racha:**
    * Implementa la lógica para ordenar la lista de jugadores en `Sistema` por partidas ganadas (descendente). Puedes usar `Collections.sort()` con un `Comparator` personalizado.
    * Muestra la lista ordenada.
    * Recorre la lista de jugadores para encontrar el/los jugador/es con la `rachaMaxima` más alta y muéstralos.

**Fase 6: Pulido Final y Entrega**

20. **Pruebas Exhaustivas:** Prueba *todas* las opciones del menú, todas las configuraciones, jugadas válidas e inválidas, casos borde (primera jugada, última jugada, llenar el tablero, etc.), comandos 'H' y 'X'.
21. **Manejo de Excepciones:** Revisa tu código y asegúrate de manejar posibles errores (ej. entrada no numérica cuando se espera un número) usando `try-catch` donde sea apropiado.
22. **Calidad del Código:**
    * Revisa nombres de variables y métodos (claros, descriptivos, sigue convenciones Java - camelCase).
    * Elimina código repetido (crea métodos reutilizables).
    * Añade comentarios donde la lógica no sea obvia.
    * Asegúrate de que cada archivo `.java` tenga el nombre de los autores al inicio.
    * Formatea el código consistentemente.
23. **Documentación (PDF):**
    * Finaliza el diagrama UML de clases del dominio (atributos y *métodos principales*).
    * Crea la carátula con toda la información requerida (fotos, nombres, números).
    * Ensambla el PDF.
24. **Empaquetado:**
    * Crea el archivo ZIP asegurándote de que contenga la carpeta del proyecto NetBeans (sin comprimir internamente, sin la carpeta `build` o `dist` si es posible para reducir tamaño) y el PDF. Verifica el límite de tamaño (40MB).

**Consejos Adicionales:**

* **Empieza Simple:** No intentes implementar todo de golpe. Haz funcionar una parte básica (mostrar tablero, poner una banda) y luego añade complejidad.
* **Prueba Constantemente:** Después de implementar cada pequeña funcionalidad, pruébala para asegurarte de que funciona antes de seguir. Escribir pequeñas pruebas unitarias (aunque no sea un requisito formal) puede ayudar mucho.
* **Usa Control de Versiones (Git):** ¡Fundamental! Aunque trabajen juntos en la misma máquina, usa Git. Crea commits frecuentes después de cada paso funcional. Te salvará de desastres. Plataformas como GitHub o GitLab ofrecen repositorios privados gratuitos.
* **Divide y Vencerás:** Si trabajas en equipo, dividan las tareas *después* de tener un diseño base acordado. Uno puede trabajar en la lógica del tablero, otro en la interfaz de menú, etc. Asegúrense de integrar el código frecuentemente.
* **No te Atasques:** Si estás bloqueado en una parte específica por mucho tiempo, pide ayuda al docente, a los ayudantes o discútelo con tu compañero. A veces, una perspectiva externa desbloquea el problema.
* **Relee la Consigna:** Antes de entregar, vuelve a leer toda la consigna y la lista de sugerencias para verificar que no te olvidaste de nada.

Este plan puede parecer largo, pero cada paso es mucho más pequeño que el proyecto completo. ¡Mucho ánimo con el obligatorio! Tienes tiempo, empieza paso a paso y verás cómo avanza.
