# Estructuras de Control en Scala y Tipos de Datos

## Expresiones

Hay que tener cuidado con las expresiones que creamos, por ejemplo comparemos la creción de listas siguiente:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

1::2::Nil

// Exiting paste mode, now interpreting.

res0: List[Int] = List(1, 2)
```
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

true::1::2::Nil

// Exiting paste mode, now interpreting.

res1: List[AnyVal] = List(true, 1, 2)
```

Aunque `true` usualmente se maneja como si fuese un entero `1`, aqui podemos ver que tiene un efecto muy diferente en la lista creada.

Inclusive el tipo `Unit` o `()` puede acarrear inesperadas consecuencias:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

()::true::1::2::Nil

// Exiting paste mode, now interpreting.

res2: List[AnyVal] = List((), true, 1, 2)
```

## Estructura de control `if` para asignación

Hay que tener cuidado con las asignaciones, veamos la siguiente asignación

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val a = true
val b = 10
val x = if (a) a else b

// Exiting paste mode, now interpreting.

a: Boolean = true
b: Int = 10
x: AnyVal = true
```
como podemos ver la variable`x` no cuenta con el tipo de dato esperado, lo cual hará un poco más complicado el parseo en algunos casos.



## try.. catch.. finally

Asi como con `if` tenemos una expresión algo parecido sucede con `try` `catch` `finally`

en el ejemplo siguiente x puede ser un entero o puede ser un boolean, aunque al final se le asignará el tipo `AnyVal`:
```scala
val res = if (x > 0) x else false
```

en el caso del `try` `catch` `finally`, dependerá del tipo que resulte de `try` o de la excepción `catch`, mientras que los tipos que se encuentren en `finally` no afectarn al tipo retornado.

## Estructura de control While 

La forma de dejar usar while y dejarle esta tarea a scala es usar recursión, scala se encargará de detectar el patrón recursivo y convertirlo en un while para el compilador.


```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

import scala.annotation.tailrec

@tailrec
def greet(n: Int, curr: Int = 0): Unit = {
  if (curr < n) {
    println("Hello")
    greet(n, curr + 1)
  }
}

greet(5)

// Exiting paste mode, now interpreting.

Hello
Hello
Hello
Hello
Hello
import scala.annotation.tailrec
greet: (n: Int, curr: Int)Unit

```


## Extructura de control For
```scala
scala> for (i <- 1 to 10) println(i * i)
1
4
9
16
25
36
49
64
81
100
```

```scala
scala> (1 to 10).foreach(i => println(i * i))
1
4
9
16
25
36
49
64
81
100
```

```scala
scala> for (i <- 1 to 3; j <- 1 to 3) println(i * j)
1
2
3
2
4
6
3
6
9
```

```scala
scala> (1 to 3).foreach(i => (1 to 3).foreach(j => println(i * j)))
1
2
3
2
4
6
3
6
9
```

### Usando llaves tenemos una versión más leible de la estructura de control `for`

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

for {
  i <- 1 to 3
  j <- 1 to 3
} {
  println(i * j)
}

// Exiting paste mode, now interpreting.

1
2
3
2
4
6
3
6
9
```

### Ahora el for como expresión:


```scala
scala> for (i <- 1 to 10) yield i
res9: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

```scala
scala> (1 to 10).map(i => i )
res10: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

```scala
scala> for (i <- 1 to 3; j <- 1 to 3) yield i * j
res11: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 2, 4, 6, 3, 6, 9)
```

```scala
scala> (1 to 3).flatMap(i => (1 to 3).map(j => i * j))
res12: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 2, 4, 6, 3, 6, 9)
```

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

for {
  i <- 1 to 3
  j <- 1 to 3
  k <- 1 to 3
} yield {
  i * j * k
}

// Exiting paste mode, now interpreting.

res14: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 2, 4, 6, 3, 6, 9, 2, 4, 6, 4, 8, 12, 6, 12, 18, 3, 6, 9, 6, 12, 18, 9, 18, 27)
```

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

(1 to 3).flatMap(i => (1 to 3).flatMap(j => (1 to 3).map(k => i * j * k)))

// Exiting paste mode, now interpreting.

res13: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 2, 3, 2, 4, 6, 3, 6, 9, 2, 4, 6, 4, 8, 12, 6, 12, 18, 3, 6, 9, 6, 12, 18, 9, 18, 27)
```


## Las 4 Gs de For

- Generator: Todos los generadores en el mismo bloque perteneciente a un `for` deben ser del mismo tipo!
- Guard: Una expresion if dentro de las llaves  de un `for` para la correcta ejecución del `for`.
- inline assiGnment: 
- Give: 


Vamos a Revisar el siguiente ejemplo, primero leemos algunos archivos y creamos una función auxiliar para leer las lineas de un archivo:

```scala
import java.io.File

