import kotlinOpenScad.core.ScadBuilder
import kotlinOpenScad.core.ScadModuleBuilder
import kotlinOpenScad.extension.ScadPoint
import kotlinOpenScad.extension.circle
import kotlinOpenScad.extension.color
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.cylinder
import kotlinOpenScad.extension.difference
import kotlinOpenScad.extension.linearExtrude
import kotlinOpenScad.extension.polygon
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.sphere
import kotlinOpenScad.extension.translate
import kotlinOpenScad.extension.union
import java.io.File

// todo polygedron
// todo surface
// todo check with https://hexdocs.pm/open_scad/OpenSCAD.Sphere.html#t:t/0 / or another doc
// etc


fun main(args: Array<String>) {
    val b = ScadBuilder()
    val car = b.module(name = "car", fn = 10) {

        color(0.2).of {
            wheel(0, 10)
            wheel(0, 115, 5)
        }

        translate(0, 10, z = -5).of {
            color(0.3).cylinder(height = 15, radius = 9)
            color(0.9).translate(z = -10).of {
                cylinder(height = 10, topRadius = 9, bottomRadius = 20)
                translate(z = -10).cylinder(height = 10, radius = 20)
            }
        }

        translate(0, 115, z = -5).of {
            color(0.3).cylinder(height = 15, radius = 9)
            color(0.9).translate(z = -10).of {
                cylinder(height = 10, topRadius = 9, bottomRadius = 20)
                translate(z = -10).cylinder(height = 10, radius = 20)
            }
        }
        color(0.9).translate(x = -20, z = -25, y = 10).cube(x = 40, y = 105, z = 10)

        of {
            color(0.6).translate(z = 10, x = 0, y = 10).sphere(diameter = 19)
            color(0.1).translate(z = 11, x = -15, y = 9).cube(x = 30, y = 2, z = 2)
            color(0.6).translate(z = 10, x = 0, y = 115).sphere(diameter = 19)
            color(0.1).translate(z = 11, x = -15, y = 114).cube(x = 30, y = 2, z = 2)
        }
    }


    car.call(50.0)


    val file = File("/dropbox/OpenSCAD/openscadTest.scad")
    val old = file.readText()
    val new = b.toString()
    if (old != new) {
        file.writeText(new)
    }
    println("Hello World!")
}

private fun ScadModuleBuilder.wheel(posX: Number, posY: Number, angleShift: Int = 0) {
    translate(x = posX, y = posY)
        .linearExtrude(height = 10)
        .difference {
            union {
                circle(radius = 50)
                for (angle in 0 until 360 step 10) {
                    rotate(z = angle + angleShift).translate(x = 50 - 1.0, y = -5).of {
                        polygon(
                            points = listOf(
                                ScadPoint(0, 0),
                                ScadPoint(5, 5),
                                ScadPoint(0, 10),
                            )
                        )
                    }
                }
            }

            union {
                for (angle in 0 until 360 step 30) {
                    rotate(z = angle + angleShift).translate(x = 15, y = -5).of {
                        polygon(
                            points = listOf(
                                ScadPoint(15, 0),
                                ScadPoint(0, 2.5),
                                ScadPoint(0, 7.5),
                                ScadPoint(15, 10),
                            )
                        )
                    }

                }
            }
            circle(radius = 10)


        }
}

