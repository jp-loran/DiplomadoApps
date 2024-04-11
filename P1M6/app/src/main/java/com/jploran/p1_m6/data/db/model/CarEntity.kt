package com.jploran.p1_m6.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars_data_table")
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="car_id")
    val id: Long = 0,
    @ColumnInfo(name="car_brand")
    var brand: String,
    @ColumnInfo(name="car_model")
    var model:String,
    @ColumnInfo(name="car_hp")
    var hp: Int,
    @ColumnInfo(name="car_price")
    var price: Int
)