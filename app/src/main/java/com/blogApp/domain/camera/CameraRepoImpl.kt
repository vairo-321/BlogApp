package com.blogApp.domain.camera

import android.graphics.Bitmap
import com.blogApp.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) = dataSource.uploadPhoto(imageBitmap, description)
}