package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.translate(x: Number = 0.0, y: Number = 0.0, z: Number = 0.0) = _extend("translate(${_buildParamsArray(x, y, z)})")

fun ScadModuleBuilder.rotate(x: Number = 0.0, y: Number = 0.0, z: Number = 0.0) = _extend("rotate(${_buildParamsArray(x, y, z)})")


fun ScadModuleBuilder.scale(x: Number = 0.0, y: Number = 0.0, z: Number = 0.0) = _extend("scale(${_buildParamsArray(x, y, z)})")


fun ScadModuleBuilder.resize(x: Number = 0.0, y: Number = 0.0, z: Number = 0.0) = _extend("resize(${_buildParamsArray(x, y, z)})")

fun ScadModuleBuilder.group(func: ScadModuleBuilder.() -> Unit) = _extend("group()").of(func)

fun ScadModuleBuilder.mirror(x: Number = 0.0, y: Number = 0.0, z: Number = 0.0) = _extend("mirror(${_buildParamsArray(x, y, z)})")


fun ScadModuleBuilder.projection(cut: Boolean = false) = _extend("projection(cut=$cut)")

fun ScadModuleBuilder.comment(comment: String) {
    this._scadBuilder.appendLine("// $comment")
}

fun ScadModuleBuilder.color(gray: Double, alpha: Double? = null): ScadModuleBuilder {
    return color(gray, gray, gray, alpha = alpha)
}

fun ScadModuleBuilder.color(r: Double, g: Double, b: Double, alpha: Double? = null): ScadModuleBuilder {
    val colorMod = when (alpha) {
        null -> "color([$r,$g,$b])"
        else -> "color([$r,$g,$b], $alpha)"
    }
    return _extend(colorMod)
}

fun ScadModuleBuilder.color(name: String, alpha: Double? = null): ScadModuleBuilder {
    val colorMod = when (alpha) {
        null -> "color(\"$name\")"
        else -> "color(\"$name\", $alpha)"
    }
    return _extend(colorMod)
}

fun ScadModuleBuilder.highlight(): ScadModuleBuilder {
    return _extend("#")
}

fun ScadModuleBuilder.sphere(diameter: Number? = null) {
    val params = _buildParams(
        "d" to diameter
    )
    command("sphere($params)")
}

fun ScadModuleBuilder.cylinder(
    height: Number?,
    radius: Number? = null,
    bottomRadius: Number? = null,
    topRadius: Number? = null,
    diameter: Number? = null,
    bottomDiameter: Number? = null,
    topDiameter: Number? = null,
    center: Boolean? = null,
    fragmentMinimumAngle: Number? = null,
    fragmentMinimumLength: Number? = null,
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
    command("cylinder($params)")
}


fun ScadModuleBuilder.cube(
    size: Number,
    center: Boolean = false
) {
    val params = _buildParams(
        "size" to size,
        "center" to center
    )
    command("cube($params)")
}


fun ScadModuleBuilder.cube(
    x: Number,
    y: Number,
    z: Number,
    center: Boolean = false
) {
    val params = _buildParams(
        "size" to _buildParamsArray(x, y, z),
        "center" to center
    )
    command("cube($params)")
}