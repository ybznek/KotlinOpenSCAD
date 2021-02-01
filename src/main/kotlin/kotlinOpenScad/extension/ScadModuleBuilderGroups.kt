package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadCode



fun ScadCode.union(func: ScadCode.() -> Unit) {
    _buildGroup("union()", func)
}

fun ScadCode.difference(func: ScadCode.() -> Unit) {
    _buildGroup("difference()", func)
}

fun ScadCode.intersection(func: ScadCode.() -> Unit) {
    _buildGroup("intersection()", func)
}
