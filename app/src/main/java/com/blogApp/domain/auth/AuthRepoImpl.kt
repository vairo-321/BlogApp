package com.blogApp.domain.auth

import android.graphics.Bitmap
import com.blogApp.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)

    override suspend fun signUp(email: String, password: String, completeName: String): FirebaseUser? = dataSource.signUp(email, password, completeName)

    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) = dataSource.updateUserProfile(imageBitmap, username)
}