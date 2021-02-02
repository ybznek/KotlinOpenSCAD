package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadCode


fun ScadCode.translate(x: Number = 0, y: Number = 0, z: Number = 0) = _extend("translate(${_buildParamsArray(x, y, z)})")

fun ScadCode.rotate(x: Number = 0, y: Number = 0, z: Number = 0) = _extend("rotate(${_buildParamsArray(x, y, z)})")


fun ScadCode.scale(x: Number = 0, y: Number = 0, z: Number = 0) = _extend("scale(${_buildParamsArray(x, y, z)})")


fun ScadCode.resize(x: Number = 0, y: Number = 0, z: Number = 0) = _extend("resize(${_buildParamsArray(x, y, z)})")

fun ScadCode.group(func: ScadCode.() -> Unit) = _extend("group()").of(func)

fun ScadCode.mirror(x: Number = 0, y: Number = 0, z: Number = 0) = _extend("mirror(${_buildParamsArray(x, y, z)})")


fun ScadCode.projection(cut: Boolean = false) = _extend("projection(cut=$cut)")

fun ScadCode.comment(comment: String) {
    this._scadBuilder.appendLine("// $comment")
}

fun ScadCode.color(gray: Double, alpha: Double? = null): ScadCode {
    return color(gray, gray, gray, alpha = alpha)
}

fun ScadCode.color(r: Double, g: Double, b: Double, alpha: Double? = null): ScadCode {
    val colorMod = when (alpha) {
        null -> "color([$r,$g,$b])"
        else -> "color([$r,$g,$b], $alpha)"
    }
    return _extend(colorMod)
}

fun ScadCode.color(name: String, alpha: Double? = null): ScadCode {
    val colorMod = when (alpha) {
        null -> "color(\"$name\")"
        else -> "color(\"$name\", $alpha)"
    }
    return _extend(colorMod)
}

fun ScadCode.highlight(): ScadCode {
    return _extend("#")
}

fun ScadCode.sphere(diameter: Number? = null) {
    val params = _buildParams(
        "d" to diameter
    )
    command("sphere($params)")
}

fun ScadCode.cylinder(
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


fun ScadCode.cube(
    size: Number,
    center: Boolean = false
) {
    val params = _buildParams(
        "size" to size,
        "center" to center
    )
    command("cube($params)")
}


fun ScadCode.surface(
    filename: Any,
    center: Boolean? = null,
    invert: Boolean? = null,
    convexity: Int? = null
) {
    val params = _buildParams(
        "file" to _prepareText(filename),
        "center" to center,
        "invert" to invert,
        "convexity" to convexity,
    )
    command("surface($params)")
}


fun ScadCode.cube(
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