package com.blogApp.ui.camera

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blogApp.R
import com.blogApp.core.Result
import com.blogApp.data.remote.camera.CameraDataSource
import com.blogApp.databinding.FragmentCameraBinding
import com.blogApp.domain.camera.CameraRepoImpl
import com.blogApp.presentation.camera.CameraViewModel
import com.blogApp.presentation.camera.CameraViewModelFactory


class CameraFragment : Fragment(R.layout.fragment_camera) {

    // private val REQUEST_IMAGE_PICTURE = 1     <----- Deprecated<----- Deprecated<----- Deprecated
    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImpl(
                CameraDataSource()
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCameraBinding.bind(view)

        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getImagePhoto.launch(takePicture)
            //startActivityForResult(takePicture, REQUEST_IMAGE_PICTURE)    <----- Deprecated<----- Deprecated<----- Deprecated
        } catch (error: Exception) {
            Toast.makeText(
                requireContext(),
                "Error, no se encontro camara: ${error}",
                Toast.LENGTH_LONG
            ).show()
        }


        binding.btnUploadPhoto.setOnClickListener {
            val textDescript = binding.etxPhotoDescription.text.toString().trim()
            bitmap?.let {
                viewModel.uploadPhoto(it, textDescript)
                    .observe(/* owner = */ viewLifecycleOwner, /* observer = */
                        Observer { result ->
                            when (result) {
                                is Result.Loading -> {
                                    Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_LONG).show()
                                }
                                is Result.Success -> {
                                    findNavController().popBackStack()
                                    //findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                                }
                                is Result.Failure -> {
                                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG).show()
                                }

                            }
                        })
            }


        }


    }

    private val getImagePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                binding.imgAddPhoto.setImageBitmap(imageBitmap)
                bitmap = imageBitmap
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

