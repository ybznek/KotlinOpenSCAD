package kotlinOpenScad.core

import java.io.File
import java.io.IOException

class ScadBuilder {
    private val b = StringBuilder()
    var indentation = 0



    fun appendLine(str: String) {
        b.appendLine(getIndentationString() + str)
    }

    private fun getIndentationString(): String {
        if (indentation < 0)
            return ""
        return "\t".repeat(indentation)
    }

    fun append(str: String) {
        b.append(str)
    }

    inline fun indent(f: () -> Unit) {
        indentation++
        f()
        indentation--
    }

    inline fun unindent(f: () -> Unit) {
        indentation--
        f()
        indentation++
    }

    fun write(file: File) {
        val oldContent = readOldContent(file)
        val newContent = b.toString()
        if (oldContent != newContent) {
            file.writeText(newContent)
        }
    }

    private fun readOldContent(file: File): String {
        try {
            if (file.exists()) {
                return file.readText()
            }
        } catch (e: IOException) {
            ; // handle bellow
        }
        return ""
    }

    override fun toString(): String {
        return b.toString()
    }
}