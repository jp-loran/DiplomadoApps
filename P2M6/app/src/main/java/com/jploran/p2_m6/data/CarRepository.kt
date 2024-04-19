package com.jploran.p2_m6.data

import com.jploran.p2_m6.data.remote.CarsApi
import com.jploran.p2_m6.data.remote.model.CarDetailDto
import com.jploran.p2_m6.data.remote.model.CarDto
import retrofit2.Call
import retrofit2.Retrofit

class CarRepository(private val retrofit: Retrofit) {
    private val carsApi: CarsApi = retrofit.create(CarsApi::class.java)
    fun getCars(url: String?): Call<List<CarDto>> = carsApi.getCars(url)
    fun getCarDetailApiary(id: String?): Call<CarDetailDto> = carsApi.getCarDetail(id)
}