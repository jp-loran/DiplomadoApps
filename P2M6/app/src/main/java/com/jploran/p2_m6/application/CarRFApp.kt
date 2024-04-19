package com.jploran.p2_m6.application

import android.app.Application
import com.jploran.p2_m6.data.CarRepository
import com.jploran.p2_m6.data.remote.RetrofitHelper

class CarRFApp: Application() {
    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }
    val repository by lazy { CarRepository(retrofit) }
}