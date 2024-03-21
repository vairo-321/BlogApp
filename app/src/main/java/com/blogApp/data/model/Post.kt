package com.blogApp.data.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Post(
    @Exclude @JvmField
    var id: String = "",
    //se delega a que el atributo siguiente lo llene el server.. en este caso con Date
    @ServerTimestamp
    var created_at: Date? = null,
    val poster: Poster? = null,
    val post_image: String = "",
    val post_description: String = "",
    @Exclude @JvmField
    var liked: Boolean = false,
    val likes: Long = 0
)

data class Poster(val username: String? = "", val uid: String? = null, val profile_picture: String = "")




