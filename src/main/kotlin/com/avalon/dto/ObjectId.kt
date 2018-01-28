package com.avalon.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ObjectId {

    @JsonProperty("\$oid")
    var oid:String? = null
}