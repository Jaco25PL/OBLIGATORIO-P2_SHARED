# Diseño UML Detallado - Clases del Dominio "Triángulos"

A continuación se detallan las clases identificadas como parte del dominio del problema, con sus atributos, constructores, métodos (incluyendo getters y setters explícitos) y las relaciones entre ellas.

---

## 1. Enum `Direccion`

**Propósito:** Representa las 6 direcciones posibles para una banda.
**Tipo:** Enumeración (enum)
**Valores:**
  * `NOROESTE`
  * `NORESTE`
  * `ESTE`
  * `SURESTE`
  * `SUROESTE`
  * `OESTE`
**Métodos (Implícitos de Enum):**
  * `+ static values(): Direccion[]`
  * `+ static valueOf(String name): Direccion`
  * `+ toString(): String`

---

## 2. Clase `Jugador`

**Propósito:** Representa a un jugador con sus datos y estadísticas.
**Atributos:**
  * `- nombre: String`
  * `- edad: int`
  * `- partidasGanadas: int`
  * `- rachaActual: int`
  * `- rachaMaxima: int`
**Constructores:**
  * `+ Jugador(String nombre, int edad)`
**Métodos:**
  * `+ getNombre(): String`
  * `+ setNombre(String nombre): void`
  * `+ getEdad(): int`
  * `+ setEdad(int edad): void`
  * `+ getPartidasGanadas(): int`
  * `- setPartidasGanadas(int partidasGanadas): void` // Privado o protegido si solo se modifica internamente
  * `+ getRachaActual(): int`
  * `- setRachaActual(int rachaActual): void` // Privado o protegido
  * `+ getRachaMaxima(): int`
  * `- setRachaMaxima(int rachaMaxima): void` // Privado o protegido
  * `+ incrementarPartidasGanadas(): void`
  * `+ resetRachaActual(): void`
  * `+ incrementarRachaActual(): void`
  * `+ actualizarRachaMaxima(): void` // Compara rachaActual con rachaMaxima
  * `+ toString(): String` // Devuelve representación textual (ej: "Nombre (Edad)")
  * `+ equals(Object o): boolean` // Compara por 'nombre'
  * `+ hashCode(): int` // Basado en 'nombre'

---

## 3. Clase `Punto`

**Propósito:** Representa una ubicación ("clavo") específica en el tablero.
**Atributos:**
  * `- fila: int`
  * `- columna: char`
**Constructores:**
  * `+ Punto(int fila, char columna)`
**Métodos:**
  * `+ getFila(): int`
  * `+ setFila(int fila): void` // Considerar si debe ser inmutable (sin setters)
  * `+ getColumna(): char`
  * `+ setColumna(char columna): void` // Considerar si debe ser inmutable
  * `+ toString(): String` // Devuelve la coordenada (ej: "D1")
  * `+ equals(Object o): boolean` // Compara por 'fila' y 'columna'
  * `+ hashCode(): int` // Basado en 'fila' y 'columna'

---

## 4. Clase `Banda`

**Propósito:** Representa un segmento elástico entre dos puntos, colocado por un jugador.
**Atributos:**
  * `- puntoOrigen: Punto`
  * `- puntoDestino: Punto`
  * `- jugador: Jugador`
**Constructores:**
  * `+ Banda(Punto puntoOrigen, Punto puntoDestino, Jugador jugador)`
**Métodos:**
  * `+ getPuntoOrigen(): Punto`
  * `- setPuntoOrigen(Punto puntoOrigen): void` // Probablemente no necesario, una banda no cambia
  * `+ getPuntoDestino(): Punto`
  * `- setPuntoDestino(Punto puntoDestino): void` // Probablemente no necesario
  * `+ getJugador(): Jugador`
  * `- setJugador(Jugador jugador): void` // Probablemente no necesario
  * `+ toString(): String` // Representación (ej: "Banda de [Jugador] desde [PuntoOrigen] a [PuntoDestino]")
  * `+ equals(Object o): boolean` // Compara por ambos puntos (quizás sin importar orden) y jugador
  * `+ hashCode(): int` // Basado en puntos y jugador

---

## 5. Clase `Triangulo`

**Propósito:** Representa un triángulo elemental (lado 1) formado y potencialmente ganado.
**Atributos:**
  * `- punto1: Punto`
  * `- punto2: Punto`
  * `- punto3: Punto`
  * `- jugadorGanador: Jugador` // null si no ha sido ganado
**Constructores:**
  * `+ Triangulo(Punto p1, Punto p2, Punto p3)`
