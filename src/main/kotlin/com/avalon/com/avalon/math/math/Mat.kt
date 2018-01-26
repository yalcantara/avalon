package com.avalon.math

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import java.io.IOException
import java.text.DecimalFormat
import java.lang.Math.max
import java.text.NumberFormat


class Mat {

    val m: Int
    val n: Int
    val length: Int
    private val grid: INDArray

    val rows: Int
        get() = m

    val cols: Int
        get() = n


    constructor(rows: Int, cols: Int) {
        m = rows
        n = rows
        length = m * n
        grid = Nd4j.create(rows, cols)
    }


    operator fun get(i: Int, j: Int): Float {
        return grid.getFloat(i, j)
    }

    operator fun set(i: Int, j: Int, v: Float) {
        grid.putScalar(i, j, v.toDouble())
    }

    fun print() {
        print(System.out, MAX_PRINT_ROWS, MAX_PRINT_COLS)
    }

    override fun toString(): String {
        val sb = StringBuilder(1024)
        print(sb, MAX_PRINT_ROWS, MAX_PRINT_COLS)
        return sb.toString()
    }


    fun print(out: Appendable, maxRows: Int, maxCols: Int) {
        val mr = if (maxRows < m) maxRows else m
        val mc = if (maxCols < n) maxCols else n

        try {
            if (mr < m || mc < n) {
                out.append("Matrix " + m + "x" + n + "  (truncated)\n")
            } else {
                out.append("Matrix " + m + "x" + n + "\n")
            }

            val f = DecimalFormat.getNumberInstance()
            f.maximumFractionDigits = 4
            f.minimumFractionDigits = 4
            f.isGroupingUsed = true

            val maxLength = IntArray(mc)

            for (j in 0 until mc) {
                for (i in 0 until mr) {

                    val str = f.format(get(i, j))

                    maxLength[j] = max(maxLength[j], str.length)
                }
            }

            for (i in 0 until mr) {
                for (j in 0 until mc) {
                    if (j > 0) {
                        out.append("  ")
                    }
                    val str = f.format(get(i, j))
                    val leading = maxLength[j] - str.length
                    for (s in 0 until leading) {
                        out.append(" ")
                    }

                    out.append(str)
                }
                out.append("\n")
            }

            out.append("\n")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}