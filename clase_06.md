# Funciones y Closures

## Metodos Privados

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

import scala.annotation.tailrec

object FactSeq {

  def factSeq(n: Int): List[Long] = {
    factSeqInner(n, List(1L), 2)
  }

  @tailrec
  private def factSeqInner(n: Int, acc: List[Long], ct: Int): List[Long] = {
    if (ct > n) acc else factSeqInner(n, ct * acc.head :: acc, ct + 1)
  }
}

FactSeq.factSeq(8)

// Exiting paste mode, now interpreting.

import scala.annotation.tailrec
defined object FactSeq
res34: List[Long] = List(40320, 5040, 720, 120, 24, 6, 2, 1)
```


## Metodos Anidados

Una manera de escoder metodos es simplemente ponerlos dentro del metodo, esto los hace por defecto privados!

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

object FactSeqNested {

  def factSeq(n: Int): List[Long] = {
    @tailrec
    def factSeqInner(n: Int, acc: List[Long], ct: Int): List[Long] = {
      if (ct > n) acc else factSeqInner(n, ct * acc.head :: acc, ct + 1)
    }

    factSeqInner(n, List(1L), 2)
  }
}

FactSeqNested.factSeq(8)

// Exiting paste mode, now interpreting.

defined object FactSeqNested
res35: List[Long] = List(40320, 5040, 720, 120, 24, 6, 2, 1)
```

Como dentro de este scope algunas variables usadas por los submetodos son constantes podemos omitirlos como entradas del submetodo, por ejemplo la `n` en `factSeqInner`.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

object FactSeqScoped {
  def factSeq(n: Int): List[Long] = {
    @tailrec
    def factSeqInner(acc: List[Long], ct: Int): List[Long] = {
      if (ct > n) acc else factSeqInner(ct * acc.head :: acc, ct + 1)
    }
    factSeqInner(List(1L), 2)
  }
}

FactSeqScoped.factSeq(8)

// Exiting paste mode, now interpreting.

defined object FactSeqScoped
res36: List[Long] = List(40320, 5040, 720, 120, 24, 6, 2, 1)
```

## Funciones Literales
- Una función literal o lambda es una función que podría no tener nombre.

```scala
scala> def multiplyMethod(a: Int, b: Int): Int = a * b
multiplyMethod: (a: Int, b: Int)Int

scala> multiplyMethod(2, 3)
res38: Int = 6
```

```scala
scala> val multiplyFunction: (Int, Int) => Int = (a, b) => a * b
multiplyFunction: (Int, Int) => Int = $Lambda$1480/1092243752@6006c7e2

scala> multiplyFunction(2, 3)
res40: Int = 6
```

Para definir una función basta con definir lo siguiente:
```scala
scala> (a: Int, b: Int) => a*b
res42: (Int, Int) => Int = $Lambda$1482/1531715833@50b40052
```

Definiendo:

```scala
scala> val mult = (a: Int, b: Int) => a*b
mult: (Int, Int) => Int = $Lambda$1483/1713254040@47a9b104
```

ya podemos usar esta función exactamente como usariamos un metodo:

```scala
scala> mult(1,2)
res43: Int = 2
```

Ejemplos donde es util usar funciones literales/lambdas:
```scala
scala> val nums = (1 to 5).toList
nums: List[Int] = List(1, 2, 3, 4, 5)
```

```scala
scala> nums.map(x => x * x)
res44: List[Int] = List(1, 4, 9, 16, 25)
```

```scala
scala> nums.map(x => x * 3)
res45: List[Int] = List(3, 6, 9, 12, 15)
```

```scala
scala> nums.map(x => x % 2 == 0)
res46: List[Boolean] = List(false, true, false, true, false)
```

Cuandoe scribimos una función lambda

```scala
scala> val fn1: (Int, Int) => Int = (a, b) => a + b
fn1: (Int, Int) => Int = $Lambda$1487/206044501@19a5c643
```
realmente estamos escribiendo una función con tres parámetros y sobreescibiendo el metodo `apply`, por lo cual podemos simplemente evaluarla pasando los parámetros

```scala
scala> fn1(1,2)
res47: Int = 3
```
o mandando llamar el metodo `apply`
```scala
scala> fn1.apply(1,2)
res48: Int = 3
```
otra manera es mandar llamar la función helper `Function2` que como el nombre lo indica ayuda a definir una función que recibe dos parámetros.

En el siguiente ejemplo indica que la función recibira dos enteros y regresará un entero:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val fn2 = new Function2[Int, Int, Int] {
  override def apply(a: Int, b: Int) = a + b
}

// Exiting paste mode, now interpreting.

fn2: (Int, Int) => Int = <function2>
```
y podemos usar como cualquier otra función literal que hallamos definido antes

