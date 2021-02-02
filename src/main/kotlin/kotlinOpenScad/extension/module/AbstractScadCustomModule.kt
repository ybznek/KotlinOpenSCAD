package kotlinOpenScad.extension.module

import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.extension.callModule

abstract class AbstractScadCustomModule(protected val name: String) {

    open fun define(scadCode: ScadCode) {
        scadCode.defineInternal()
    }

    abstract fun ScadCode.defineInternal()

    open fun call(scadCode: ScadCode, vararg pair: Pair<String, Number>) {
        scadCode.callModule(name, scadCode._buildParams(*pair))
    }
}

fun ScadCode.call(module: AbstractScadCustomModule, vararg pair: Pair<String, Number>) {
    return module.call(this,*pair)
}

fun ScadCode.define(module: AbstractScadCustomModule) {
    return module.define(this)
}