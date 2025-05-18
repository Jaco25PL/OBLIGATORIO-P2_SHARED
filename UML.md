## 1. Clase `Direccion`

**Atributos**

- `- direccionChar: char`
- `+ _NOROESTE_CHAR: char_` // Assuming char constants like 'Q'
- `+ _NORESTE_CHAR: char_`
- `+ _ESTE_CHAR: char_`
- `+ _SURESTE_CHAR: char_`
- `+ _SUROESTE_CHAR: char_`
- `+ _OESTE_CHAR: char_`
- `+ _NW: Direccion_` // Static final instances of Direccion
- `+ _NE: Direccion_`
- `+ _E: Direccion_`
- `+ _SE: Direccion_`
- `+ _SW: Direccion_`
- `+ _W: Direccion_`
  **Métodos**
- `+ _static fromChar(char c): Direccion_`
- `+ toString(): String`
- `+ equals(Object o): boolean`
- `+ hashCode(): int`

---

## 2. Clase `Punto`

**Atributos**

- `- fila: int`
- `- columna: char`
  **Métodos**
- `+ toString(): String`
- `+ equals(Object o): boolean`
- `+ hashCode(): int`

---

## 3. Clase `Jugador`

**Atributos**

- `- nombre: String`
- `- edad: int`
- `- partidasGanadas: int`
- `- rachaActualVictorias: int`
- `- mejorRachaVictorias: int`
  **Métodos**
- `+ incrementarPartidasGanadas(): void`
- `+ resetRachaActual(): void`
- `+ incrementarRachaActual(): void`
- `+ actualizarMejorRacha(): void`
- `+ toString(): String`
- `+ equals(Object o): boolean`
- `+ hashCode(): int`

---

## 4. Clase `Banda`

**Atributos**

- `- puntoA: Punto`
- `- puntoB: Punto`
- `- jugador: Jugador`
  **Métodos**
- `+ isWhitePlayer(): boolean`
- `+ toString(): String`
- `+ equals(Object o): boolean`
- `+ hashCode(): int`

---

## 5. Clase `Triangulo`

**Atributos**

- `- punto1: Punto`
- `- punto2: Punto`
- `- punto3: Punto`
- `- jugadorGanador: Jugador`
- `- isWhitePlayer: boolean`
  **Métodos**
- `+ setJugadorGanador(Jugador jugadorGanador, boolean isWhitePlayer): void`
- `+ isWhitePlayer(): boolean`
- `+ toString(): String`
- `+ equals(Object o): boolean`
- `+ hashCode(): int`

---

## 6. Clase `ConfiguracionPartida`

**Atributos**

- `- requiereContacto: boolean`
- `- largoBandasVariable: boolean`
- `- largoFijo: int`
- `- cantidadBandasFin: int`
- `- cantidadTablerosMostrar: int`
- `+ _MIN_BANDAS_FIN: int_`
- `+ _MAX_LARGO_BANDA: int_`
- `+ _MIN_LARGO_BANDA: int_`
- `+ _MIN_TABLEROS_MOSTRAR: int_`
- `+ _MAX_TABLEROS_MOSTRAR: int_`
- `+ _DEFAULT_LARGO_FIJO: int_`
- `+ _DEFAULT_CANT_BANDAS_FIN: int_`
- `+ _DEFAULT_CANT_TABLEROS_MOSTRAR: int_`
- `+ _DEFAULT_REQUIERE_CONTACTO: boolean_`
- `+ _DEFAULT_LARGO_VARIABLE: boolean_`
  **Métodos**
- `+ resetToDefaults(): void`
- `+ toString(): String`

---

## 7. Clase `Tablero`

**Atributos**

- `- puntosDisponibles: List<Punto>`
- `- bandasColocadas: List<Banda>`
- `- triangulosGanados: List<Triangulo>`
  **Métodos**
