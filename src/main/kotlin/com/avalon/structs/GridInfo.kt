package com.avalon.structs

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class GridInfo {

    val headers: List<String>

    val max: Map<String, Double?>

    val min: Map<String, Double?>
    val avg: Map<String, Double?>
    val sum: Map<String, Double?>
    val absum: Map<String, Double?>
    val variance: Map<String, Double?>
    val stdev: Map<String, Double?>

    private val positives: Map<String, Int>
    private val negatives: Map<String, Int>
    private val integers: Map<String, Int>
    private val zeroes: Map<String, Int>
    private val decimals: Map<String, Int>
    private val nans: Map<String, Int>
    private val ninfinities: Map<String, Int>
    private val pinfinities: Map<String, Int>

    private val words: Map<String, Int>
    private val empties: Map<String, Int>
    /*private val nulls: Map<String, Int>
    private val maxWord: Map<String, String?>
    private val minWord: Map<String, String?>
*/

    constructor(g: Grid) {

        val max = LinkedHashMap<String, Double?>()
        val min = LinkedHashMap<String, Double?>()
        val avg = LinkedHashMap<String, Double?>()
        val sum = LinkedHashMap<String, Double?>()
        val absum = LinkedHashMap<String, Double?>()
        val variance = LinkedHashMap<String, Double?>()
        val stdev = LinkedHashMap<String, Double?>()

        val positives = LinkedHashMap<String, Int>()
        val negatives = LinkedHashMap<String, Int>()
        val integers = LinkedHashMap<String, Int>()
        val zeroes = LinkedHashMap<String, Int>()
        val decimals = LinkedHashMap<String, Int>()
        val nans = LinkedHashMap<String, Int>()
        val ninfinities = LinkedHashMap<String, Int>()
        val pinfinities = LinkedHashMap<String, Int>()

        val words = LinkedHashMap<String, Int>()
        val empties = LinkedHashMap<String, Int>()

        for (k in g.headers()) {

            max[k] = computeMax(g, k)
            min[k] = computeMin(g, k)
            avg[k] = computeAvg(g, k)
            sum[k] = computeSum(g, k)
            absum[k] = computeAbsum(g, k)
            variance[k] = computeVariance(g, k)
            stdev[k] = computeStdev(variance, k)

            positives[k] = computePositives(g, k)
            negatives[k] = computeNegatives(g, k)
            zeroes[k] = computeZeroes(g, k)
            integers[k] = computeIntegers(g, k)
            decimals[k] = computeDecimals(g, k)
            nans[k] = computeNans(g, k)
            ninfinities[k] = computeNegativeInfinities(g, k)
            pinfinities[k] = computePositiveInfinities(g, k)

            words[k] = computeWords(g, k)
            empties[k] = computeEmpties(g, k)
        }


        this.headers = Collections.unmodifiableList(g.headers())

        this.max = Collections.unmodifiableMap(max)
        this.min = Collections.unmodifiableMap(min)
        this.avg = Collections.unmodifiableMap(avg)
        this.sum = Collections.unmodifiableMap(sum)
        this.absum = Collections.unmodifiableMap(absum)
        this.variance = Collections.unmodifiableMap(variance)
        this.stdev = Collections.unmodifiableMap(stdev)
        this.positives = Collections.unmodifiableMap(positives)
        this.negatives = Collections.unmodifiableMap(negatives)

        this.zeroes = Collections.unmodifiableMap(zeroes)
        this.integers = Collections.unmodifiableMap(integers)
        this.decimals = Collections.unmodifiableMap(decimals)
        this.nans = Collections.unmodifiableMap(nans)
        this.ninfinities = Collections.unmodifiableMap(ninfinities)
        this.pinfinities = Collections.unmodifiableMap(pinfinities)

        this.words = Collections.unmodifiableMap(words)
        this.empties = Collections.unmodifiableMap(empties)
    }


    private fun verifyDouble(e: Any?): Double? {

        if (e == null ||
                e == "") {
            return null
        }

        var ans: Double
        if (e is String) {

            try {
                ans = e.toDouble()
            } catch (e: NumberFormatException) {
                return null
            }

            if (ans == Double.POSITIVE_INFINITY ||
                    ans == Double.NEGATIVE_INFINITY) {
                return null
            }

            if(ans is Number && ans.isNaN()){
                return null
            }

            return ans
        }

        ans = (e as Number).toDouble()

        if (ans.isNaN() || ans.isInfinite()) {
            return null
        }

        return ans
    }

    private fun computeMax(g: Grid, k: String): Double? {

        var crt: Double? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (crt == null) {
                crt = d
            } else {
                crt = Math.max(crt, d)
            }
        }
        return crt
    }

    private fun computeMin(g: Grid, k: String): Double? {

        var crt: Double? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (crt == null) {
                crt = d
            } else {
                crt = Math.min(crt, d)
            }
        }
        return crt
    }


    private fun computeAvg(g: Grid, k: String): Double? {

        var sum: Double? = null
        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (sum == null) {
                sum = d
            } else {
                sum += d
            }

            count++
        }

        if (sum == null) {
            return null
        }

        var mean = sum / count

        return mean
    }

    private fun computeSum(g: Grid, k: String): Double? {

        var sum: Double? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (sum == null) {
                sum = d
            } else {
                sum += d
            }
        }

        if (sum == null) {
            return null
        }

        return sum
    }

    private fun computeAbsum(g: Grid, k: String): Double? {

        var sum: Double? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }


            if (sum == null) {
                sum = d
            } else {
                sum += Math.abs(d)
            }
        }

        return sum
    }

    private fun computeVariance(g: Grid, k: String): Double? {

        var mean = 0.0
        var m2 = 0.0
        var count = 0


        for (i in 0 until g.rows) {
            val e = g[i, k]
            val newValue: Double? = verifyDouble(e)
            if (newValue == null) {
                continue
            }

            count++
            val delta = newValue - mean
            mean += delta / count
            val delta2 = newValue - mean
            m2 += delta * delta2
        }

        if (count == 0) {
            return null
        }

        if (count == 1) {
            return 0.0
        }

        val variance = m2 / (count - 1)

        return variance
    }

    private fun computeStdev(variance: Map<String, Double?>, k: String): Double? {

        val v = variance[k]
        if (v == null) {
            return null
        }

        val stdev = Math.sqrt(v)

        return stdev
    }

    private fun computePositives(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (d > 0) {
                count++
            }
        }
        return count
    }

    private fun computeNegatives(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (d < 0) {
                count++
            }
        }
        return count
    }

    private fun computeZeroes(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if (d == 0.0) {
                count++
            }
        }
        return count
    }

    private fun computeIntegers(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            val ival = d.toLong().toDouble()

            if (d == ival) {
                count++
            }
        }
        return count
    }

    private fun computeDecimals(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null || d == 0.0) {
                continue
            }

            val ival = d.toLong().toDouble()

            if (d != ival) {
                count++
            }
        }
        return count
    }

    private fun computeNans(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if (e == null || e == "") {
                continue
            }


            val dval: Double
            if (e is String) {
                try {
                    dval = e.toDouble()
                } catch (ex: NumberFormatException) {
                    continue
                }
            } else {
                dval = (e as Number).toDouble()
            }

            if (dval.isNaN()) {
                count++
            }
        }
        return count
    }


    private fun computeNegativeInfinities(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if (e == null || e == "") {
                continue
            }


            val dval: Double
            if (e is String) {
                try {
                    dval = e.toDouble()
                } catch (ex: NumberFormatException) {
                    continue
                }
            } else {
                dval = (e as Number).toDouble()
            }

            if (dval.isInfinite() && dval < 0) {
                count++
            }
        }
        return count
    }

    private fun computePositiveInfinities(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if (e == null || e == "") {
                continue
            }


            val dval: Double
            if (e is String) {
                try {
                    dval = e.toDouble()
                } catch (ex: NumberFormatException) {
                    continue
                }
            } else {
                dval = (e as Number).toDouble()
            }

            if (dval.isInfinite() && dval > 0) {
                count++
            }
        }
        return count
    }

    private fun computeWords(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if(e == null || e == ""){
                continue
            }

            if(e is String){
                try{
                    e.toDouble()
                    continue
                }catch (ex: NumberFormatException){
                    //if it isn't  Double, then is a word
                    count++
                }
            }
        }

        return count
    }

    private fun computeEmpties(g: Grid, k: String): Int {

        var count: Int = 0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if(e != null && e == ""){
                count++
            }
        }

        return count
    }


    fun toGrid(): Grid {


        val max = LinkedHashMap<String, Any?>()
        max[""] = "Maximum"
        rowInfo(this.max, max)

        val min = LinkedHashMap<String, Any?>()
        min[""] = "Minimum"
        rowInfo(this.min, min)

        val avg = LinkedHashMap<String, Any?>()
        avg[""] = "Average"
        rowInfo(this.avg, avg)

        val sum = LinkedHashMap<String, Any?>()
        sum[""] = "Sum"
        rowInfo(this.sum, sum)

        val absum = LinkedHashMap<String, Any?>()
        absum[""] = "Absolute Sum"
        rowInfo(this.absum, absum)

        val variance = LinkedHashMap<String, Any?>()
        variance[""] = "Variance"
        rowInfo(this.variance, variance)

        val stdev = LinkedHashMap<String, Any?>()
        stdev[""] = "Standard Dev."
        rowInfo(this.stdev, stdev)




        //=====================================================================
        val positives = LinkedHashMap<String, Any?>()
        positives[""] = "Positives"
        rowInfo(this.positives, positives)

        val negatives = LinkedHashMap<String, Any?>()
        negatives[""] = "Negatives"
        rowInfo(this.negatives, negatives)

        val zeroes = LinkedHashMap<String, Any?>()
        zeroes[""] = "Zeroes"
        rowInfo(this.zeroes, zeroes)

        val integers = LinkedHashMap<String, Any?>()
        integers[""] = "Integers"
        rowInfo(this.integers, integers)

        val decimals = LinkedHashMap<String, Any?>()
        decimals[""] = "Decimals"
        rowInfo(this.decimals, decimals)

        val nans = LinkedHashMap<String, Any?>()
        nans[""] = "NaN"
        rowInfo(this.nans, nans)

        val ninfinities = LinkedHashMap<String, Any?>()
        ninfinities[""] = "Negative Infinities"
        rowInfo(this.ninfinities, ninfinities)

        val pinfinities = LinkedHashMap<String, Any?>()
        pinfinities[""] = "Positive Infinities"
        rowInfo(this.pinfinities, pinfinities)
        //=====================================================================

        val words = LinkedHashMap<String, Any?>()
        words[""] = "Words"
        rowInfo(this.words, words)

        val empties = LinkedHashMap<String, Any?>()
        empties[""] = "Empties"
        rowInfo(this.empties, empties)

        val list = ArrayList<Map<String, Any?>>()


        list.add(max)
        list.add(min)
        list.add(avg)
        list.add(sum)
        list.add(absum)
        list.add(variance)
        list.add(stdev)

        list.add(positives)
        list.add(negatives)
        list.add(zeroes)
        list.add(integers)
        list.add(decimals)
        list.add(nans)
        list.add(ninfinities)
        list.add(pinfinities)

        list.add(words)
        list.add(empties)

        val g = Grid(list)

        return g

    }

    private fun rowInfo(info: Map<String, *>, row: LinkedHashMap<String, Any?>) {

        for (k in info.keys) {
            val v = info[k]

            row[k] = v
        }
    }

}