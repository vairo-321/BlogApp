package com.blogApp.ui.auth

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
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
import com.blogApp.data.remote.auth.AuthDataSource
import com.blogApp.databinding.FragmentSetupProfileBinding
import com.blogApp.domain.auth.AuthRepoImpl
import com.blogApp.presentation.auth.AuthViewModel
import com.blogApp.presentation.auth.AuthViewModelFactory

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }
    private var bitmap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSetupProfileBinding.bind(view)

        binding.profilePicture.setOnClickListener {

            val intentPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                getPhoto.launch(intentPicture)
            } catch (error: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error, no se encontro camara: ${error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            val username = binding.etextUsername.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()

            bitmap?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(it, username)
                        .observe(viewLifecycleOwner, Observer { result ->
                            when (result) {
                                is Result.Loading -> {
                                    alertDialog.show()
                                }
                                is Result.Success -> {
                                    alertDialog.dismiss()
                                    findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                                }
                                is Result.Failure -> {
                                    alertDialog.dismiss()
                                }
                            }
                        })
                }
            }
        }

    }

    private val getPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                bitmap = imageBitmap
                binding.profilePicture.setImageBitmap(bitmap)
            }
        }

}