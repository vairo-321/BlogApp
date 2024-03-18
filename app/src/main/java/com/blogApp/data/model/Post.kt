package com.blogApp.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    //se delega a que el atributo siguiente lo llene el server.. en este caso con Date
    @ServerTimestamp
    var created_at: Date? = null,
    val post_image: String = "",
    val post_description: String = "",
    val uid:String = ""
)




