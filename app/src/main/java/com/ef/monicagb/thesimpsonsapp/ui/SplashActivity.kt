package com.ef.monicagb.thesimpsonsapp.ui

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**/
        HandlerCompat.postDelayed(Handler(), {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, null, 5000)
    }
}