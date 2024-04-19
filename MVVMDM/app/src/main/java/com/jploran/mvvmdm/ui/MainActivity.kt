package com.jploran.mvvmdm.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jploran.mvvmdm.data.remote.model.AnimeDto
import com.jploran.mvvmdm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var animes = mutableListOf<AnimeDto>()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myAdapter = AnimesAdapter(animes){

        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = myAdapter
        }

        binding.btnAdd.setOnClickListener{
            mainViewModel.getAnime()
            binding.btnAdd.isEnabled = false
            binding.pbDownload.visibility = View.VISIBLE
        }

        mainViewModel.anime.observe(this, Observer { anime ->
            animes.add(anime)
            myAdapter.notifyItemInserted(animes.size-1)
        })
    }
}