package com.blogApp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blogApp.R
import com.blogApp.core.Result
import com.blogApp.core.hide
import com.blogApp.core.show
import com.blogApp.data.model.Post
import com.blogApp.data.remote.home.HomeScreenDataSource
import com.blogApp.databinding.FragmentHomeScreenBinding
import com.blogApp.domain.home.HomeScreenRepoImpl
import com.blogApp.presentation.home.HomeScreenViewModel
import com.blogApp.presentation.home.HomeScreenViewModelFactory
import com.blogApp.ui.home.adapter.HomeScreenAdapter
import com.blogApp.ui.home.adapter.OnPostClickListener

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenRepoImpl(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        //clearStackFragment()

        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer{result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    if (result.data.isEmpty()){
                        binding.emptyContainer.show()
                        return@Observer
                    }else{
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onLikeButtonCLick(post: Post, liked: Boolean) {
        viewModel.registerLikeButtonState(post.id, liked).observe(viewLifecycleOwner, Observer {result ->
            when(result){
                is Result.Loading -> {}

                is Result.Success -> {}

                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    //private fun clearStackFragment(){
    //    val navController = findNavController()

    //    navController.popBackStack(R.id.homeScreenFragment, false)
    //}
}