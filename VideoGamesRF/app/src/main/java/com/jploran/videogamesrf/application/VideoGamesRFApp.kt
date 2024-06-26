package com.jploran.videogamesrf.application

import android.app.Application
import com.jploran.videogamesrf.data.GameRepository
import com.jploran.videogamesrf.data.remote.RetrofitHelper

class VideoGamesRFApp : Application() {
    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }
    val repository by lazy {GameRepository(retrofit)}
}