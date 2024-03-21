package com.blogApp.data.remote.camera

import android.graphics.Bitmap
import android.net.Uri
import com.blogApp.data.model.Post
import com.blogApp.data.model.Poster
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class CameraDataSource {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String){
        //Aqui se hace referencia al usuario actual y tambien se hace referencia a donde se guardara la foto imageref
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef =  FirebaseStorage.getInstance().reference.child("${user?.uid}/posts/$randomName")
        //Aqio se comprime y se formatea la foto
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        //Aqui se carga la foto en la referencia anterior al Storaged y luego se descarga su URL(metadatos) para guardar en otro lado
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        //Aqui se guarda el Post actualizado
        user?.let {
            it.displayName?.let {displayName ->
                FirebaseFirestore.getInstance().collection("posts").add(Post(
                    poster = Poster(username = displayName, profile_picture = it.photoUrl.toString(), uid = user.uid),
                    post_image = downloadUrl,
                    post_description = description,
                    likes = 0
                ))
            }
        }
    }
}