package com.avalon

import com.avalon.dto.Post
import com.avalon.etl.PostTransformer
import com.avalon.structs.Grid
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.wnameless.json.flattener.JsonFlattener
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    val om = jacksonObjectMapper()

    var list: List<Post> = om.readValue(File("files/sp_posts_small.json"))
    list = list.subList(0, 10)

    val trans = PostTransformer()

    val maps = trans.toMap(list)

    val g = Grid(maps)

    g.print()
    println("size: ${g.rows}")

}


fun load():String{

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

