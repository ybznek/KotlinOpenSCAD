package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.translate(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) = _extend("translate(${_buildParamsArray(x, y, z)})")
fun ScadModuleBuilder.translate(x: Int = 0, y: Int = 0, z: Int = 0) = translate(x.toDouble(), y.toDouble(), z.toDouble())

fun ScadModuleBuilder.rotate(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) = _extend("rotate(${_buildParamsArray(x, y, z)})")
fun ScadModuleBuilder.rotate(x: Int = 0, y: Int = 0, z: Int = 0) = rotate(x.toDouble(), y.toDouble(), z.toDouble())

fun ScadModuleBuilder.scale(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) = _extend("scale(${_buildParamsArray(x, y, z)})")
fun ScadModuleBuilder.scale(x: Int = 0, y: Int = 0, z: Int = 0) = scale(x.toDouble(), y.toDouble(), z.toDouble())

fun ScadModuleBuilder.resize(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) = _extend("resize(${_buildParamsArray(x, y, z)})")
fun ScadModuleBuilder.resize(x: Int = 0, y: Int = 0, z: Int = 0) = resize(x.toDouble(), y.toDouble(), z.toDouble())
fun ScadModuleBuilder.group(func: ScadModuleBuilder.() -> Unit) = _extend("group()").of(func)

fun ScadModuleBuilder.comment(comment: String) {
    this._scadBuilder.appendLine("// $comment")
}

fun ScadModuleBuilder.color(r: Float, g: Float, b: Float, alpha: Float? = null): ScadModuleBuilder {
    val colorMod = when (alpha) {
        null -> "color([$r,$g,$b])"
        else -> "color([$r,$g,$b], $alpha)"
    }
    return _extend(colorMod)
}

fun ScadModuleBuilder.color(name: String, alpha: Float? = null): ScadModuleBuilder {
    val colorMod = when (alpha) {
        null -> "color(\"$name\")"
        else -> "color(\"$name\", $alpha)"
    }
    return _extend(colorMod)
}

fun ScadModuleBuilder.highlight(): ScadModuleBuilder {
    return _extend("#")
}

fun ScadModuleBuilder.sphere(diameter: Double? = null) {
    val params = _buildParams(
        "d" to diameter
    )
    _scadBuilder.appendLine("${_readableModifier}sphere($params);")
}

fun ScadModuleBuilder.cylinder(
    height: Double?,
    radius: Double? = null,
    bottomRadius: Double? = null,
    topRadius: Double? = null,
    diameter: Double? = null,
    bottomDiameter: Double? = null,
    topDiameter: Double? = null,
    center: Boolean? = null,
    fragmentMinimumAngle: Double? = null,
    fragmentMinimumLength: Double? = null,
    fragmentCount: Int? = null
) {
    val params = _buildParams(
        "d" to diameter,
        "r" to radius,
        "r1" to bottomRadius,
        "r2" to topRadius,
        "d1" to bottomDiameter,
        "d2" to topDiameter,
        "\$fa" to fragmentMinimumAngle,
        "\$fs" to fragmentMinimumLength,
        "\$fn" to fragmentCount,
        "h" to height,
        "center" to center
    )
    _scadBuilder.appendLine("${_readableModifier}cylinder($params);")
}


fun ScadModuleBuilder.cube(
    size: Double,
    center: Boolean = false
) {
    val params = _buildParams(
        "size" to size,
        "center" to center
    )
    _scadBuilder.appendLine("${_readableModifier}cube($params);")
}


fun ScadModuleBuilder.cube(
    x: Double,
    y: Double,
    z: Double,
    center: Boolean = false
) {
    val params = _buildParams(
        "size" to _buildParamsArray(x, y, z),
        "center" to center
    )
    _scadBuilder.appendLine("${_readableModifier}cube($params);")
}