package com.jploran.videogamesrf.data

import com.jploran.videogamesrf.data.remote.GamesApi
import com.jploran.videogamesrf.data.remote.model.GameDetailDto
import com.jploran.videogamesrf.data.remote.model.GameDto
import retrofit2.Call
import retrofit2.Retrofit

class GameRepository(private val retrofit: Retrofit) {
    private val gamesApi: GamesApi = retrofit.create(GamesApi::class.java)

    fun getGames(url: String?): Call<List<GameDto>> = gamesApi.getGames(url)
    fun getGameDetail(id:String?): Call<GameDetailDto> = gamesApi.getGameDetail(id)
}