val fileLoc = new File(getClass.getClassLoader.getResource("").toURI)
val filesHere = new File(fileLoc.getParentFile, ".").listFiles()

def fileLines(f: File) = {
  io.Source.fromFile(f).getLines()
}
```


Ahora un parser de estos archivos podría ser el siguiente:

```scala
val forLineLengths =
  for {
    file <- filesHere
    if file.getName.endsWith(".sc")
    line <- fileLines(file)
    trimmed = line.trim
    if trimmed.matches(".*for.*")
  } yield trimmed.length
```
lo cual es equivalente al siguiente código

```scala
filesHere.filter(_.getName.endsWith(".sc")).flatMap { file =>
  fileLines(file).filter(_.trim.matches(".*for.*")).map { line =>
    line.trim.length
  }
}
```


## Más de For

El `for` en Scala no solo itera sobre colecciones si no que es capaz de trabajar con futures, dandonos mecanismos para trabajar de manera asincrona mucho más naturalmente.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

import scala.concurrent._
import duration._
import ExecutionContext.Implicits.global

val f1 = Future(1.0)
val f2 = Future(2.0)
val f3 = Future(3.0)

val f4 = for {
  v1 <- f1
  v2 <- f2
  v3 <- f3
} yield v1 + v2 + v3

// Exiting paste mode, now interpreting.

import scala.concurrent._
import duration._
import ExecutionContext.Implicits.global
f1: scala.concurrent.Future[Double] = Future(<not completed>)
f2: scala.concurrent.Future[Double] = Future(Success(2.0))
f3: scala.concurrent.Future[Double] = Future(Success(3.0))
f4: scala.concurrent.Future[Double] = Future(<not completed>)
```

lo tuve que separar aun no se por que, me queda pendiente revisar esto!!!

```scala
scala> Await.result(f4, 10.seconds)
res29: Double = 6.0
```

## Expresiones Regulares

En Scala `match` funciona como un `switch` de JAVA/C++, pero tambien nos deja crear expresiones.

Como enunciado lo usariamos:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val x = 1

x match {
  case 1 => println("it's one")
  case 2 => println("it's two")
  case _ => println("it's something else")
}
// Exiting paste mode, now interpreting.

it's one
x: Int = 1
```

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
val res = x match {
  case 1 => "one"
  case 2 => "two"
  case _ => "something else"
}
// Exiting paste mode, now interpreting.
res: String = one
```

```scala
val n = -1

n match {
  case 0 => "It's zero"
  case v if n > 0 => s"It's positive $v"
  case v => s"It's negative ${v.abs}"
}
```

Debemos tener cuidado con la expresión `match` si poir alguna razón no entra en ningún caso lanza una excepción!!!

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def matchIt(x: Any): String = x match {
  case "Hello" => "Well, hello back"
  case 1 :: rest => s"A list beginning with 1, rest is $rest"
  case Nil => "The empty list"
  case 5 => "The number 5"
  case _: List[_] => "Some kind of list, not empty and not starting with 1"
}
matchIt(2.0)

// Exiting paste mode, now interpreting.

scala.MatchError: 2.0 (of class java.lang.Double)
  at .matchIt(<pastie>:30)
  ... 36 elided
```

para evitar esto, se agrega un caso por defecto:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def matchIt(x: Any): String = x match {
  case "Hello" => "Well, hello back"
  case 1 :: rest => s"A list beginning with 1, rest is $rest"
  case Nil => "The empty list"
  case 5 => "The number 5"
  case _: List[_] => "Some kind of list, not empty and not starting with 1"
  case _=> "Cualquier cosa"
}
matchIt(2.0)
matchIt(5)
matchIt(List(1,2,3))
matchIt(List(1))
matchIt(List(2,3))
matchIt(Nil)

// Exiting paste mode, now interpreting.

matchIt: (x: Any)String
res32: String = The empty list
```

## Interpolación de cadenas de carácteres

```scala
val x = 10
val y = 2.12
val name = "Fred"
```

### Sustituyendo valores usando el prefijo `s`
```scala
s"$name $x $y"
```
```scala
s"$name is ${x * y}"
```
### String con formato usando ekl prefijo `f`
```scala
f"$name is ${x * y}%08.4f"
```
Aqui fallará por que no tenemos una variable `names`
```scala
s"$names"
```
```scala
s"${name}s"
```

### Strings raw usando el prefijo `raw`

```scala
"\t\n"
```
```scala
raw"\t\n"
```
### Triple quotes

```scala
""" Nos permite manejar caracteres especiales como "comillas" \t\n"""
```

