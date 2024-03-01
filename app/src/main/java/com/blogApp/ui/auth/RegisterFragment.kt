package com.blogApp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blogApp.R
import com.blogApp.core.Result
import com.blogApp.data.remote.auth.AuthDataSource
import com.blogApp.databinding.FragmentRegisterBinding
import com.blogApp.domain.auth.AuthRepoImpl
import com.blogApp.presentation.auth.AuthViewModel
import com.blogApp.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {


    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp(){

        binding.btnSignup.setOnClickListener{

            val userName = binding.editTextUserName.text.toString().trim()
            val eMail = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

            if (validateUserData(password, confirmPassword, userName, eMail)) return@setOnClickListener
            createUser(eMail, password, userName)
        }
    }

    private fun createUser(eMail: String, password: String, userName: String) {
        viewModel.signUp(eMail, password, userName).observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }

                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun validateUserData(password: String, confirmPassword: String, userName: String, eMail: String): Boolean {
        if (password != confirmPassword) {
            binding.editTextPassword.error = "Password does not match"
            binding.editTextConfirmPassword.error = "Password does not match"
            return true
        }

        if (userName.isEmpty()) {
            binding.editTextUserName.error = "User Name is empty"
            return true
        }

        if (eMail.isEmpty()) {
            binding.editTextEmail.error = "eMail is empty"
            return true
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }

        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Password is empty"
            return true
        }
        return false
    }
}