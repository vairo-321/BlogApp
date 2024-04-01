package com.blogApp.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.blogApp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {

        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, completeName: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let {uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid).set(User(email, completeName))
        }
        return authResult.user
    }

    suspend fun updateUserProfile(imageBitmap: Bitmap, username: String){
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef =  FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        //sssssssssss
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        //AQUI PROCEDEMOS A ACTUALIZAR EL PERFIL DE USUARIO DE FIREBASE, VER MAS INFO EN DOC FIREBASE
        //https://firebase.google.com/docs/auth/android/manage-users?hl=es-419#kotlin+ktx_3
        val profileUpdate = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(downloadUrl))
            .build()
        user?.updateProfile(profileUpdate)?.await()
    }


}