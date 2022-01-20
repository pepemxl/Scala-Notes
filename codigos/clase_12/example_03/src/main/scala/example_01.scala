package example_01
import scala.collection.immutable._  

/**
 * foldLeft method takes associative biinary operator function as parameter and will use it
 * to collapse elements from the collection. The order for tranversing the elemeents in the
 * collection is from left to right. Usually this methos permit you to specify an initial 
 * value.
 * One of the problems of use foldLeft is that is hard to parallelized. However we can think
 * in some ways to do this.
 * */

object Example_01 extends App{
    def sum(ints: Seq[Int]): Int = { //Seq is a superclass of list, then it has foldLeft method
        ints.foldLeft(0)((a,b) => a+b)
    }
    val mySeq:Seq[Int] = Seq(1,2,3,4,5,6,7,8,9,10)
    println("The sum of mySeq is %d".format(sum(mySeq)))
    // IndexedSeq is a superclass of random-access sequences like Vector in the STL.
    // Unlike list, these sequences provide an efficient splitAt method
    val myIndexedSeq: IndexedSeq[Int] = IndexedSeq(1,2,3,4,5,6,7,8,9,10)
    def sum2(ints: IndexedSeq[Int]): Int = { 
        if (ints.size <= 1 ){
            ints.headOption getOrElse 0
        }else{
            val (l,r) = ints.splitAt(ints.length/2)
            sum2(l) + sum2(r)
        }
    }
    println("The sum2 of mySeq is %d".format(sum2(myIndexedSeq)))
}