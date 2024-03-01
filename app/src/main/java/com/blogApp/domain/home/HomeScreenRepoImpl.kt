package com.blogApp.domain.home

import com.blogApp.core.Result
import com.blogApp.data.model.Post
import com.blogApp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPost(): Result<List<Post>> = dataSource.getLatestPost()
}