package com.blogApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blogApp.core.hide
import com.blogApp.core.show
import com.blogApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when(destination.id){
                R.id.loginFragment -> {binding.bottomNavigationView.hide()}
                R.id.registerFragment -> {binding.bottomNavigationView.hide()}
                R.id.setupProfileFragment -> {binding.bottomNavigationView.hide()}
                else -> {binding.bottomNavigationView.show()}
            }
        }
    }
}