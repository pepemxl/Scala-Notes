package section01

object Section01 extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello section 01"
}
