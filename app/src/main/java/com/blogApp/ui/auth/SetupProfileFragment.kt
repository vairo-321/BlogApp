package com.blogApp.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blogApp.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSetupProfileBinding.bind(view)


    }

}