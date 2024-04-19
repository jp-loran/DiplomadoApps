package com.jploran.uploaddm.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object{ //patron singleton
        fun getRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://www.serverbpw.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}