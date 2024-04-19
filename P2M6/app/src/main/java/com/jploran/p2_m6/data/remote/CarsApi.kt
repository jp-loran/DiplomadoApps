package com.jploran.p2_m6.data.remote

import com.jploran.p2_m6.data.remote.model.CarDetailDto
import com.jploran.p2_m6.data.remote.model.CarDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CarsApi {
    @GET
    fun getCars(@Url url: String?): Call<List<CarDto>>
    @GET("cars/car_detail/{id}")
    fun getCarDetail(@Path("id") id: String?): Call<CarDetailDto>
}