package com.blogApp.domain

import com.blogApp.core.Resource
import com.blogApp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Resource<List<Post>>
}