package kotlinOpenScad.examples

import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.extension.*
val height = 3.0


 fun ScadCode.example2() {
    baseWithHoles()


    wheel()
    mirror(z = 1.0 - 0.3).of {
        wheel()
    }

    translate(x = 160.0 - 10.0, y = 50.0 - 10).of {
        wheel()
        mirror(z = 1.0 - 0.3).of {
            wheel()
        }
    }
}

private fun ScadCode.baseWithHoles() {
    difference {
        base()
        baseHoles()
    }
}

private fun ScadCode.baseHoles() {
    val holeRadius = 3.0

    translate(x = 5.0, y = 5.0, z = -0.1).cylinder(height = height + 0.2, radius = holeRadius)
    translate(x = 160.0 - 5.0, y = 40 + 5.0, z = -0.1).cylinder(height = height + 0.2, radius = holeRadius)
}

private fun ScadCode.wheel() {
    val axeRadius = 1.5
    val wheelRadius = 30.0
    val wheelInnerRadius = 20.0
    val wheelInner2Radius = 10.0
    val middleX = 5.0
    val middleY = 5.0
    union {
        difference {
            union {
                translate(x = middleX, y = middleY, z = -10.0).cylinder(height = 30.0, radius = axeRadius)
                color(0.1).translate(x = middleX, y = middleY, z = 10.0).cylinder(height = 10.0, radius = wheelRadius)
            }

            //substracted part
            color(0.3).translate(x = middleX, y = middleY, z = 15.0).cylinder(height = 10.0, radius = wheelInnerRadius)
        }

        // inner cylinder
        color(0.3).translate(x = middleX, y = middleY, z = 15.0).cylinder(height = 10.0, bottomRadius = wheelInner2Radius, topRadius = wheelInner2Radius - 2)


        comment("Bolts")
        translate(x = middleX, y = middleY, z = 15.0).of {
            val between = (wheelInnerRadius + wheelInner2Radius) / 2
            for (angle in 0 until 360 step 30) {
                rotate(z = angle).translate(x = between).color(0.7).cylinder(radius = 1.0, height = 1.0)
            }
        }

        difference {
            translate(x = 5.0, y = 5.0, z = 15.0).of {
                color(0.2).rotateExtrude(convexity = 20.0).translate(x = 30.0).circle(radius = 5.0)
            }

            translate(x = middleX, y = middleY, z = 15.0).of {
                for (angle in 0 until 360 step 10) {
                    rotate(z = angle).translate(x = wheelRadius + 5, z = -5.0).color(0.7).cylinder(radius = 2.0, height = 10.0)
                    rotate(z = angle + 5).translate(x = wheelRadius + 5, z = -5.0).color(0.7).cylinder(radius = 1.0, height = 10.0)
                }
            }

        }

    }
}

private fun ScadCode.base() {
    comment("base")
    val p = listOf(
        ScadPoint(0, 0),
        ScadPoint(10, 0),
        ScadPoint(50, 20),
        ScadPoint(150, 40),
        ScadPoint(160, 40),
    )

    val back = p.reversed().map { x -> x.diffY(10) }


    linearExtrude(height = height).of {
        polygon(
            points = p + back
        )
    }
}

