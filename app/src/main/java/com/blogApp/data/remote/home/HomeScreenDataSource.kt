package com.blogApp.data.remote.home

import com.blogApp.core.Result
import com.blogApp.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeScreenDataSource {

    suspend fun getLatestPost(): Result<List<Post>> {
        val postList = mutableListOf<Post>()

        withContext(Dispatchers.IO){
            val querySnapshot = FirebaseFirestore.getInstance().collection("posts").orderBy("created_at", Query.Direction.DESCENDING).get().await()
            for (post in querySnapshot.documents){
                post.toObject(Post::class.java)?.let {fbPost ->
                    //Estima el tiempo del post para solucionar el problema de delay en los post recientes
                    fbPost.apply { created_at = post.getTimestamp("created_at", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)?.toDate() }
                    postList.add(fbPost)
                }
            }
        }
        return Result.Success(postList)
    }
}