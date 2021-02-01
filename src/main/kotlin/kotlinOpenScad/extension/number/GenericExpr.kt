package kotlinOpenScad.extension.number

class GenericExpr(private val expression: String) : ScadExpr() {
    override fun toString() = expression
}

fun ex(expression: String):ScadExpr = GenericExpr(expression)