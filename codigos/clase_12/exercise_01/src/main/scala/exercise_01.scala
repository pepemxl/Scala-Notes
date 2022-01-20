/** Exercise 1
 * Par.map2 is a new higher-order function for combining the result of two parallel 
 * computations. What is its signature? 
 * Give the most general signature possible (don’t assume it works only for Int).
 * */

package exercise_01
import scala.collection.immutable._  
import scala.concurrent.Future
 // A Future represents an asynchronous computation. 
 // You can wrap your computation in a Future and when you need the result, 
 // you simply call a blocking Await.result() method on it. 
 // An Executor returns a Future. If you use the Finagle RPC system, 
 // you use Future instances to hold results that might not have arrived yet.
// A FutureTask is a Runnable and is designed to be run by an Executor
import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}
import java.util.Date


opaque type Par[A] = ExecutorService => Future[A]

object Par:


object Exercise_01 extends App{
    val ini = 1
    val end = 10000
    val myIndexedSeq: IndexedSeq[Int] = IndexedSeq.range(ini, end)
    def sum(ints: IndexedSeq[Int]): Int = { 
        if (ints.size <= 1 ){
            ints.headOption getOrElse 0
        }else{
            val (l,r) = ints.splitAt(ints.length/2)
            Par.map2(sum(l), sum(r))(_+_)
        }
    }
    println("The sum2 of mySeq is %d".format(sum(myIndexedSeq)))
}