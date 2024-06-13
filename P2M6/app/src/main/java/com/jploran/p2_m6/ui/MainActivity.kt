package com.jploran.p2_m6.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jploran.p2_m6.R
import com.jploran.p2_m6.data.CarRepository
import com.jploran.p2_m6.data.remote.RetrofitHelper
import com.jploran.p2_m6.data.remote.model.CarDto
import com.jploran.p2_m6.databinding.ActivityMainBinding
import com.jploran.p2_m6.ui.fragments.CarListFragment
import com.jploran.p2_m6.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarListFragment())
                .commit()
        }
    }
}