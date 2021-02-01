package kotlinOpenScad.extension

import kotlinOpenScad.core.ScadModuleBuilder


fun ScadModuleBuilder.circle(
    radius: Number? = null,
    diameter: Number? = null,
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
    command("circle($params)")
}

fun ScadModuleBuilder.square(
    size: Number,
    center: Boolean? = null
) {
    val params = _buildParams(
        "size" to size,
        "center" to center
    )
    command("square($params)")
}

fun ScadModuleBuilder.square(
    sizeX: Number? = null,
    sizeY: Number? = null,
    center: Boolean? = null
) {
    val params = _buildParams(
        "size" to _buildParamsArray(sizeX, sizeY),
        "center" to center
    )
    command("square($params)")
}


fun ScadModuleBuilder.polygon(
    points: List<ScadPoint>? = null,
    paths: List<List<Number>>? = null,
    convexity: Int? = null
) {
    val params = _buildParams(
        "points" to points?.map { _buildParamsArray(it.x, it.y) },
        "paths" to paths?.map { _buildParamsArray(*it.toTypedArray()) },
        "convexity" to convexity
    )
    command("polygon($params)")
}

enum class HorizontalAlign(private val str: String) {
    Left("left"),
    Center("center"),
    Right("right");

    override fun toString() = str
}

enum class VerticalAlign(private val str: String) {
    Top("top"),
    Center("center"),
    BaseLine("baseline"),
    Bottom("bottom");

    override fun toString() = str
}


enum class TextDirection(private val str: String) {
    LeftToRight("ltr"),
    RightToLeft("rtl"),
    TopToBottom("ttb"),
    BottomToTop("bbt");

    override fun toString() = str
}


fun ScadModuleBuilder.text(
    text: String,
    size: Int? = null,
    font: String? = null,
    horizontalAlign: HorizontalAlign? = null,
    verticalAlign: VerticalAlign? = null,
    spacing: Number? = null,
    direction: TextDirection? = null,
    language: String? = null,
    script: String? = null,
    fragmentCount: Int? = null
) {
    val params = _buildParams(
        "text" to _quote(text),
        "size" to size,
        "font" to font,
        "halign" to horizontalAlign,
        "valign" to verticalAlign,
        "spacing" to spacing,
        "direction" to direction,
        "language" to language,
        "script" to script,
        "\$fn" to fragmentCount
    )
    command("text($params)")
}


fun ScadModuleBuilder.offset(
    radius: Number? = null,
    delta: Number? = null,
    chamfer: Boolean? = null
): ScadModuleBuilder {
    val params = _buildParams(
        "r" to radius,
        "delta" to delta,
        "chamfer" to chamfer
    )
    return _extend("offset($params)")
}


