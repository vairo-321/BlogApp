package com.blogApp.domain

import com.blogApp.core.Resource
import com.blogApp.data.model.Post
import com.blogApp.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPost(): Resource<List<Post>> = dataSource.getLatestPost()
}