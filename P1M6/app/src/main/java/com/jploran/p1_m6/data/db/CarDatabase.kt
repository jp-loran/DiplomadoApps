package com.jploran.p1_m6.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jploran.p1_m6.data.db.model.CarEntity

@Database(
    entities = [CarEntity::class],
    version=1,
    exportSchema = true
)

abstract  class CarDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object{
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "cars_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }



}