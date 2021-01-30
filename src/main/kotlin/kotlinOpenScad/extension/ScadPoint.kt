package kotlinOpenScad.extension

data class ScadPoint(val x: Number, val y: Number) {
    fun diffX(value: Number): ScadPoint {
        return ScadPoint(x.toDouble() + value.toDouble(), y)
    }

    fun diffY(value: Number): ScadPoint {
        return ScadPoint(x, y.toDouble() + value.toDouble())
    }

    fun diff(xValue: Number, yValue: Number): ScadPoint {
        return ScadPoint(x.toDouble() + xValue.toDouble(), y.toDouble() + yValue.toDouble())
    }
}