package kotlinOpenScad.extension.expr

class GenericExpr(private val expression: String) : ScadExpr() {
    override fun toString() = expression
}

fun ex(expression: String): ScadExpr = GenericExpr(expression)
fun ex(num: Number): ScadExpr = GenericExpr(num.toString())