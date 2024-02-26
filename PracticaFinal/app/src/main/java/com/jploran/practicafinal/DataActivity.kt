package com.jploran.practicafinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jploran.practicafinal.databinding.ActivityDataBinding
import com.jploran.practicafinal.databinding.FragmentRegisterBinding

class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME")
        val lastName = intent.getStringExtra("EXTRA_LAST_NAME")
        val email = intent.getStringExtra("EXTRA_EMAIL")
        val genre = intent.getStringExtra("EXTRA_GENRE")

        binding.tvNameDisplay.text = name
        binding.tvLastNameDisplay.text = lastName
        binding.tvEmailDisplay.text = email
        binding.tvGenreDisplay.text = genre

    }
}