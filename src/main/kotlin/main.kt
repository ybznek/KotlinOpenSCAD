import kotlinOpenScad.core.ScadBuilder
import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.examples.Joiner
import kotlinOpenScad.extension.FOR
import kotlinOpenScad.extension.expr.FN
import kotlinOpenScad.extension.expr.TimeExpr
import kotlinOpenScad.extension.expr.ex
import kotlinOpenScad.extension.module.define
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.text
import kotlinOpenScad.extension.translate
import java.io.File

// todo polygedron
// todo check with https://hexdocs.pm/open_scad/OpenSCAD.Sphere.html#t:t/0 / or another doc
// etc

fun main() {
    val b = ScadBuilder()
    ScadCode(b).apply {
        result()
    }
    b.write(File("/dropbox/OpenSCAD/openscadTest.scad"))
}


private fun ScadCode.result() {
    include("lib-gear-dh.scad")

    val joiner = Joiner("joiner")
    define(joiner)

    val i = ex("i")

    FOR("i = [1:2:11]") {
        translate(x = i * 10).text(i)
    }

    FOR(1..11 step 2) { exprVar ->
        translate(x = exprVar * 10, y = 20).text(exprVar)
    }

    FOR(listOf(1, 2)) { exprVar2 ->
        translate(x = (exprVar2 + 1) * 100, y = 40).text(exprVar2)
    }

    rotate(z = TimeExpr * 360).of {
        joiner.call(this, "$FN" to 100)
    }
}

