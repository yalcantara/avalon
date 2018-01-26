package com.avalon

import com.avalon.structs.Grid
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

fun main(args: Array<String>) {
    val om = jacksonObjectMapper()

    var list: Array<Map<String, Any>> = om.readValue(File("files/sp_posts_small.json"))

    list = list.copyOfRange(0, 3)

    val g = Grid(list)

    println(list.size)
    println(g)
}

