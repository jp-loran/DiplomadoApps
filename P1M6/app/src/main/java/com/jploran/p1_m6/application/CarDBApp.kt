package com.jploran.p1_m6.application

import android.app.Application
import com.jploran.p1_m6.data.CarRepository
import com.jploran.p1_m6.data.db.CarDatabase

class CarDBApp: Application() {
    private val database by lazy{
        CarDatabase.getDatabase(this@CarDBApp)
    }
    val repository by lazy {
        CarRepository(database.carDao())
    }
}
