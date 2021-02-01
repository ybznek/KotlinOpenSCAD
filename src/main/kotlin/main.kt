import kotlinOpenScad.core.ScadBuilder
import kotlinOpenScad.core.ScadModuleBuilder
import kotlinOpenScad.examples.Joiner
import kotlinOpenScad.extension.FOR
import kotlinOpenScad.extension.IF
import kotlinOpenScad.extension.MODULE
import kotlinOpenScad.extension.ScadPoint
import kotlinOpenScad.extension.call
import kotlinOpenScad.extension.circle
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.difference
import kotlinOpenScad.extension.highlight
import kotlinOpenScad.extension.linearExtrude
import kotlinOpenScad.extension.module.AbstractScadCustomModule
import kotlinOpenScad.extension.module.define
import kotlinOpenScad.extension.number.TimeExpr
import kotlinOpenScad.extension.number.ex
import kotlinOpenScad.extension.polygon
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.translate
import kotlinOpenScad.extension.union
import java.io.File

// todo polygedron
// todo surface
// todo check with https://hexdocs.pm/open_scad/OpenSCAD.Sphere.html#t:t/0 / or another doc
// etc
// todo add posibility to not use module

class ScadCustomModule(name: String) : AbstractScadCustomModule(name) {
    private val f = ex("f")

    override fun ScadModuleBuilder.defineInternal() {
        MODULE("test", "$f = 10") {
            val i = ex("i")
            FOR("$i = [0:$f]") {
                cube(size = i, center = false);
            }
            IF("1==1", then = {
                cube(4)
            }, otherwise = {
                cube(6)
            })
        }
    }

    fun call(scadModuleBuilder: ScadModuleBuilder, value: Number) {
        genericCall(scadModuleBuilder, "$f" to value)
    }
}


fun main() {
    val b = ScadBuilder()
    b.include("lib-gear-dh.scad")
    val car = b.module(name = "car", fn = 10) {
        rotate(z = ex(360) * TimeExpr).of {
            Joiner().build(this)
        }


        val m = ScadCustomModule("test")
        define(m)

        m.call(this, value = 42)

        highlight().call("test", "50")

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

