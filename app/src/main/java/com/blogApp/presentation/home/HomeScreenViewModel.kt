package com.blogApp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.blogApp.core.Result
import com.blogApp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers

class HomeScreenViewModel(private val repo: HomeScreenRepo): ViewModel() {
    fun fetchLatestPost() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Result.Loading())

        kotlin.runCatching {
            repo.getLatestPost()
        }.onSuccess {postListResult ->
            emit(postListResult)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }
}

class HomeScreenViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }

}



