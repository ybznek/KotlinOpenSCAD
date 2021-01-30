import kotlinOpenScad.core.ScadBuilder
import kotlinOpenScad.core.ScadModuleBuilder
import kotlinOpenScad.extension.color
import kotlinOpenScad.extension.comment
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.cylinder
import kotlinOpenScad.extension.difference
import kotlinOpenScad.extension.group
import kotlinOpenScad.extension.highlight
import kotlinOpenScad.extension.intersection
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.rotateExtrude
import kotlinOpenScad.extension.sphere
import kotlinOpenScad.extension.square
import kotlinOpenScad.extension.translate
import java.io.File


private fun ScadModuleBuilder.buildBoxWithHole(x: Int, y: Int) {
    difference {
        translate(x = x, y = y).cube(10.0, center = true)
        translate(x = x, y = y).cylinder(height = 10.0, diameter = 10.0)
    }
}

fun main(args: Array<String>) {
    val b = ScadBuilder()
    val logo = b.module(name = "logo", fn = 10) {

        difference {
            val diameter = 10.0
            val height = 1.0
            val c = true
            sphere(diameter = diameter)
            rotate(x = 90.0).cylinder(diameter = diameter, height = height, center = c);
            rotate(y = 90.0).cylinder(diameter = diameter, height = height, center = c);
            highlight().rotate(z = 90.0).cylinder(diameter = diameter, height = height, center = c);
        }

        intersection {
            for (deg in 0..360 step 90) {
                rotate(x = deg).translate(x = -20).of {
                    color("red", 0.5F).translate(z = -20).cylinder(height = 10.0, bottomDiameter = 20.0, topDiameter = 10.0)
                    color("red", 0.5F).translate(z = -10).cylinder(height = 10.0, diameter = 10.0)
                    color("blue").cylinder(height = 10.0, bottomDiameter = 10.0, topDiameter = 20.0)
                }
            }
        }
        comment("Build box holder")
        for (y in 0..100 step 10)
            for (x in 0..100 step 10) {
                buildBoxWithHole(x, y)
            }
        group {
            translate(-30, 0, 0).of {
                rotateExtrude(angle = 180.0, convexity = 100.0)
                    .translate(x = -40)
                    .square(sizeX = 10.0, sizeY = 20.0)
            }
        }
    }
    logo.call(50.0)

    File("/dropbox/OpenSCAD/openscadTest.scad").writeText(b.toString())
    println("Hello World!")
}

