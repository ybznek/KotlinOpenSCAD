package kotlinOpenScad.extension.expr

object VariableGenerator {
    var prefix = "__"

    private var index = 0

    fun get(): String {
        return "$prefix${index++}"
    }

    fun getExpr(): ScadExpr {
        return ex(get())
    }
}