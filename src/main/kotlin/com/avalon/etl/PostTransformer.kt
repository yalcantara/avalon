package com.avalon.etl

import com.avalon.dto.Post

class PostTransformer {




    constructor(){

    }



    fun toMap(posts: List<Post>):List<Map<String, Any?>>{

        val ans = ArrayList<Map<String, Any?>>()

        for(i in 0 until posts.size){
            val post = posts[i]
            val map = toMap(post)
            ans.add(map)
        }

        return ans
    }


    fun toMap(post: Post): Map<String, Any?>{


        val m = LinkedHashMap<String, Any?>()


        m["currency"] = post.currency
        m["price"] = post.price
        m["creationDate"] = post.creationDate

        m["contact.type"] = post.contact?.type

        m["car.brand"] = post.car?.brand
        m["car.model"] = post.car?.model
        m["car.edition"] = post.car?.edition
        m["car.year"] = post.car?.year
        m["car.exteriorColorStr"] = post.car?.exteriorColorStr
        m["car.transmission"] = post.car?.transmission
        m["car.fuelType"] = post.car?.fuelType
        m["car.cylinders"] = post.car?.cylinders
        m["car.litres"] = post.car?.litres
        m["car.litresStr"] = post.car?.litresStr
        m["car.layout"] = post.car?.layout
        m["car.doors"] = post.car?.doors
        m["car.passengers"] = post.car?.passengers
        m["car.type"] = post.car?.type
        m["car.interiorColorStr"] = post.car?.interiorColorStr


        return m
    }
}