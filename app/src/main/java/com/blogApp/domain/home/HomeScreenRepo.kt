package com.blogApp.domain.home

import com.blogApp.core.Result
import com.blogApp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>
}