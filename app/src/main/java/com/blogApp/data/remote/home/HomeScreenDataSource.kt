package com.blogApp.data.remote.home

import com.blogApp.core.Result
import com.blogApp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPost(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents){
            post.toObject(Post::class.java)?.let {fbPost ->
                postList.add(fbPost)
            }
        }
        return Result.Success(postList)
    }
}