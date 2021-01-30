package kotlinOpenScad.core

class ScadModule(private val name: String, private val builder: ScadBuilder) {

    fun call(fn: Double? = null) {
        builder.addModuleCall(name, fn)
    }
}
