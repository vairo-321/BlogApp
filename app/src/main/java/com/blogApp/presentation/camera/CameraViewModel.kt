package com.blogApp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.blogApp.core.Result
import com.blogApp.domain.camera.CameraRepo
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo): ViewModel() {

    fun uploadPhoto(imageBitmal: Bitmap, description: String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmal, description)))
        }catch (error: Exception){
            emit(Result.Failure(error))
        }
    }
}

class CameraViewModelFactory(private val repo: CameraRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(repo) as T
    }
}