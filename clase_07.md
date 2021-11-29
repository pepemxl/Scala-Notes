# Estructuras de Control Customizadas

Lo más parecido a templates que he encontrado hasta el momento, sería crear la siguiente estructura:

```scala
def mifuncion[A](a: Int, fn:String => A, default: A): A = {

}

```

donde A es un tipo que puede ser inferido.

Esta estructura nos dice basicamente que podemos crear wrappers de funciones usando esta estructura particular.


## Currificación (Currying)
Esta palabra hace alución a que podemos transformar una función que utiliza multiples(más de uno) argumentos en una secuencia de funciones que solo usan un argumento!


Dada una función

$f: X\times Y \longrightarrow Z$ puede transformarse en un conjunto de funciones $f_{Y}:X\longrightarrow Z$

dicho de otra manera estamos creando funciones $f_{y}\, \forall\, y  \in Y$, de tal manera que:

$f(x, y) \longmapsto f_{y}(x)$

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val add3: (Int, Int, Int) => Int = (a, b, c) => a + b + c
val add3curried = add3.curried
val add3c: Int => Int => Int => Int = a => b => c => a + b + c
println(add3(1,2,3))
println(add3curried(1)(2)(3))
println(add3c(1)(2)(3))

// Exiting paste mode, now interpreting.

6
6
6
add3: (Int, Int, Int) => Int = $Lambda$1605/1243535773@23123696
add3curried: Int => (Int => (Int => Int)) = scala.Function3$$Lambda$1606/1348705573@1f3bb1f8
add3c: Int => (Int => (Int => Int)) = $Lambda$1607/544028511@110279cc

```
```scala
scala> add3c.apply(1).apply(2).apply(3)
res79: Int = 6
```


```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def add3method(a: Int)(b: Int)(c: Int) = a + b + c
println(add3method(1)(2)(3))

println(add3method { 1 } { 2 } { 3 })

// Exiting paste mode, now interpreting.

6
6
add3method: (a: Int)(b: Int)(c: Int)Int
```

## Creando Loops funcionalmente

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

import scala.annotation.tailrec

@tailrec
def myFunctionalLoop(pred: () => Boolean)(body: () => Unit): Unit = {
  if (pred()) {
    body()
    myFunctionalLoop(pred)(body)
  }
}

// Exiting paste mode, now interpreting.

import scala.annotation.tailrec
myFunctionalLoop: (pred: () => Boolean)(body: () => Unit)Unit
```

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

var x = 0

myFunctionalLoop(() => x < 5) { () =>
  println(x * x)
  x += 1
}

// Exiting paste mode, now interpreting.

0
1
4
9
16
x: Int = 5
```

Ahora para escribirlo un poco más limpio o almenos sencillo de recordar:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

@tailrec
def myFunctionalLoop2(pred: => Boolean)(body: => Unit): Unit = {
  if (pred) {
    body
    myFunctionalLoop2(pred)(body)
  }
}
var y = 0
myFunctionalLoop2(y < 5) {
  println(y * y)
  y += 1
}

// Exiting paste mode, now interpreting.

0
1
4
9
16
myFunctionalLoop2: (pred: => Boolean)(body: => Unit)Unit
y: Int = 5
```

```scala

```

```scala

```