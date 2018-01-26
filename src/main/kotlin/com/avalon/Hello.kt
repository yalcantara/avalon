package com.avalon

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.nd4j.shade.jackson.databind.ObjectMapper
import java.io.File
import java.util.Map

fun main(args: Array<String>) {
    val om = jacksonObjectMapper()

    var list: Array<Map<Any, Any>> = om.readValue(File("files/sp_posts_small.json"))

    var car = list[0]["car"]

    println(car)


}

