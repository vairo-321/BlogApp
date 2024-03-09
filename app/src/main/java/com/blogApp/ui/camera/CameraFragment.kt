package com.blogApp.ui.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.blogApp.R
import com.blogApp.databinding.FragmentCameraBinding


class CameraFragment : Fragment(R.layout.fragment_camera) {

   // private val REQUEST_IMAGE_PICTURE = 1     <----- Deprecated<----- Deprecated<----- Deprecated
    private lateinit var binding: FragmentCameraBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCameraBinding.bind(view)


        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getImagePhoto.launch(takePicture)
            //startActivityForResult(takePicture, REQUEST_IMAGE_PICTURE)    <----- Deprecated<----- Deprecated<----- Deprecated
        }catch (error: Exception){
            Toast.makeText(requireContext(), "Error, no se encontro camara: ${error}", Toast.LENGTH_LONG).show()
        }

    }

    private val getImagePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK){
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.imgAddPhoto.setImageBitmap(imageBitmap)
        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {           <----- Deprecated<----- Deprecated<----- Deprecated
        super.onActivityResult(requestCode, resultCode, data)                               <----- Deprecated<----- Deprecated<----- Deprecated

        if(requestCode == REQUEST_IMAGE_PICTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap                   <----- Deprecated<----- Deprecated<----- Deprecated
            binding.imgAddPhoto.setImageBitmap(imageBitmap)                             <----- Deprecated<----- Deprecated<----- Deprecated
        }
    }*/
}

