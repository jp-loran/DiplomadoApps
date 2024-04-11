package com.jploran.p1_m6.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jploran.p1_m6.data.db.model.CarEntity

@Dao
interface CarDao {
    @Insert
    suspend fun insertCar(car:CarEntity)
    @Query("select * from cars_data_table")
    suspend fun getCars(): List<CarEntity>
    @Update
    suspend fun updateCar(car:CarEntity)
    @Delete
    suspend fun deleteCar(car:CarEntity)
}