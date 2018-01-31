package com.avalon.structs

import com.avalon.math.MAX_PRINT_COLS
import com.avalon.math.MAX_PRINT_ROWS
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.LinkedHashSet

class Grid {


    private val grid: MutableMap<String, MutableList<Any?>> //column Mayor

    var cols: Int = 0
        get() = grid.size

    var rows: Int = 0
        get() {
            if (grid.isEmpty()) return 0
            return grid.iterator().next().value.size
        }

    constructor() {
        grid = LinkedHashMap()
    }

    constructor(data: List<Map<String, Any?>>) {
        val headers = columns(data)
        grid = LinkedHashMap()
        for (h in headers) {
            grid[h] = ArrayList()
        }

        val ha = headers.toArray() // just to speed up things

        for (j in 0 until ha.size) {
            val k = ha[j]
            for (i in 0 until data.size) {
                var r = data[i]

                accept(r, i)
                grid[k]!!.add(r[k])
            }
        }
    }

    private fun accept(r:Map<String, Any?>, i:Int){
        for(k in r.keys){
            val e = r[k]

            if(e != null && e !is String && e !is Number){
                throw IllegalArgumentException("Invalid value: ${e.toString()} in row $i and column $k.");
            }
        }
    }

    private fun columns(data: List<Map<String, Any?>>): HashSet<String> {
        val cols = LinkedHashSet<String>()
        data.forEach { r -> r.keys.forEach { k -> cols.add(k) } }
        return cols
    }

    fun headers():List<String>{
        val h = ArrayList<String>()

        grid.keys.forEach{k-> h.add(k)}

        return h
    }

    fun header(idx: Int): String {
        return grid.keys.elementAt(idx)
    }

    operator fun get(row: Int, col: Int): Any? {
        val k = header(col)
        return grid[k]!![row]
    }

    operator fun get(row: Int, col: String): Any? {
        return grid[col]!![row]
    }

    operator fun set(row: Int, col: String, ele: Any?) {
        grid[col]!![row] = ele
    }

    fun removeColumn(col:Int){
        val h = header(col)
        grid.remove(h)
    }

    fun info():GridInfo{
        return GridInfo(this)
    }



    fun print() {
        print(System.out, MAX_PRINT_ROWS, MAX_PRINT_COLS)
    }

    override fun toString(): String {
        val sb = StringBuilder(1024)
        print(sb, MAX_PRINT_ROWS, MAX_PRINT_COLS)
        return sb.toString()
    }


    fun format(nf: NumberFormat, obj: Any?): String {
        if (obj == null) {
            return "(null)"
        }

        var str = ""
        if (obj!! is String) {
            str = obj as String
        } else {
            val n = obj as Number

            if(n is Double && n.isNaN() || n is Float && n.isNaN()){
                return "(NaN)"
            }

            if(n is Double && n.isInfinite() && n < 0){
                return "(-Infinite)"
            }

            if(n is Double && n.isInfinite() && n > 0){
                return "(+Infinite)"
            }
            str = nf.format(n)
        }

        if (str.length > 60) {
            return str.substring(0, 60)
        }

        return str
    }

    fun print(out: Appendable, maxRows: Int, maxCols: Int) {
        val mr = if (maxRows < rows) maxRows else rows
        val mc = if (maxCols < cols) maxCols else cols

        try {
            if (mr < rows || mc < cols) {
                out.append("Grid " + rows + "x" + cols + "  (truncated)\n")
            } else {
                out.append("Grid " + rows + "x" + cols + "\n")
            }


            val maxLength = IntArray(mc)


            val nf = DecimalFormat()
            nf.minimumFractionDigits = 2
            nf.maximumFractionDigits = 2


            for (j in 0 until mc) {

                val str = format(nf, header(j))

                maxLength[j] = Math.max(maxLength[j], str.length)
            }

            for (j in 0 until mc) {
                for (i in 0 until mr) {

                    val str = format(nf, get(i, j))

                    maxLength[j] = Math.max(maxLength[j], str.length)
                }
            }


            line(out, maxLength, mc)
            out.append('\n')
            //Headers
            //=================================================================
            for (j in 0 until mc) {
                if (j == 0) {
                    out.append("¦ ")
                } else {
                    out.append(" ")
                }
                val h = header(j)
                val leading = maxLength[j] - h.length


                for (s in 0 until leading) {
                    out.append(" ")
                }

                out.append(h)
                out.append(" ¦")
            }
            //=================================================================
            out.append('\n')
            line(out, maxLength, mc)
            out.append('\n')
            for (i in 0 until mr) {
                for (j in 0 until mc) {
                    if (j == 0) {
                        out.append("¦ ")
                    } else {
                        out.append(" ")
                    }
                    val str = format(nf, get(i, j))
                    val leading = maxLength[j] - str.length


                    for (s in 0 until leading) {
                        out.append(" ")
                    }

                    out.append(str)
                    out.append(" ¦")
                }
                out.append("\n")
            }
            line(out, maxLength, mc)
            out.append("\n")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun line(out: Appendable, maxLength: IntArray, maxCols: Int) {

        for (j in 0 until maxCols) {
            if (j == 0) {
                out.append("+-")
            } else {
                out.append("-")
            }

            for (s in 0 until maxLength[j]) {
                out.append("-")
            }

            out.append("-+")
        }
    }

}