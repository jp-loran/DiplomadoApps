package com.jploran.videogamesdb.application

import android.app.Application
import com.jploran.videogamesdb.data.GameRepository
import com.jploran.videogamesdb.data.db.GameDatabase

class VideoGamesDBApp: Application(){
     private val database by lazy {
         GameDatabase.getDatabase(this@VideoGamesDBApp)
     }

    val repository by lazy{
        GameRepository(database.gameDao())
    }
}