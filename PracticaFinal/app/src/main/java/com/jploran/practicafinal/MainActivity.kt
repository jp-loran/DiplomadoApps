package com.jploran.practicafinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jploran.practicafinal.databinding.ActivityMainBinding
import com.jploran.practicafinal.fragments.LoginFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.mainFragment, LoginFragment.newInstance())
            .commit()
    }
}