- `- inicializarPuntosDelTablero(): void`
- `- agregarPuntosFila(int fila, char[] columnas): void`
- `+ getPunto(char columna, int fila): Punto`
- `+ getPunto(String coordenada): Punto`
- `+ addBanda(Banda banda): void`
- `+ getBandas(): List<Banda>`
- `+ addTrianguloGanado(Triangulo triangulo): void`
- `+ getTriangulosGanados(): List<Triangulo>`
- `+ getTriangulosGanadosPor(Jugador jugador): List<Triangulo>`
- `+ getBandasQueUsanPunto(Punto punto): List<Banda>`
- `+ _static sonPuntosAdyacentes(Punto p1, Punto p2): boolean_`
- `+ getPuntosAdyacentes(Punto punto): List<Punto>`
- `+ toString(): String`

---

## 8. Clase `Partida`

**Atributos**

- `- jugadorBlanco: Jugador`
- `- jugadorNegro: Jugador`
- `- tablero: Tablero`
- `- configuracion: ConfiguracionPartida`
- `- turnoActual: Jugador`
- `- bandasColocadasEnPartida: int`
- `- triangulosJugadorBlanco: int`
- `- triangulosJugadorNegro: int`
- `- historialJugadas: List<String>`
- `- partidaTerminada: boolean`
- `- ganador: Jugador`
- `- jugadorAbandono: Jugador`
- `- movimientosRealizados: int`
- `- _MAX_TRIANGULOS_POSIBLES: int_`
- `- historialDeTablerosSnapshots: List<Tablero>`
- `- maxHistorialSnapshots: int`
  **Métodos**
- `+ getHistorialDeTablerosSnapshots(): List<Tablero>`
- `+ getTurnoActual(): Jugador`
- `+ getHistorialJugadas(): List<String>`
- `+ isPartidaTerminada(): boolean`
- `+ getGanador(): Jugador`
- `+ getJugadorAbandono(): Jugador`
- `+ procesarJugada(String inputJugada): boolean`
- `- detectarNuevosTriangulosConBanda(Banda banda): int`
- `- parsearJugadaInput(String input): Object`
- `- validarLogicaJugada(Object parsedJugada): boolean`
- `- calcularPuntoSiguiente(Punto actual, Direccion dir): Punto`
- `- cambiarTurno(): void`
- `- verificarFinPartida(): boolean`
- `- determinarGanadorFinal(): void`
- `- abandonarPartida(Jugador jugadorQueAbandona): void`
- `- mostrarHistorial(): void`

---

## Resumen de Relaciones Principales

- `Partida ------ 1 Direccion` (Asociación: `Partida` utiliza `Direccion` en `calcularPuntoSiguiente`)
- `Partida ---<> 1 Tablero` (Agregación: `Partida` posee su `tablero` actual)
- `Partida ---<> 0..* Tablero` (Agregación: `Partida` contiene `historialDeTablerosSnapshots`)
- `Partida ------ 1 ConfiguracionPartida` (Asociación: `Partida` usa una `configuracion`)
- `Partida ------ 2 Jugador` (Asociación: `Partida` tiene `jugadorBlanco` y `jugadorNegro`)
- `Partida ----> Jugador` (Asociación: `turnoActual` en `Partida` es un `Jugador`)
- `Partida ----> Jugador` (Asociación: `ganador` en `Partida` puede ser un `Jugador`)
- `Partida ----> Jugador` (Asociación: `jugadorAbandono` en `Partida` puede ser un `Jugador`)

- `Tablero ---<> * Punto` (Agregación: `Tablero` contiene `puntosDisponibles`)
- `Tablero ---<> 0..* Banda` (Agregación: `Tablero` contiene `bandasColocadas`)
- `Tablero ---<> 0..* Triangulo` (Agregación: `Tablero` contiene `triangulosGanados`)

- `Banda ------ 2 Punto` (Asociación: `Banda` se define por `puntoA` y `puntoB`)
- `Banda ----> Jugador` (Asociación: `Banda` pertenece a un `Jugador`)

- `Triangulo ------ 3 Punto` (Asociación: `Triangulo` se define por `punto1`, `punto2`, `punto3`)
- `Triangulo ----> Jugador` (Asociación: `Triangulo` puede tener un `jugadorGanador`)

_(Nota: La clase `Partida` tiene un método `calcularPuntoSiguiente` que utiliza la clase `Direccion` para indicar la dirección. La clase interna `ParsedJugada` de `Partida` se omite aquí, y sus usos se representan con `Object` o se infieren de los métodos privados de `Partida`.)_
