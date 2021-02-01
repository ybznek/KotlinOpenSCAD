package kotlinOpenScad.extension.lib

import kotlinOpenScad.core.ScadModuleBuilder
import kotlinOpenScad.extension.ScadPoint
import kotlinOpenScad.extension.color
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.cylinder
import kotlinOpenScad.extension.difference
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.translate
import kotlinOpenScad.extension.union

fun ScadModuleBuilder.gear(teeth: Int, thick: Number, scale: Number? = null) {
    val params = _buildParams(
        "n" to teeth,
        "thk" to thick,
        "sc" to scale
    )
    _scadBuilder.appendLine("${_readableModifier}gear($params);")
}

fun getPoint(radius: Double, angle: Double): ScadPoint {
    val angleRad = Math.toRadians(angle)
    val x = radius * kotlin.math.cos(angleRad)
    val y = radius * kotlin.math.sin(angleRad)
    return ScadPoint(x, y)
}




fun ScadModuleBuilder.gear2(teeth: Int, thick: Number, scale: Number) {
    union {
        difference {
            color(0.7).gear(teeth, thick, scale)

            color(0.0, alpha = null).translate(z = -11).of {
                cylinder(height = thick.toDouble() + 22, radius = scale.toDouble() * 0.65)
            }
        }
        color(0.2).of {
            difference {
                cylinder(height = thick.toDouble() + 20, radius = scale.toDouble() * 0.3)
                translate(z=-1).cylinder(height = thick.toDouble() + 22, radius = scale.toDouble() * 0.2)
            }

            for (angle in 0 until 360 step 45) {
                rotate(z = angle).translate(x = 15, y = -5).cube(x = scale.toDouble() * 0.5, y = 10, z = thick)
            }
        }

    }

}