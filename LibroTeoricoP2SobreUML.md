3.3 UML - Notación básica

UML (unified modelling language) es el lenguaje unificado de modelado. Es un lenguaje que permite visualizar, especificar y documentar los distintos elementos del software, entre otros aspectos. Se utiliza en especial en este curso para representar las clases y sus relaciones. En una primera etapa del curso solamente se verán los elementos fundamentales de la notación, con simplificaciones.

Un diagrama de clases sirve para visualizar las relaciones entre las clases que involucran el sistema. Veremos cómo representar una clase. 

3.3.1 Clase

La clase se representa con una caja con 3 partes:

## Clase `Nombre de la clase`
**Atributos**
**Métodos**

El área superior contiene el nombre de la clase. El nombre va en mayúscula y en singular.

El área intermedia contiene los atributos (o variables) que caracterizan a la clase. Los atributos representan los datos que vamos a guardar de cada uno de los objetos que vayamos a crear. A modo de ejemplo, para una clase que modele Persona pueden ser atributos: nombre, dirección, teléfono y mail. En general, los atributos se indican que son “privados”, o sea, su visibilidad es privada. Si se necesitan consultar o modificar, se hace a través de métodos.

Los atributos se indican con su visibilidad, nombre y tipo. La visibilidad es: 
	- privada (private): se antecede el signo de menos ( - ) al atributo. 
	- pública (public): se antecede el signo de más ( + ) al atributo. 
	- protegida (protected): se antecede el signo numeral ( # ) al atributo.

Se verá con más detalle más adelante todo lo referido a la visibilidad.

El área inferior contiene los métodos u operaciones, los cuales definen la forma de interacción del objeto con su entorno (dependiendo de la visibilidad: privada, protegida o pública). En general, por cada atributo se tiene un método que permite obtener su valor y otro que permite modificarlo. Así, si tenemos el atributo teléfono, tendremos un método para consultarlo (que de acuerdo a los estándares de Java se llama getTelefono) y otro para modificarlo (que se llamará setTelefono). Estos métodos se denominan “métodos de acceso y modificación”.

Los métodos se escriben indicando su visibilidad, el nombre, la lista de parámetros que reciben (si tuviera) y la indicación del retorno, que en el caso de ser métodos que no retornan nada se utiliza la palabra reservada void.

A modo de ejemplo en la notación UML, en caso de tener una clase Persona con dos atributos: nombre y edad, cuyo tipos de dato serán String e int respectivamente y métodos de acceso y modificación, el diagrama quedaría:

## Clase `Persona`
**Atributos**
	* `- nombre: String`
	* `- edad: int`
**Métodos**
	* `+ getNombre(): String`
	* `+ getEdad(): int`
	* `+ setNombre(String): void`
	* `+ setEdad(int): void`

Para el caso de métodos y/o atributos de clase (concepto que se verá más adelante), se utiliza la convención de subrayarlos y de utilizar nombres que comiencen en mayúscula.

3.3.2 Asociación

En el caso del ejemplo de la Biblioteca, entre Préstamo y Socio existe una relación denominada asociación. El préstamo corresponde a un socio: el socio participa del préstamo. También existe asociación entre Préstamo y Funcionario, y entre Préstamo y Material. En particular, el vínculo entre Préstamo y Material se representa así:

* `Prestamo ------ Material` (Asociación)

Puede indicarse el sentido de la relación, por ejemplo: el préstamo es de un material:

* `Prestamo -----> Material` (Asociación)

3.3.3 Generalización-especialización (Herencia)

Como se vio en el ejemplo de la Biblioteca, los materiales podrían ser publicaciones o videos. Así, una Publicación es un tipo de Material. Esa relación se denomina generalización-especialización: la publicación es un caso específico de material y el material generaliza el concepto de publicación. Publicación es subclase de Material, tiene todas las características del Material y puede incluir otras características propias.

Para indicar la relación se utiliza una línea que termina en un triángulo, como se indica en la siguiente figura:

* `Publicacion -----|> Material` (Herencia)
* `Video -----|> Material` (Herencia)

A través del mecanismo de herencia se comparten elementos de la relación de generalización especialización.

3.3.4 Agregación y Composición

En el ejemplo de la Biblioteca se vio que ella contenía la información de un conjunto de socios. Para los fines de este curso, no se hará distinción entre agregación y composición. Esta diferenciación es apropiada para cursos posteriores de diseño orientado a objetos. Este tipo de relación se indica en UML mediante una línea que culmina con un rombo, que puede ser blanco (agregación) o negro (composición).

Así, la Biblioteca contiene una lista de socios, en UML se representa así:

* `Socio -----<> Biblioteca` (Agregación)

3.4 Biblioteca: Diagrama de clases

En el diagrama de clases se incluyen las clases referidas al dominio o contexto del problema, con sus atributos, métodos y relaciones. Una primera aproximación del problema de la Biblioteca se presenta en forma parcial en el siguiente diagrama.

## 1. Clase `Biblioteca`
### Atributos
	* `- listaSocios`
	* `- listaMateriales`
	* `- listaPrestamos`
	* `- listaFuncionarios`
### Métodos

---

## 2. Clase `Persona`
### Atributos
	* `- nombre: String`
	* `- direccion: String`
### Métodos

---

## 3. Clase `Socio` extends `Persona`
### Atributos
	* `- numeroSocio: int`

---

### 3.1. Clase `Estudiante` extends `Socio`
#### Atributos
	* `- numeroEstudiante: int`

---

### 3.2. Clase `Profesor` extends `Socio`
#### Atributos
	* `- numeroProfesor: int`

---

## 4. Clase `Funcionario` extends `Persona`
### Atributos
	* `- numero: int`

---

## 5. Clase `Material`
### Atributos
	* `- numeroInventario: int`
	* `- titulo: String`

---

### 5.1. Clase `Publicacion` extends `Material`
#### Atributos
	* `- cantidadPaginas: int`

---

#### 5.1.1. Clase `Libro` extends `Publicacion`
##### Atributos
	* `- isbn: String`

---

#### 5.1.2. Clase `Revista` extends `Publicacion`
##### Atributos
	* `- issn: String`

---

### 5.2. Clase `Video` extends `Material`
#### Atributos
	* `- duracion: int`

---

## 6. Clase `Prestamo`
### Atributos
	* `- socio: Socio`
	* `- material: Material`
	* `- funcionario: Funcionario`
	* `- fecha: Date`

---

## Resumen de Relaciones Principales

* `Socio -----<> Biblioteca` (Agregación)
* `Funcionario -----<> Biblioteca` (Agregación)
* `Prestamo -----<> Biblioteca` (Agregación)
* `Material -----<> Biblioteca` (Agregación)

* `Prestamo -----> Material` (Asociación)
* `Prestamo -----> Funcionario` (Asociación)
* `Prestamo ------ Socio` (Asociación)

* `Publicacion -----|> Material` (Herencia)
* `Video -----|> Material` (Herencia)

* `Libro -----|> Publicacion` (Herencia)
* `Revista -----|> Publicacion` (Herencia)

* `Funcionario -----|> Persona` (Herencia)
* `Socio -----|> Persona` (Herencia)

* `Estudiante -----|> Socio` (Herencia)
* `Profesor -----|> Socio` (Herencia)

---

Estudiante y Profesor son un tipo de socio, "heredan" de socio todas las características, dado que son subclases de Socio. Notar que el nombre de la clase se pone con su inicial en mayúscula y en singular.

Las líneas entre clases muestran la relación entre ellas. Cada préstamo está asociado a Material, Funcionario y Socio (se marca con una línea, podría incluirse el sentido también). Publicación es un tipo de Material (se marca con un triángulo). La Biblioteca tiene un conjunto de socios (se marca con un rombo).