```scala
scala> fn2(1, 2)
res49: Int = 3
```
```scala
scala> fn2.apply(1, 2)
res50: Int = 3
```
además del metodo apply tenemos otros metodos que son creados al momento de definir una función literal como es el caso de 
- curried: Definen una secuencia de subfunciones de un parámetro
- tupled: Crea una versión de la función que recibe tuplas
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val fn1curried = fn1.curried
fn1curried(1)(2)

// Exiting paste mode, now interpreting.

fn1curried: Int => (Int => Int) = scala.Function2$$Lambda$1529/1173320708@4360b933
res52: Int = 3
```
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val fn1tupled = fn1.tupled
val tup = (1, 2)
fn1tupled(tup)

// Exiting paste mode, now interpreting.

fn1tupled: ((Int, Int)) => Int = scala.Function2$$Lambda$215/680988889@72de5a2c
tup: (Int, Int) = (1,2)
res53: Int = 3
```
## Funciones de Orden Superior

Scala permite la definición de funciones de orden superior. Estas funciones son las que toman otras funciones como parámetros, o las cuales el resultado es una función. 

Creemos una lista de datos:
```scala
scala> val nums = (1 to 10).toList
nums: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```
al aplicar la función de orden superior `map` a la función `x => x*x` tenemos una lista de los cuadrados de la lista original:
```scala
scala> nums.map(x => x * x)
res55: List[Int] = List(1, 4, 9, 16, 25, 36, 49, 64, 81, 100)
```
al aplicar la función de orden superior `filter` a la función `x => x < 4` tenemos una lista con todos los numeros que cumplen la condición:
```scala
scala> nums.filter(x => x < 4)
res56: List[Int] = List(1, 2, 3)
```
al aplicar la función de orden superior `span` a la función `x => x % 4 != 0` tenemos una serie de listas que se crean cada vez que se incumple la condión:
```scala
scala> nums.span(x => x % 4 != 0)
res57: (List[Int], List[Int]) = (List(1, 2, 3),List(4, 5, 6, 7, 8, 9, 10))
```
al aplicar la función de orden superior `span` a la función `x => x % 4 != 0` tenemos un par de listas que se crean una donde sus elementos cumplen con la condición y otra donde no:
```scala
scala> nums.partition(x => x % 4 != 0)
res58: (List[Int], List[Int]) = (List(1, 2, 3, 5, 6, 7, 9, 10),List(4, 8))
```

Tambien podemos crear nuestras propiasn funciones de orden superior:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def compareNeighbors(xs: List[Int], compare: (Int, Int) => Int): List[Int] = {
  for (pair <- xs.sliding(2)) yield {
    compare(pair(0), pair(1))
  }
}.toList

compareNeighbors(nums, (a, b) => a + b)

// Exiting paste mode, now interpreting.

