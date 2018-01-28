package com.avalon.dto


class Post {


    var _id: ObjectId? = null
    var extractionId: ObjectId? = null
    var postId: Long? = null
    var url: String? = null
    var car: Car? = null
    var currency: String? = null
    var price: Double? = null
    var images: Set<String>? = null
    var accessories: Set<String>? = null
    var contact: Contact? = null
    var notes: String? = null
    var creationDate: String?  = null
    var creationDateString: String? = null

}