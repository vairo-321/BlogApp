package com.blogApp.data.remote

import com.blogApp.core.Resource
import com.blogApp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPost(): Resource<List<Post>>{
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {fbPost ->
                postList.add(fbPost)
            }
        }
        return Resource.Success(postList)
    }
}