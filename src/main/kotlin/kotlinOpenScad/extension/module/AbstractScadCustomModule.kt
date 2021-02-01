package kotlinOpenScad.extension.module

import kotlinOpenScad.core.ScadModuleBuilder
import kotlinOpenScad.extension.call

abstract class AbstractScadCustomModule(private val name: String) {

    open fun define(scadModuleBuilder: ScadModuleBuilder) {
        scadModuleBuilder.defineInternal()
    }

    abstract fun ScadModuleBuilder.defineInternal()

    open fun genericCall(scadModuleBuilder: ScadModuleBuilder, vararg pair: Pair<String, Number>) {
        scadModuleBuilder.call(name, scadModuleBuilder._buildParams())
    }
}

fun ScadModuleBuilder.genericCall(module: AbstractScadCustomModule, vararg pair: Pair<String, Number>) {
    return module.genericCall(this,*pair)
}

fun ScadModuleBuilder.define(module: AbstractScadCustomModule) {
    return module.define(this)
}