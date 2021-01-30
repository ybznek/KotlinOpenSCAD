package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.linearExtrude(
    height: Number?,
    center: Boolean? = null,
    convexity: Number? = null,
    twist: Number? = null,
    slices: Int? = null,
    scale: Number? = null,
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
    height: Number?,
    center: Boolean? = null,
    convexity: Number? = null,
    twist: Number? = null,
    slices: Int? = null,
    scaleX: Number,
    scaleY: Number,
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
    convexity: Number? = null,
    angle: Number? = null,
    fragmentMinimumAngle: Number? = null,
    fragmentMinimumLength: Number? = null,
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