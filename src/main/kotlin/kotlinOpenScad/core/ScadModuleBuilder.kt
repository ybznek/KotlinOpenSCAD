package kotlinOpenScad.core

@Suppress("FunctionName", "PropertyName") // functions with "_" in prefix are public for extension
class ScadModuleBuilder(val _scadBuilder: ScadBuilder, private val modifier: String? = null) {

    private fun createCleanBuilder() = ScadModuleBuilder(_scadBuilder)
    val _readableModifier: String
        get() {
            if (modifier == null)
                return ""
            return "$modifier "
        }

    fun _buildGroup(def: String, func: ScadModuleBuilder.() -> Unit) {
        _scadBuilder.appendLine("$def {")
        _scadBuilder.indent {
            func(createCleanBuilder())
        }
        _scadBuilder.appendLine("}")
    }

    fun of(func: ScadModuleBuilder.() -> Unit) {
        return _buildGroup(_readableModifier, func)
    }

    fun _extend(newModifier: String): ScadModuleBuilder {
        return ScadModuleBuilder(_scadBuilder, modifier = "${_readableModifier}$newModifier")
    }

    fun _buildParams(vararg params: Pair<String, Any?>): String {
        if (params.isEmpty())
            return ""

        return params
            .filter { x -> x.second != null }
            .joinToString(separator = ", ") { (name, value) -> "${name}=${tryCastToInt(value)}" }
    }

    fun _buildParamsArray(vararg params: Any?): String {
        return when (params.size) {
            0 -> "[]"
            2 -> "[${tryCastToInt(params[0])}, ${tryCastToInt(params[1])}]"
            3 -> "[${tryCastToInt(params[0])}, ${tryCastToInt(params[1])}, ${tryCastToInt(params[2])}]"
            else -> params.joinToString(
                separator = ", ",
                prefix = "[",
                postfix = "]"
            ) { value -> "${tryCastToInt(value)}" }
        }

    }

    private fun tryCastToInt(value: Any?): Any? {
        return when (value) {
            is Float -> {
                val intVal = value.toInt()
                when (value) {
                    intVal.toFloat() -> intVal
                    else -> value
                }
            }
            is Double -> {
                val intVal = value.toInt()
                when (value) {
                    intVal.toDouble() -> intVal
                    else -> value
                }
            }
            else -> value
        }
    }


}