**Métodos:**
  * `+ getPunto1(): Punto`
  * `- setPunto1(Punto p1): void` // Probablemente no necesario
  * `+ getPunto2(): Punto`
  * `- setPunto2(Punto p2): void` // Probablemente no necesario
  * `+ getPunto3(): Punto`
  * `- setPunto3(Punto p3): void` // Probablemente no necesario
  * `+ getJugadorGanador(): Jugador`
  * `+ setJugadorGanador(Jugador jugadorGanador): void` // Necesario para marcarlo como ganado
  * `+ contienePunto(Punto p): boolean` // Verifica si el punto es uno de los vértices
  * `+ toString(): String` // Representación (ej: "Triángulo en [P1, P2, P3] ganado por [Jugador]")
  * `+ equals(Object o): boolean` // Compara los 3 puntos (sin importar el orden)
  * `+ hashCode(): int` // Basado en los 3 puntos (sin importar el orden)

---

## 6. Clase `ConfiguracionPartida`

**Propósito:** Almacena las reglas configurables para una o más partidas.
**Atributos:**
  * `- requiereContacto: boolean`
  * `- largoBandasVariable: boolean`
  * `- largoFijoDefault: int`
  * `- cantidadBandasFin: int`
  * `- cantidadTablerosMostrar: int`
**Constructores:**
  * `+ ConfiguracionPartida()` // Constructor para valores por defecto
  * `+ ConfiguracionPartida(boolean requiereContacto, boolean largoBandasVariable, int largoFijoDefault, int cantidadBandasFin, int cantidadTablerosMostrar)` // Para configuración personalizada
**Métodos:**
  * `+ isRequiereContacto(): boolean` // Getter para boolean
  * `+ setRequiereContacto(boolean requiereContacto): void`
  * `+ isLargoBandasVariable(): boolean` // Getter para boolean
  * `+ setLargoBandasVariable(boolean largoBandasVariable): void`
  * `+ getLargoFijoDefault(): int`
  * `+ setLargoFijoDefault(int largoFijoDefault): void`
  * `+ getCantidadBandasFin(): int`
  * `+ setCantidadBandasFin(int cantidadBandasFin): void`
  * `+ getCantidadTablerosMostrar(): int`
  * `+ setCantidadTablerosMostrar(int cantidadTablerosMostrar): void`
  * `+ toString(): String` // Descripción de la configuración

---

## 7. Clase `Tablero`

**Propósito:** Representa el estado del área de juego (puntos, bandas, triángulos).
**Atributos:**
  * `- puntos: Map<String, Punto>` // Mapa de Coordenada (ej "D1") a objeto Punto
  * `- bandas: List<Banda>` // Lista de bandas colocadas
  * `- triangulosGanados: List<Triangulo>` // Lista de triángulos ya asignados
**Constructores:**
  * `+ Tablero()` // Inicializa el mapa de puntos y las listas
**Métodos:**
  * `- inicializarPuntos(): void` // Privado, llamado por el constructor
  * `+ getPunto(char columna, int fila): Punto` // Busca o devuelve null
  * `+ getPunto(String coordenada): Punto` // Sobrecarga útil
  * `+ addBanda(Banda banda): void` // Añade a la lista `bandas`
  * `+ getBandas(): List<Banda>` // Devuelve copia o referencia inmodificable
  * `+ addTrianguloGanado(Triangulo t): void` // Añade a la lista `triangulosGanados`
  * `+ getTriangulosGanados(): List<Triangulo>` // Devuelve copia o referencia inmodificable
  * `+ getTriangulosGanadosPor(Jugador j): List<Triangulo>` // Filtra la lista
  * `+ getBandasQueUsanPunto(Punto p): List<Banda>` // Filtra la lista `bandas`
  * `+ getPuntosAdyacentes(Punto p): List<Punto>` // Calcula vecinos válidos
  * `+ toString(): String` // **Método clave:** Genera la representación textual del tablero para la consola

---

## 8. Clase `Partida`

**Propósito:** Gestiona el flujo completo de una sesión de juego.
**Atributos:**
  * `- jugadorBlanco: Jugador`
  * `- jugadorNegro: Jugador`
  * `- tablero: Tablero`
  * `- configuracion: ConfiguracionPartida`
  * `- turnoActual: Jugador` // Referencia a jugadorBlanco o jugadorNegro
  * `- bandasColocadas: int` // Contador
  * `- triangulosBlanco: int` // Contador
  * `- triangulosNegro: int` // Contador
  * `- historialJugadas: List<String>` // Almacena inputs como "D1C3"
  * `- partidaTerminada: boolean`
  * `- ganador: Jugador` // null si empate o no terminada
  * `- jugadorAbandono: Jugador` // null si nadie abandonó
