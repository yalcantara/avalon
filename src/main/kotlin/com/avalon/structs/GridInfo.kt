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
    private val positives: Map<String, Int?>
    private val negatives: Map<String, Int?>

    private val integers: Map<String, Int?>
    /*private val decimals: Map<String, Int>
    private val nans: Map<String, Int>
    private val ninfinites: Map<String, Int>
    private val pinfinites: Map<String, Int>

    private val words: Map<String, Int>
    private val empties: Map<String, Int>
    private val nulls: Map<String, Int>
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
        val positives = LinkedHashMap<String, Int?>()
        val negatives = LinkedHashMap<String, Int?>()

        val integers = LinkedHashMap<String, Int?>()

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

            integers[k] = computeIntegers(g, k)
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

        this.integers = Collections.unmodifiableMap(integers)
    }


    private fun verifyDouble(e: Any?): Double? {

        if (e == null ||
                e == "" ||
                e == Double.NaN ||
                e == Double.POSITIVE_INFINITY ||
                e == Double.NEGATIVE_INFINITY) {
            return null
        }

        var ans: Double
        if (e is String) {

            try {
                ans = e.toDouble()
            } catch (e: NumberFormatException) {
                return null
            }

            if (ans == Double.NaN ||
                    ans == Double.POSITIVE_INFINITY ||
                    ans == Double.NEGATIVE_INFINITY) {
                return null
            }

            return ans
        }

        ans = (e as Number).toDouble()

        if (ans == Double.NaN ||
                ans == Double.POSITIVE_INFINITY ||
                ans == Double.NEGATIVE_INFINITY) {
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

            if(sum == null){
                sum = d
            }else{
                sum += d
            }

            count++
        }

        if(sum == null){
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

            if(sum == null){
                sum = d
            }else{
                sum += d
            }
        }

        if(sum == null){
            return null
        }

        return sum
    }

    private fun computeAbsum(g: Grid, k: String): Double? {

        var sum = 0.0
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            sum += Math.abs(d)
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

    private fun computePositives(g: Grid, k: String): Int? {

        var count: Int? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if(d > 0) {
                if(count == null){
                    count = 1
                }else{
                    count++
                }
            }
        }
        return count
    }

    private fun computeNegatives(g: Grid, k: String): Int? {

        var count: Int? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            val d: Double? = verifyDouble(e)
            if (d == null) {
                continue
            }

            if(d < 0) {
                if(count == null){
                    count = 1
                }else{
                    count++
                }
            }
        }
        return count
    }

    private fun computeIntegers(g: Grid, k: String): Int? {

        var count: Int? = null
        for (i in 0 until g.rows) {
            val e = g[i, k]
            if(e == null || e == ""){
                continue
            }

            if(k == "car.litresStr"){
                val r = 0
            }

            val ival: Int
            val dval: Double
            val tval: Double
            if(e is String){
                try{
                    dval = e.toDouble()
                    ival = dval.toInt()
                    tval = ival.toDouble()
                }catch(ex:NumberFormatException){
                    continue
                }
            }else if(e is Number){

                dval = e.toDouble()
                ival = dval.toInt()
                tval = ival.toDouble()
            }else{
                throw IllegalArgumentException("Only String and Numeric values are allowed. Column: $k, row: $i.")
            }


            if(dval == tval){
                if(count == null){
                    count = 1
                }else{
                    count++
                }
            }
        }
        return count
    }



    fun toGrid():Grid{



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

        val positives = LinkedHashMap<String, Any?>()
        positives[""] = "Positives"
        rowInfo(this.positives, positives)

        val negatives = LinkedHashMap<String, Any?>()
        negatives[""] = "Negatives"
        rowInfo(this.negatives, negatives)


        //=====================================================================
        val integers = LinkedHashMap<String, Any?>()
        integers[""] = "Integers"
        rowInfo(this.integers, integers)



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

        list.add(integers)

        val g = Grid(list)

        return g

    }

    private fun rowInfo(info:Map<String, *>, row: LinkedHashMap<String, Any?>){

        for(k in info.keys){
            val v = info[k]

            row[k] = v
        }
    }

}