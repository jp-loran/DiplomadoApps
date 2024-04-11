package com.jploran.p1_m6.data

import com.jploran.p1_m6.data.db.CarDao
import com.jploran.p1_m6.data.db.model.CarEntity

class CarRepository(private val carDao: CarDao) {

    suspend fun insertCar(car: CarEntity){
        carDao.insertCar(car)
    }

    suspend fun getCars(): List<CarEntity>{
        return carDao.getCars()
    }

    suspend fun updateCar(car: CarEntity){
        carDao.updateCar(car)
    }

    suspend fun deleteCar(car: CarEntity){
        carDao.deleteCar(car)
    }

}