compareNeighbors: (xs: List[Int], compare: (Int, Int) => Int)List[Int]
res59: List[Int] = List(3, 5, 7, 9, 11, 13, 15, 17, 19)
```

```scala
scala> compareNeighbors(nums, (a, b) => a*5+b)
res60: List[Int] = List(7, 13, 19, 25, 31, 37, 43, 49, 55)
```
Con esto ahora tenemos una manera efectiva de crear funciones de orden superior y con ello un poco más de programación funcional.

## Sugar Sintaxis

Ahora en lugar de usar la notación anterior en la creación de la función de orden superior `(Int, Int) => Int`  podemos usar la notación `Function2[Int, Int, Int]`.

```scala
def compareNeighbors(xs: List[Int], compare: Function2[Int, Int, Int]): List[Int] = {
  for (pair <- xs.sliding(2)) yield {
    compare(pair(0), pair(1))
  }
}.toList
```
Volvamos al ejemplo de `map`, `filter`, `span` y `partition`

```scala
scala> val nums = (1 to 10).toList
nums: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
```

En lugar de filtrar con `(x => x < 4)` basta con `_ < 4`:
```scala
scala> nums.filter(_ < 4)
res61: List[Int] = List(1, 2, 3)
```

```scala
scala> nums.span(_ % 4 != 0)
res62: (List[Int], List[Int]) = (List(1, 2, 3),List(4, 5, 6, 7, 8, 9, 10))
```

```scala
scala> nums.partition(_ % 4 != 0)
res63: (List[Int], List[Int]) = (List(1, 2, 3, 5, 6, 7, 9, 10),List(4, 8))
```
El problema viene con la función de orden superior `map`:
```scala
scala> nums.map(_ * _)
<console>:32: error: missing parameter type for expanded function ((x$1: <error>, x$2) => x$1.$times(x$2))
       nums.map(_ * _)
                ^
<console>:32: error: missing parameter type for expanded function ((x$1: <error>, x$2: <error>) => x$1.$times(x$2))
       nums.map(_ * _)
```
Aqui nos dice que no podemos usar de nuevo el comodin para representar la variable que se le esta pasando, si quisieramos obtener una sintaxis equivalente, loq ue necesitamos es crear nuestra propia función de orden superior:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def squareArg(xs: List[Int], compare: (Int, Int) => Int): List[Int] = {
    for (pair <- xs.sliding(1)) yield {
         compare(pair(0), pair(0))
    }
}.toList

squareArg(nums, _ * _)

// Exiting paste mode, now interpreting.

squareArg: (xs: List[Int], compare: (Int, Int) => Int)List[Int]
res67: List[Int] = List(1, 4, 9, 16, 25, 36, 49, 64, 81, 100)
```

## Aplicación Parcial de Funciones

Ahora lo que haremos sera definir una función `add3Nums` que sumará tres enteros, despupés una función que manda llamar esta funcion, pero está vez solo tiene una variable!!!

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val add3Nums = (a: Int, b: Int, c: Int) => a + b + c

val add1and3 = add3Nums(1, _: Int, 3)

add1and3(2)

// Exiting paste mode, now interpreting.

add3Nums: (Int, Int, Int) => Int = $Lambda$1564/291620839@6a416c63
add1and3: Int => Int = $Lambda$1565/938798498@63b2f300
res68: Int = 6
```
Podemos hacer esto con un metodo:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

def add3Method(a: Int, b: Int, c: Int) = a + b + c

val add1and3 = add3Method(1, _: Int, 3)

add1and3(2)

// Exiting paste mode, now interpreting.

add3Method: (a: Int, b: Int, c: Int)Int
add1and3: Int => Int = $Lambda$1566/627420474@6834e9ec
res69: Int = 6
```
Notemos que en este caso el tipo de dato no es opcional, almenos que estemos replazando todos los parámetros.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val add3Functionv1 = add3Method(_, _, _)
add3Functionv1(1,2,3)

// Exiting paste mode, now interpreting.

add3Functionv1: (Int, Int, Int) => Int = $Lambda$1572/1829723062@2c276433
res70: Int = 6
```
Y una forma un poco más reducida:

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

val add3Functionv2 = add3Method _
add3Functionv2(1,2,3)

// Exiting paste mode, now interpreting.

add3Functionv2: (Int, Int, Int) => Int = $Lambda$1576/446445869@14d5fc19
res71: Int = 6
```

## Clousures

Todos los clousures son funciones literales, sin embargo no todas las funciones literales son clousures!

