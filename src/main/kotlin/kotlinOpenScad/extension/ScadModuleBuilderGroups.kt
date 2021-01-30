package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModule
import kotlinOpenScad.core.ScadModuleBuilder

fun ScadModuleBuilder.build(name: String, function: ScadModuleBuilder.() -> Unit): ScadModule {
    val scadModule = ScadModule(name, _scadBuilder)
    function(this)
    return scadModule
}

fun ScadModuleBuilder.union(func: ScadModuleBuilder.() -> Unit) {
    _buildGroup("union()", func)
}

fun ScadModuleBuilder.difference(func: ScadModuleBuilder.() -> Unit) {
    _buildGroup("difference()", func)
}

fun ScadModuleBuilder.intersection(func: ScadModuleBuilder.() -> Unit) {
    _buildGroup("intersection()", func)
}
