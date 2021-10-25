import java.util
import scala.jdk.CollectionConverters._

object WithJava extends App {
    val numbersInJava: util.List[Int] = util.Arrays.asList(1,2,3,4,5)
    val numbersInScala: scala.collection.mutable.Buffer[Int] = numbersInJava.asScala
    numbersInScala.foreach(number => println(number))
}