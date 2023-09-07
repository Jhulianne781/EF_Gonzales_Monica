package com.ef.monicagb.thesimpsonsapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUserLoggedInState()
        /*Código para el BottomNavigation*/
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_personajes) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)
    }

    private fun checkUserLoggedInState() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userLoggedIn = sharedPreferences.getBoolean("user_logged_in", false)
        if (!userLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}