**Constructores:**
  * `+ Partida(Jugador jugadorBlanco, Jugador jugadorNegro, ConfiguracionPartida configuracion)`
**Métodos:**
  * `+ getJugadorBlanco(): Jugador`
  * `+ getJugadorNegro(): Jugador`
  * `+ getTablero(): Tablero`
  * `+ getConfiguracion(): ConfiguracionPartida`
  * `+ getTurnoActual(): Jugador`
  * `+ getBandasColocadas(): int`
  * `+ getTriangulosBlanco(): int`
  * `+ getTriangulosNegro(): int`
  * `+ getHistorialJugadas(): List<String>` // Devuelve copia o referencia inmodificable
  * `+ isPartidaTerminada(): boolean`
  * `+ getGanador(): Jugador` // Devuelve el ganador o null
  * `+ getJugadorAbandono(): Jugador` // Devuelve quién abandonó o null
  * `+ procesarJugada(String inputJugada): boolean` // Lógica principal: valida, ejecuta, actualiza estado. Retorna éxito/fracaso.
  * `- validarFormatoJugada(String input): boolean` // Valida sintaxis "A1D4"
  * `- parsearJugada(String input): ParsedMove` // Clase interna o DTO con Punto, Direccion, Largo
  * `- validarLogicaJugada(Punto origen, Direccion dir, int largo): boolean` // Límites, contacto, largo variable/fijo
  * `- calcularPuntoDestino(Punto origen, Direccion dir, int largo): Punto`
  * `- colocarBanda(Punto origen, Punto destino)` // Crea Banda, la añade al tablero
  * `- detectarYAsignarTriangulos(Banda nuevaBanda): int` // Busca, asigna, retorna #nuevos
  * `- actualizarPuntajes(int nuevosTriangulos)`
  * `- registrarJugadaHistorial(String inputJugada)`
  * `- cambiarTurno(): void`
  * `- verificarFinPartida(): boolean` // Chequea bandasColocadas vs cantidadBandasFin
  * `- determinarGanadorFinal(): void` // Compara triángulos al final normal
  * `+ abandonar(Jugador jugadorQueAbandona): void` // Marca fin, establece perdedor

---

## Resumen de Relaciones Principales (UML)

* **Partida y Jugador:**
    * `Partida --- 2 --> Jugador` (Asociación)
    * Una `Partida` está asociada exactamente con dos objetos `Jugador`. La flecha indica que desde la `Partida` se puede navegar/acceder a los `Jugador`es (roles: `jugadorBlanco`, `jugadorNegro`).
* **Partida y ConfiguracionPartida:**
    * `Partida --- 1 --> ConfiguracionPartida` (Asociación)
    * Una `Partida` usa una instancia de `ConfiguracionPartida`.
* **Partida y Tablero:**
    * `Partida *--- 1 --> Tablero` (Composición)
    * Una `Partida` **posee** un `Tablero`. El rombo relleno (`*`) indica composición: el `Tablero` es creado por la `Partida` y su ciclo de vida depende de ella (si se destruye la `Partida`, se destruye su `Tablero`).
* **Tablero y Punto:**
    * `Tablero *--- * --> Punto` (Composición)
    * El `Tablero` **posee** y gestiona múltiples objetos `Punto` (a través del `Map`).
* **Tablero y Banda:**
    * `Tablero *--- 0..* --> Banda` (Composición)
    * El `Tablero` **contiene** cero o más objetos `Banda`. Las bandas existen *dentro* de un tablero específico.
* **Tablero y Triangulo:**
    * `Tablero *--- 0..* --> Triangulo` (Composición)
    * El `Tablero` **contiene** cero o más objetos `Triangulo` que han sido ganados en él.
* **Banda y Punto:**
    * `Banda --- 2 --> Punto` (Asociación)
    * Una `Banda` está asociada con exactamente dos `Punto`s (roles: `puntoOrigen`, `puntoDestino`).
* **Banda y Jugador:**
    * `Banda --- 1 --> Jugador` (Asociación)
    * Una `Banda` está asociada con el `Jugador` que la colocó.
* **Triangulo y Punto:**
    * `Triangulo --- 3 --> Punto` (Asociación)
    * Un `Triangulo` está asociado con exactamente tres `Punto`s (sus vértices).
* **Triangulo y Jugador:**
    * `Triangulo --- 0..1 --> Jugador` (Asociación)
    * Un `Triangulo` puede estar asociado con cero o un `Jugador` (el que lo ganó, rol: `jugadorGanador`).

*(Nota: La clase `Direccion` (enum) es usada por valor, generalmente no se muestra con una línea de relación explícita en diagramas de clase complejos, aunque `Partida` y `Tablero` la usarán indirectamente al procesar jugadas).*
