package com.jploran.videogamesrf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jploran.videogamesrf.R
import com.jploran.videogamesrf.data.GameRepository
import com.jploran.videogamesrf.data.remote.RetrofitHelper
import com.jploran.videogamesrf.data.remote.model.GameDto
import com.jploran.videogamesrf.databinding.ActivityMainBinding
import com.jploran.videogamesrf.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = RetrofitHelper().getRetrofit()
        repository = GameRepository(retrofit)

        lifecycleScope.launch {
            val call: Call<List<GameDto>> = repository.getGames("cm/games/games_list.php")
            call.enqueue(object: Callback<List<GameDto>>{
                override fun onResponse(p0: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                    Log.d(Constants.LOGTAG, "Respuesta recibida: ${response.body()}")
                }

                override fun onFailure(p0: Call<List<GameDto>>, error: Throwable) {
                    Toast.makeText(this@MainActivity, "Error e la conexión: ${error.message}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}