object MathCompanion{
    def sum(a: Int, b: Int): Int = a + b
    def getPrivateMember: Int = new MathCompanion().max
}

class MathCompanion{
    private val max = 100
}
