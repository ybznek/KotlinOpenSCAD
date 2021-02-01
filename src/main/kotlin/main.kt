import kotlinOpenScad.core.ScadBuilder
import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.examples.Joiner
import kotlinOpenScad.extension.FOR
import kotlinOpenScad.extension.FUNCTION
import kotlinOpenScad.extension.IF
import kotlinOpenScad.extension.MODULE
import kotlinOpenScad.extension.call
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.expr.FN
import kotlinOpenScad.extension.expr.TimeExpr
import kotlinOpenScad.extension.expr.ex
import kotlinOpenScad.extension.module.AbstractScadCustomModule
import kotlinOpenScad.extension.module.define
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.text
import kotlinOpenScad.extension.translate
import java.io.File

// todo polygedron
// todo surface
// todo check with https://hexdocs.pm/open_scad/OpenSCAD.Sphere.html#t:t/0 / or another doc
// etc
// todo add posibility to not use module

class ScadCustomModule(name: String) : AbstractScadCustomModule(name) {
    private val f = ex("f")

    override fun ScadCode.defineInternal() {
        MODULE(name, "$f = 10") {
            val i = ex("i")

            FOR("$i = [0:$f]") {
                cube(size = i, center = false);
            }

            IF("1==1",
                then = {
                    cube(4)
                },
                otherwise = {
                    cube(6)
                })
        }
    }

    fun call(scadCode: ScadCode, value: Number) {
        genericCall(scadCode, "$f" to value)
    }
}


fun main() {
    val b = ScadBuilder()

    ScadCode(b).apply {
        include("lib-gear-dh.scad")
        val mainModule = MODULE(name = "mainModule", "$FN = 10") {
            rotate(z = TimeExpr * 360).of {
                Joiner().build(this)
            }

            val iVar = ex("i")

            val funcName = FUNCTION(
                name = "sum",
                params = "a, b",
                expression = """
                    a+b
                """
            )

            FOR("$iVar = [1:2]") {
                translate(iVar * 10).text(call(funcName, "1,2"))
            }

            val m = ScadCustomModule("custom")
            define(m)
            m.call(this, 3)

        }

        call(mainModule)
    }
    b.write(File("/dropbox/OpenSCAD/openscadTest.scad"))

}

