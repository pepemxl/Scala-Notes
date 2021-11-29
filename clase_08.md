# Composición y Herencia

## Clases en Scala

Como en la mayoria de los lenguajes orientados a objetos tenemos clases en Scala, con más razón siendo un lenguaje que proviene de JAVA, dada una clase nosotros podemos crear instancias u objatos de dicha clase:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
class Person(name: String, age: Int) {
  def isAdult: Boolean = age >= 18
}

val p1 = new Person("Pepe", 18)
val p2 = new Person("Luis", 25)

println(p1.isAdult)
println(p2.isAdult)
// Exiting paste mode, now interpreting.

true
true
defined class Person
p1: Person = Person@592c6916
p2: Person = Person@d30941d
```
Una cosa curiosa es que como trabajan las cadenas de caracteres, cuando usamos un string este queda en algun sition de la JVM, cuando volvemos a usar un string, la JVM recicla esta cadena de caracteres, si queremos forzar el uso de una nueva instancia, es necesario utilizar el especificador `new`:

```scala
scala> "hello".eq("hello")
res88: Boolean = true
```

```scala
scala> new String("hello").eq(new String("hello"))
<console>:36: warning: comparing a fresh object using `eq' will always yield false
       new String("hello").eq(new String("hello"))
                             ^
res91: Boolean = false
```

```scala
scala> new String("hello").equals(new String("hello"))
res92: Boolean = true
```

## Clases Abstractas

Para crear clases abstractas en Scala usamos el espcificador `abstract`:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
import java.time.LocalDate
abstract class Car(make: String, model: String, year: Int) {
  def isVintage: Boolean = LocalDate.now.getYear - year > 20
}
// Exiting paste mode, now interpreting.
defined class Car
```
Cosa curiosa es que aunque no es una clase real clase actracta no podemos instanciarla por el simple de hecho de ser abstracta.
```scala
scala> val mustang = new Car("Ford", "Mustang", 1965)
<console>:36: error: class Car is abstract; cannot be instantiated
       val mustang = new Car("Ford", "Mustang", 1965)
```
Din embargo basto con dar la implementación de lo que no halla sido definido, que en este caso es nada, asi que basta con agregar las llaves al final.
```scala
scala> val mustang = new Car("Ford", "Mustang", 1965) {}
mustang: Car = $anon$1@28085f30
```

Ahora que si definimos algun metodo, variable de manera actracta y debemos de agregar el especificador:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
class Car(make: String, model: String, year: Int) {
  def isVintage: Boolean
}
// Exiting paste mode, now interpreting.
<pastie>:35: error: class Car needs to be abstract, since method isVintage is not defined
class Car(make: String, model: String, year: Int) {
```