Este es un ejemplo de una función que no es un closure:
```scala
scala> val incBy1 = (x: Int) => x + 1
incBy1: Int => Int = $Lambda$1577/111508627@2ab035f5
```
Haciendo una pequeña modificación lo podemos volver un clousure:
```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)

var more = 10
val incByMore = (x: Int) => x + more

// Exiting paste mode, now interpreting.

more: Int = 10
incByMore: Int => Int = $Lambda$1578/356072597@2e118f2f
```
recordemos que un hablamos de un clousure cuando el resultado depende de algo más que el estado inicial de una variable!
En Scala podriamos usar `vars` para guardar el estados de variables, sin embargo, hay que evitar este uso!

## Funciones Parcialmente Definidas

Una función parcialmente definida $\hat{F}$ usualmente es una función que extiende a una función $F$, está puede ser usada en lugar donde sea usada la función $F$. Cuando está función $\hat{F}$ no puede ser más extendida le llamamos función completa, ya que esta definida en todo su dominio.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
val pf1: PartialFunction[Int, Int] = {
  case x: Int if x > 0 => x + x
  case x => x * -1
}
// Exiting paste mode, now interpreting.
pf1: PartialFunction[Int,Int] = <function1>
```

```scala
scala> val fn1: Int => Int = pf1
fn1: Int => Int = <function1>
```

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
val nums = (-5 to 5).toList
nums.map(pf1)
// Exiting paste mode, now interpreting.
nums: List[Int] = List(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5)
res72: List[Int] = List(5, 4, 3, 2, 1, 0, 2, 4, 6, 8, 10)
```

Ejemplo donde debemos tener cuidado

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
val pf2: PartialFunction[Int, Int] = {
  case x: Int if x > 0 => x + x
}
// Exiting paste mode, now interpreting.
pf2: PartialFunction[Int,Int] = <function1>
```
Ahora si intentamos hacer un `map` de `nums`
```scala

scala> nums.map(pf2)
scala.MatchError: -5 (of class java.lang.Integer)
  at scala.PartialFunction$$anon$1.apply(PartialFunction.scala:259)
  at scala.PartialFunction$$anon$1.apply(PartialFunction.scala:257)
  at $anonfun$1.applyOrElse(<pastie>:31)
  at $anonfun$1.applyOrElse(<pastie>:31)
  at scala.runtime.AbstractPartialFunction$mcII$sp.apply$mcII$sp(AbstractPartialFunction.scala:38)
  at scala.runtime.AbstractPartialFunction$mcII$sp.apply(AbstractPartialFunction.scala:38)
  at scala.runtime.AbstractPartialFunction$mcII$sp.apply(AbstractPartialFunction.scala:30)
  at scala.collection.immutable.List.map(List.scala:293)
  ... 28 elided
```
Cuando no tenemos la definición de todos los posibles valores del dominio en nuestra función parcial, es cuando usamos `collect`:
```scala
scala> nums.collect(pf2)
res74: List[Int] = List(2, 4, 6, 8, 10)
```

## Var Argss
En el tipo de dato agregamos un asterisco `*` esto le dice a scala que esta por recibir un numero variable de entradas en la función.

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
def sayHello(names: String*): Unit = {
  for (name <- names) print(s"$name ")
  println("")
}
sayHello()
sayHello("Hello")
sayHello("Hello", "World", "!")
// Exiting paste mode, now interpreting.
Hello
Hello World !
sayHello: (names: String*)Unit
```

para poder pasar una lista guardada en una variable basta con agregar `:_*` que le dice a scala que debe expander la secuencia que le debemos y mandarsela a la funciónq ue espera dicha lista de argumentos.

`_*` se conoce como el operador expansión.



## Parámetros de una funcion

```scala 
scala> :paste
// Entering paste mode (ctrl-D to finish)
def f1(x: Int, y:Int = 2): Int = {
    x*2+y
}
println(f1(x=1,y=2))
println(f1(y=2,x=1))
println(f1(1))
// Exiting paste mode, now interpreting.
4
4
4
f1: (x: Int, y: Int)Int
```











