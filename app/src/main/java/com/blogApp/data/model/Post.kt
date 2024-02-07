package com.blogApp.data.model

import com.google.firebase.Timestamp

data class Post(
    val profilePicture: String,
    val profileName: String,
    val postTimestamp: Timestamp? = null,
    val postImage: String
)