```scala
class Car(make: String, model: String, year: Int) {
  def isVintage: Boolean
}
```
lo mismo si se trata de un atributo

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
class Car(make: String, model: String, year: Int) {
  val isVintage: Boolean
}
// Exiting paste mode, now interpreting.
<pastie>:35: error: class Car needs to be abstract, since value isVintage is not defined
class Car(make: String, model: String, year: Int) {
``` 

Cuando creamos una implementacion de la clase abstracta pomodes hacer override de `def` con `def`, `def` con `val`, `val` con `val`, pero NO de `val` con `def`!!!

## Lazy val

```scala
scala> :paste
scala> :paste
// Entering paste mode (ctrl-D to finish)
class Demo {
  val a: Int = {
    println("evaluating a")
    10
  }
  def b: Int = {
    println("evaluating b")
    20
  }
  lazy val c: Int = {
    println("evaluating c")
    30
  }
}
val demo = new Demo
println("Una vez instanciado:")
println(demo.a)
println(demo.b)
println(demo.b)
println(demo.c)
println(demo.c)
// Exiting paste mode, now interpreting.
evaluating a
Una vez instanciado:
10
evaluating b
20
evaluating b
20
evaluating c
30
30
defined class Demo
demo: Demo = Demo@fddd909
```
## Herencia y Extensiones
Para extender una clase respecto a otra utilizamos la palabra reservada `extends`
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
abstract class Food {
    def name: String
}
abstract class Fruit extends Food
class Orange(val name: String) extends Fruit
val jaffa = new Orange("Jaffa")
// Exiting paste mode, now interpreting.
defined class Food
defined class Fruit
defined class Orange
jaffa: Orange = Orange@5d1982c6
```
## Llamando metodos y constructores de una clase super 
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
abstract class Vehicle(val name: String, val age: Int) {
  override def toString: String =
    s"$name, $age years old"
}
class Car(
  override val name: String,
  val make: String,
  val model: String,
  override val age: Int
) extends Vehicle(name, age) {
  override def toString: String =
    s"a $make $model, named ${super.toString}"
}
val mustang = new Car("Sally", "Ford", "Mustang", 50)
// Exiting paste mode, now interpreting.
defined class Vehicle
defined class Car
mustang: Car = a Ford Mustang, named Sally, 50 years old
```
## `override`

- Si `val` o `def` define una propiedad o metodo con el mismo tipo de parámetro sobre otro con el mismo nombre entonces este deber ser sobreescrito y por lo tanto `override` es mandatorio.
- Si `val` o `def` definen una propiedad o metodo que no existe en una clase superior entonces no debe ser utilizado.
- Si `val` o `def` definen una propiedad o metodo de una clase abstracta que ya fue implementado en una clase previa entonces podria o no tener el override.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
abstract class Upper {
  def blip: String
  val blop: String = "blop"
  def op(x: Int, y: Int): Int
}
class Lower extends Upper {
  override def blip: String = "blip"
  override val blop: String = "bloop"
  override def op(x: Int, y: Int): Int = x + y
  def op(x: Double, y: Double): Double = x + y
}
// Exiting paste mode, now interpreting.
defined class Upper
defined class Lower
```

## `final`
Cuando queremos evitar que alguien modifique una propiedad,  metodo o clase podemos usar la palabra reservada `final`

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
class Authority {
  final def theWord: String =
    "This is the final word on the matter!"
}
class Argumentative extends Authority {
  override def theWord: String =
    "No, it's not!"
}
// Exiting paste mode, now interpreting.
<pastie>:41: error: overriding method theWord in class Authority of type => String;
 method theWord cannot override final member
  override def theWord: String =
               ^
```
## `case` clases

Las clases de casos son clases regulares. Las case clases son apropiadas para modelar datos inmutables, estas son usadas como las estructuras de datos en C/C++, es decir, nos permiten definir un nuevo tipo que contendra el modelo de datos.

Lo minimo que requiere una case class es la palabra reservada `case class`, por ejemplo supongamos queremos hacer una collecion de objetos que mantengan la relación entre los nombres de los aeropuestos y su IATA code; recordemos que el código de aeropuertos de IATA está formado por grupos de tres letras, que designan a cada aeropuerto del mundo, asignadas por la Asociación Internacional de Transporte Aéreo (International Air Transport Association, IATA).

```scala
IATA(country: String, iata_code: String, airport_name: String, place: String, state: String)

val BHI = IATA("Argentina", "volaris")

	= Aeropuerto Comandante Espora	Bahía Blanca	Bandera de la Provincia de Buenos Aires Provincia de Buenos Aires
AEP	Aeroparque Internacional Jorge Newbery	Buenos Aires	Bandera de la Ciudad de Buenos Aires Ciudad Autónoma de Buenos Aires
EPA	Aeropuerto El Palomar	Área metropolitana de Buenos Aires	Bandera de la Provincia de Buenos Aires Provincia de Buenos Aires
EZE	Aeropuerto Internacional Ministro Pistarini	[[Área metropolitana de Buenos Aires	Bandera de la Provincia de Buenos Aires Provincia de Buenos Aires
BRC	Aeropuerto Internacional Teniente Luis Candelaria	San Carlos de Bariloche	Bandera de la Provincia del Río Negro Provincia de Río Negro
CTC	Aeropuerto Coronel Felipe Varela	San Fernando del Valle de Catamarca	Bandera de la Provincia de Catamarca Provincia de Catamarca
CRD	Aeropuerto Internacional General Enrique Mosconi	Comodoro Rivadavia	Bandera de la Provincia del Chubut Provincia del Chubut
COR	Aeropuerto Internacional Ingeniero Aeronáutico Ambrosio Taravella	Córdoba	Bandera de la Ciudad de Córdoba Provincia de Córdoba
CNQ	Aeropuerto Internacional Doctor Fernando Piragine Niveyro	Corrientes	Bandera de la Provincia de Corrientes Provincia de Corrientes
FTE	Aeropuerto Internacional Comandante Armando Tola	El Calafate	Bandera de la Provincia de Santa Cruz Provincia de Santa Cruz
EQS	Aeropuerto Internacional Brigadier General Antonio Parodi	Esquel	Bandera de la Provincia del Chubut Provincia del Chubut

```
## Modelos de Dominio
```scala

```
## 
```scala

```
## 
```scala

```
## 
```scala

```
## 
```scala

```
## 
```scala


```