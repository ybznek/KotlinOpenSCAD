package kotlinOpenScad.extension.module

import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.extension.callModule

abstract class AbstractScadCustomModule(protected val name: String) {

    open fun define(scadCode: ScadCode) {
        scadCode.defineInternal()
    }

    abstract fun ScadCode.defineInternal()

    open fun genericCall(scadCode: ScadCode, vararg pair: Pair<String, Number>) {
        scadCode.callModule(name, scadCode._buildParams(*pair))
    }
}

fun ScadCode.genericCall(module: AbstractScadCustomModule, vararg pair: Pair<String, Number>) {
    return module.genericCall(this,*pair)
}

fun ScadCode.define(module: AbstractScadCustomModule) {
    return module.define(this)
}