package kotlinOpenScad.extension

import ScadModuleName
import kotlinOpenScad.core.ScadModuleBuilder

private val NOOP: ScadModuleBuilder.() -> Unit = { }

fun ScadModuleBuilder.IF(
    expression: String,
    then: ScadModuleBuilder.() -> Unit = NOOP,
    otherwise: ScadModuleBuilder.() -> Unit = NOOP,
) {
    _buildGroup("if ($expression)") {
        _createCleanBuilder().then()
        if (otherwise !== NOOP) {
            _scadBuilder.unindent {
                _scadBuilder.appendLine("} else {")
            }
            _createCleanBuilder().otherwise()
        }
    }
}

fun ScadModuleBuilder.FOR(
    expression: String,
    body: ScadModuleBuilder.() -> Unit = NOOP,
) {
    _buildGroup("for ($expression)") {
        _createCleanBuilder().body()
    }
}


fun ScadModuleBuilder.MODULE(
    name: String,
    params: String = "",
    body: ScadModuleBuilder.() -> Unit = NOOP
): ScadModuleName {
    _buildGroup("module $name($params)") {
        _createCleanBuilder().body()
    }
    return ScadModuleName(name)
}

fun ScadModuleBuilder.call(
    name: ScadModuleName,
    params: String = ""
) = call(name.name, params)

fun ScadModuleBuilder.call(
    name: String,
    params: String = ""
) {
    command("${name}($params)")
}