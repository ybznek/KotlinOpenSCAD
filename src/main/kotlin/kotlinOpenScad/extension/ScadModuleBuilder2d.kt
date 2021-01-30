package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.circle(
    radius: Double? = null,
    diameter: Double? = null,
    fragmentMinimumAngle: Int? = null,
    fragmentMinimumLength: Int? = null,
    fragmentCount: Int? = null,

    ) {
    val params = _buildParams(
        "r" to radius,
        "d" to diameter,
        "\$fa" to fragmentMinimumAngle,
        "\$fs" to fragmentMinimumLength,
        "\$fn" to fragmentCount
    )
    _scadBuilder.appendLine("${_readableModifier}circle($params);")
}

fun ScadModuleBuilder.square(
    size: Double,
    center: Boolean? = null
) {
    val params = _buildParams(
        "size" to size,
        "center" to center
    )
    _scadBuilder.appendLine("${_readableModifier}square($params);")
}

fun ScadModuleBuilder.square(
    sizeX: Double? = null,
    sizeY: Double? = null,
    center: Boolean? = null
) {
    val params = _buildParams(
        "size" to _buildParamsArray(sizeX, sizeY),
        "center" to center
    )
    _scadBuilder.appendLine("${_readableModifier}square($params);")
}

