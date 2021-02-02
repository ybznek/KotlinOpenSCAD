package kotlinOpenScad.core

@Suppress("FunctionName", "PropertyName") // functions with "_" in prefix are public for extension
class ScadCode(val _scadBuilder: ScadBuilder, private val modifier: String? = null) {

    public fun _createCleanScope() = ScadCode(_scadBuilder)
    val _readableModifier: String
        get() {
            if (modifier == null)
                return ""
            return "$modifier "
        }

    fun _buildGroup(def: String, func: ScadCode.() -> Unit) {
        val finalMod = "$_readableModifier $def".trim()
        _scadBuilder.appendLine("$finalMod {")
        _scadBuilder.indent {
            func(_createCleanScope())
        }
        _scadBuilder.appendLine("}")
    }

    fun command(str: String) {
        _scadBuilder.appendLine("${_readableModifier}$str;")
    }

    fun of(func: ScadCode.() -> Unit) {
        return _buildGroup("", func)
    }

    fun _extend(newModifier: String): ScadCode {
        return ScadCode(_scadBuilder, modifier = "$_readableModifier $newModifier".trim())
    }

    fun _buildParams(vararg params: Pair<String, Any?>): String {
        if (params.isEmpty())
            return ""

        return params
            .filter { x -> x.second != null }
            .joinToString(separator = ", ") { (name, value) -> "${name}=${prepareArg(value)}" }
    }

    fun _buildParamsArray(vararg params: Any?): String {
        return params.joinToString(
            separator = ", ",
            prefix = "[",
            postfix = "]",
            transform = { value -> "${prepareArg(value)}" }
        )
    }

    fun include(path: String) {
        _scadBuilder.appendLine("include <$path>")
    }

    private fun prepareArg(value: Any?): Any? {
        return when (value) {
            is Float -> {
                val longVal = value.toLong()
                when (value) {
                    longVal.toFloat() -> longVal
                    else -> value
                }
            }
            is Double -> {
                val longVal = value.toLong()
                when (value) {
                    longVal.toDouble() -> longVal
                    else -> value
                }
            }
            is Enum<*> -> _quote(value.toString())
            else -> value
        }
    }

    fun _quote(value: String) = "\"${value.replace(""""""", """\"""")}\""

    fun _prepareText(text: Any): String {
        return when (text) {
            is Number -> "str($text)"
            is String -> _quote(text)
            else -> _quote(text.toString())
        }
    }
}
