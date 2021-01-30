package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.linearExtrude(
    height: Double?,
    center: Boolean? = null,
    convexity: Double? = null,
    twist: Double? = null,
    slices: Int? = null,
    scale: Double? = null,
    resolution: Int? = null,
): ScadModuleBuilder {
    val params = _buildParams(
        "height" to height,
        "center" to center,
        "convexity" to convexity,
        "twist" to twist,
        "slices" to slices,
        "scale" to scale,
        "\$fn" to resolution,
    )
    return _extend("linear_extrude($params)")
}

fun ScadModuleBuilder.linearExtrude(
    height: Double?,
    center: Boolean? = null,
    convexity: Double? = null,
    twist: Double? = null,
    slices: Int? = null,
    scaleX: Double,
    scaleY: Double,
    resolution: Int? = null,
): ScadModuleBuilder {
    val params = _buildParams(
        "height" to height,
        "center" to center,
        "convexity" to convexity,
        "twist" to twist,
        "slices" to slices,
        "scale" to _buildParamsArray(scaleX, scaleY),
        "\$fn" to resolution,
    )
    return _extend("linear_extrude($params)")
}



fun ScadModuleBuilder.rotateExtrude(
    convexity: Double? = null,
    angle: Double? = null,
    fragmentMinimumAngle: Double? = null,
    fragmentMinimumLength: Double? = null,
    fragmentCount: Int? = null
): ScadModuleBuilder {
    val params = _buildParams(
        "convexity" to convexity,
        "angle" to angle,
        "\$fa" to fragmentMinimumAngle,
        "\$fs" to fragmentMinimumLength,
        "\$fn" to fragmentCount,
    )
    return _extend("rotate_extrude($params)")
}