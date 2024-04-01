package com.blogApp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.blogApp.R
import com.blogApp.databinding.FragmentLoginBinding
import com.blogApp.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        inflateUser()

    }

    private fun inflateUser() {
        firebaseAuth.currentUser?.let {user ->
            Glide.with(this).load(user.photoUrl).centerCrop().into(binding.imgProfilePicture)
            binding.txtProfileName.text = user.displayName
        }
    }
}