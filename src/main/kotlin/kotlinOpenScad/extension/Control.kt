package kotlinOpenScad.extension

import ScadModuleName
import kotlinOpenScad.core.ScadModuleBuilder

private val NOOP: ScadModuleBuilder.() -> Unit = { }

fun ScadModuleBuilder.IF(
    expression: String,
    then: ScadModuleBuilder.() -> Unit = NOOP,
    otherwise: ScadModuleBuilder.() -> Unit = NOOP,
) {
    _scadBuilder.appendLine("$_readableModifier if ($expression) {")
    _createCleanBuilder().then()
    if (otherwise !== NOOP) {
        _scadBuilder.appendLine("} else {")
        _createCleanBuilder().otherwise()
    }
    _scadBuilder.appendLine("}")
}

fun ScadModuleBuilder.FOR(
    expression: String,
    body: ScadModuleBuilder.() -> Unit = NOOP,
) {
    _scadBuilder.appendLine("$_readableModifier for ($expression)")
    _scadBuilder.appendLine("{")
    _createCleanBuilder().body()
    _scadBuilder.appendLine("}")
}


fun ScadModuleBuilder.MODULE(
    name: String,
    params: String = "",
    body: ScadModuleBuilder.() -> Unit = NOOP
): ScadModuleName {
    _scadBuilder.appendLine("module $name($params)")
    _scadBuilder.appendLine("{")
    _createCleanBuilder().body()
    _scadBuilder.appendLine("}")
    return ScadModuleName(name)
}

fun ScadModuleBuilder.call(
    name:ScadModuleName,
    params: String = ""
) = call(name.name, params)

fun ScadModuleBuilder.call(
    name:String,
    params: String = ""
){
    _scadBuilder.appendLine("$_readableModifier ${name}($params);")
}