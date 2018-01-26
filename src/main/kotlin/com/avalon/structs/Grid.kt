package com.avalon.structs

import com.avalon.math.MAX_PRINT_COLS
import com.avalon.math.MAX_PRINT_ROWS
import java.io.IOException

class Grid {


    private val grid: MutableMap<String, MutableList<Any?>> //column Mayor

    var cols:Int =0
        get()=grid.size

    var rows:Int = 0
    get(){
        if(grid.isEmpty()) return 0
        return grid.iterator().next().value.size
    }

    constructor() {
        grid = LinkedHashMap()
    }

    constructor(data: Array<Map<String, Any>>) {
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

                grid[k]!!.add(r[k])
            }
        }
    }

    private fun columns(data: Array<Map<String, Any>>): HashSet<String> {
        val cols = LinkedHashSet<String>()
        data.forEach { r -> r.keys.forEach { k -> cols.add(k) } }
        return cols
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


    fun print() {
        print(System.out, MAX_PRINT_ROWS, MAX_PRINT_COLS)
    }

    override fun toString(): String {
        val sb = StringBuilder(1024)
        print(sb, MAX_PRINT_ROWS, MAX_PRINT_COLS)
        return sb.toString()
    }


    fun format(obj:Any?):String{
        if(obj == null){
            return "(null)"
        }

        var str = ""
        if(obj!! is String){
            str = obj as String
        }else{
            str = obj.toString()
        }

        if(str.length > 60){
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

            for (j in 0 until mc) {
                for (i in 0 until mr) {

                    val str = format(get(i, j))

                    maxLength[j] = Math.max(maxLength[j], str.length)
                }
            }

            for (i in 0 until mr) {
                for (j in 0 until mc) {
                    if (j == 0) {
                        out.append("| ")
                    }else{
                        out.append(" ")
                    }
                    val str = format(get(i, j))
                    val leading = maxLength[j] - str.length


                    for (s in 0 until leading) {
                        out.append(" ")
                    }

                    out.append(str)
                    out.append(" |")
                }
                out.append("\n")
            }

            out.append("\n")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}