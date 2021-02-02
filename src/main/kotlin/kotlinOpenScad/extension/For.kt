package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.extension.expr.ScadExpr
import kotlinOpenScad.extension.expr.VariableGenerator


fun ScadCode.FOR(
    expression: String,
    body: ScadCode.() -> Unit,
) {
    _buildGroup("for ($expression)") {
        _createCleanScope().body()
    }
}

fun ScadCode.FOR(
    variable: ScadExpr,
    range: IntProgression,
    body: ScadCode.() -> Unit,
) {
    return FOR("$variable = ${progressionToRange(range)}", body)
}

fun ScadCode.FOR(
    range: IntProgression,
    body: ScadCode.(ScadExpr) -> Unit,
) {
    val variable = VariableGenerator.getExpr()
    return FOR(variable, range) {
        body(variable)
    }
}


fun ScadCode.FOR(
    variable: ScadExpr,
    range: List<Any?>,
    body: ScadCode.() -> Unit,
) {
    val args = _buildParamsArray(*range.toTypedArray())
    return FOR("$variable = $args", body)
}

fun ScadCode.FOR(
    range: List<Any?>,
    body: ScadCode.(ScadExpr) -> Unit,
) {
    val variable = VariableGenerator.getExpr()
    return FOR(variable, range) {
        body(variable)
    }
}


private fun progressionToRange(prog: IntProgression): String {
    return "[${prog.first}:${prog.step}:${prog.last}]"
}
