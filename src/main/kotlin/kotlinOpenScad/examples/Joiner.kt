package kotlinOpenScad.examples

import kotlinOpenScad.core.ScadCode
import kotlinOpenScad.extension.color
import kotlinOpenScad.extension.comment
import kotlinOpenScad.extension.cube
import kotlinOpenScad.extension.difference
import kotlinOpenScad.extension.group
import kotlinOpenScad.extension.rotate
import kotlinOpenScad.extension.translate
import kotlinOpenScad.extension.union

class Joiner(
    val holderEdge: Double = 20.0,
    val holderThickness: Double = 7.0,
    val holderLength: Double = 70.0,

    val profileLength: Double = holderLength + 10,
    val profileEdge: Double = 15.0,
    val profileThickness: Double = 1.5,
    val holePlacePercent: Double = 70.0
) {

    fun build(builder: ScadCode) {
        builder.apply {
            difference {
                base()
                profileHole()
                boltHoles()
                cutEdges()
            }
        }
    }


    private fun ScadCode.cutEdges() {
        comment("cut edges")

        group {
            comment("y axe")
            translate(
                z = holderLength / 2,
                y = holderLength
            )
                .rotate(
                    x = -45
                )
                .cube(
                    50,
                    200,
                    50,
                    center = true
                )

            comment("x axe")
            translate(
                z = holderLength / 2,
                x = holderLength
            )
                .rotate(
                    x = -45,
                    z = -90
                )
                .cube(
                    50,
                    200,
                    50,
                    center = true
                )

            comment("bottom")
            translate(
                y = holderLength / 2,
                x = holderLength,
            )
                .rotate(
                    x = -45,
                    z = -90,
                    y = -90
                )
                .cube(
                    50,
                    200,
                    50,
                    center = true
                )
        }
    }

    private fun ScadCode.boltHoles() {
        comment("bolts")


        group {
            val thckheight1 = holderThickness
            val conHeight = 3
            //x
            val eq1 = holderLength - (1.5 * holderEdge)
            val eq2 = holderEdge / 2 + holderThickness / 2
            val nutIn = 0.5
            val topRadius = 5
            translate(
                x = eq1,
                z = eq2,
                y = thckheight1
            ).of {
                rotate(x = 90).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)

                translate(y = nutIn+1).rotate(x = 90).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }
            translate(
                x = eq1,
                z = thckheight1,
                y = eq2
            ).of {
                rotate(x = 180).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)
                translate(z = -nutIn).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }


            // y
            translate(
                x = thckheight1,
                z = eq2,
                y = eq1
            ).of {
                rotate(y = -90).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)
                translate(x = -nutIn).rotate(x = 90, z = 90).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }
            translate(
                x = eq2,
                z = thckheight1,
                y = eq1
            ).of {
                rotate(x = 180).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)
                translate(z = -nutIn).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }


            // z
            translate(
                x = eq2,
                z = eq1,
                y = thckheight1
            ).of {
                rotate(x = 90).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)
                translate(y = nutIn+1).rotate(x = 90).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }

            translate(
                x = thckheight1,
                z = eq1,
                y = eq2
            ).of {
                rotate(x = 90, y = 90, z = -90).boltHole(1.5, topRadius, height = thckheight1, coneHeight = conHeight)
                translate(x = -nutIn).rotate(x = 90, z = 90).screwNut(radius = 5.5 / 2, height = 2, edges = 6)
            }

        }
    }

    private fun ScadCode.profileHole() {
        comment("hole")
        translate(
            x = (holderThickness - profileThickness) / (100 / holePlacePercent),
            z = (holderThickness - profileThickness) / (100 / holePlacePercent),
            y = (holderThickness - profileThickness) / (100 / holePlacePercent)
        ).of {
            //x
            translate(x = profileEdge).of {
                cube(profileLength, profileThickness, profileEdge)
                cube(profileLength, profileEdge, profileThickness)
            }

            //y
            translate(y = profileEdge).of {
                cube(profileThickness, profileLength, profileEdge)
                cube(profileEdge, profileLength, profileThickness)
            }

            //z
            translate(z = profileEdge).of {
                cube(profileThickness, profileEdge, profileLength)
                cube(profileEdge, profileThickness, profileLength)
            }
        }
    }

    private fun ScadCode.base() {
        comment("holder")
        color(0.4, alpha = 0.2).union {
            //x
            cube(holderLength, holderThickness, holderEdge)
            cube(holderLength, holderEdge, holderThickness)

            //y
            cube(holderThickness, holderLength, holderEdge)
            cube(holderEdge, holderLength, holderThickness)

            //z
            cube(holderThickness, holderEdge, holderLength)
            cube(holderEdge, holderThickness, holderLength)
        }
    }
}

