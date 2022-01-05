import org.scalatest.funsuite._

class HelloSpec extends AnyFunSuite {
  test("Hola Scala Mundo empieza con H") {
    assert("Hola Scala Mundo".startsWith("H"))
  }
}