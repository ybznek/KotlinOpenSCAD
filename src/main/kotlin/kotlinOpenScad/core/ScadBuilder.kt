package kotlinOpenScad.core

import kotlinOpenScad.extension.build

class ScadBuilder {
    private val b = StringBuilder()
    var indentation = 0

    override fun toString(): String {
        return b.toString()
    }

    fun module(name: String, fn: Int = 100, function: ScadModuleBuilder.() -> Unit): ScadModule {
        b.appendLine("module ${name}(\$fn=$fn) {")
        lateinit var module: ScadModule
        indent {
            module = ScadModuleBuilder(this).build(name, function)
        }
        b.appendLine("}")
        return module
    }

    fun addModuleCall(name: String, fn: Double?) {
        if (fn == null) {
            b.append("$name();")
        } else {
            b.append("$name(\$fn=${fn});")
        }

    }

    fun appendLine(str: String) {
        b.appendLine("\t".repeat(indentation) + str)
    }

    fun append(str: String) {
        b.append(str)
    }

    inline fun indent(f: () -> Unit) {
        indentation++
        f()
        indentation--
    }
}