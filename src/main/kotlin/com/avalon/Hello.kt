package com.avalon

import com.avalon.dto.Post
import com.avalon.structs.Grid
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.wnameless.json.flattener.JsonFlattener
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    val om = jacksonObjectMapper()

    var list: List<Map<String, Any>> = om.readValue(File("files/sp_posts_small.json"))

    list = list.subList(0, 10)

    //list = list.map { e -> e["car"] as Map<String, Any> }


    var json = list[0]

    var post = om.convertValue(json, Post::class.java)

    //println(post)

    var id = json["extractionId"]

    println(post)
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

