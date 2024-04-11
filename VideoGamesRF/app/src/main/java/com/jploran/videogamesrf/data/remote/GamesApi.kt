package com.jploran.videogamesrf.data.remote

import com.jploran.videogamesrf.data.remote.model.GameDetailDto
import com.jploran.videogamesrf.data.remote.model.GameDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface GamesApi {
    @GET
    fun getGames(@Url url: String?): Call<List<GameDto>>
    @GET("cm/games/game_detail.php?")
    fun getGameDetail(@Query("id") id:String?): Call<GameDetailDto>
    @GET("cm/games/{id}/{name}")
    fun getGameTest(@Path("id") id:String?, @Path("id")name: String?): Call<GameDetailDto>

}