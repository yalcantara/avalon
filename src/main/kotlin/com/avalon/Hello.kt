package com.avalon

import com.avalon.dto.Post
import com.avalon.etl.PostTransformer
import com.avalon.structs.Grid
import com.avalon.structs.GridInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    start3()
}

fun start3(){
    val om = jacksonObjectMapper()

    var list: List<Post> = om.readValue(File("files/sp_posts_small.json"))
    list = list.subList(0, 10)

    val trans = PostTransformer()

    val maps = trans.toMap(list)

    val g = Grid(maps)


}

fun start1(){
    val a = Double.NaN

    val b = (a as Number).toDouble()

    b.isInfinite()
    if(a == Double.NaN){
        println("klk")
    }
}

fun start2(){
    val om = jacksonObjectMapper()

    var list: List<Post> = om.readValue(File("files/sp_posts_small.json"))
    list = list.subList(0, 10)

    val trans = PostTransformer()

    val maps = trans.toMap(list)

    val g = Grid(maps)

    g[0, "price"] = Double.NaN
    g[1, "price"] = null
    g[2, "price"] = null
    g[3, "price"] = Double.NEGATIVE_INFINITY
    g[4, "price"] = Double.POSITIVE_INFINITY
    g[5, "price"] = Double.POSITIVE_INFINITY
    g.print()
    println("size: ${g.rows}")


    val info = GridInfo(g)

    info.toGrid().print()
}


fun load(): String {

    val f = File("files/sp_posts_small.json")

    val l = f.length()

    val fr = FileReader(f)

    var str: String = ""
    fr.use {
        val buff = CharArray(l.toInt())
        val read = fr.read(buff)

        str = String(buff, 0, read)
    }



    return str
}

