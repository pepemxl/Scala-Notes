package example_02
import scala.collection.immutable._  

/**
 * In order to create an object that permite parallelize our transversing functions we will
 * create a new object capable to contain partial results. This will be Par[A] with the next
 * to methods
 * - def unit[A](a: => A): Par[A], takes A and return a computation
 * - def get[A](a: => Par[A]: A), to extract the resulting value from a parallel computation
 * one of them contains some kind of references to the thread while the other permit us to retrieve
 * the final value once the thread finished.
 * */
trait Runnable { def run: Unit }

class Thread(r: Runnable) {
    def start: Unit // Begins running r in a separate thread
    def join: Unit // Blocks the calling thread r until finishes running
}

class ExecutorService {
    def submit[A](a: Callable[A]): Future[A]
}
trait Future[A] {
    def get: A
}

object Example_02 extends App{
    val ini = 1
    val end = 10000
    val myIndexedSeq: IndexedSeq[Int] = IndexedSeq.range(ini, end)
    def sum(ints: IndexedSeq[Int]): Int = { 
        if (ints.size <= 1 ){
            ints.headOption getOrElse 0
        }else{
            val (l,r) = ints.splitAt(ints.length/2)
            val sumL: Par[Int] = Par.unit(sum(l)) // computes in parallel
            val sumR: Par[Int] = Par.unit(sum(r)) // computes in parallel
            //Par.get(sumL) + Par.get(sumR) // extract and sum
            Par.get(Par.unit(sum(l))) + Par.get(Par.unit(sum(r))) // extract and sum
        }
    }
    println("The sum of myIndexedSeq is %d".format(sum(myIndexedSeq)))
}