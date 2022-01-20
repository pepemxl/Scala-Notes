# Estructuras de datos funcionales

Una estructura de datos es usada solo a través de funciones puras. Por lo tanto las **estructuras de datos funcionales son inmutables**!!!

La lista vacia `List()` o `Nil` es tan inmutable como cualquier otra constante. 


```scala 
scala> :paste
// Entering paste mode (ctrl-D to finish)
sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head:A, tail: List[A]) extends List[A]

object List {
    def sum(ints: List[Int]): Int = ints match {
        case Nil => 0
        case Cons(x, xs) => x + sum(xs)
    }

    def product(ds: List[Double]): Double = ds match {
        case Nil => 1.0
        case Cons(0.0, _) => 0.0
        case Cons(x, xs) => x*product(xs)
    }

    def apply[A](as: A*): List[A] = if (as.isEmpty) Nil else Cons(as.head, apply(as.tail: _*))
}

// Exiting paste mode, now interpreting.

trait List
object Nil
class Cons
object List
```



val x = List(1,2,3,4,5) match {
case Cons(x, Cons(2, Cons(4, _))) => x
case Nil => 42
case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y case Cons(h, t) => h + sum(t)
case _ => 101
}



def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f)) 
}