package kotlinOpenScad.extension.number

abstract class ScadExpr : Number() {
    override fun toByte() = throwScadNumberException()
    override fun toChar() = throwScadNumberException()
    override fun toDouble() = throwScadNumberException()
    override fun toFloat() = throwScadNumberException()
    override fun toInt() = throwScadNumberException()
    override fun toLong() = throwScadNumberException()
    override fun toShort() = throwScadNumberException()
    private fun throwScadNumberException(): Nothing = throw IllegalStateException("Scad expression cannot be converted to numeric value")

    operator fun plus(second: Number): ScadExpr = op("+", second)
    operator fun minus(second: Number): ScadExpr = op("-", second)
    operator fun times(second: Number): ScadExpr = op("*", second)
    operator fun div(second: Number): ScadExpr = op("/", second)
    infix fun lt(second: Number): ScadExpr = op("<", second)
    infix fun lte(second: Number): ScadExpr = op("<=", second)
    infix fun eq(second: Number): ScadExpr = op("==", second)
    infix fun gt(second: Number): ScadExpr = op(">", second)
    infix fun gte(second: Number): ScadExpr = op(">=", second)

    private fun op(op: String, second: Number): ScadExpr {
        return GenericExpr("(${this} $op ${second})")
    }
}