package com.jploran.videogamesdb.data

import com.jploran.videogamesdb.data.db.GameDao
import com.jploran.videogamesdb.data.db.model.GameEntity

class GameRepository(private val gameDao: GameDao) {
    suspend fun insertGame(game: GameEntity){
        gameDao.insertGame(game)
    }

    suspend fun getAllGames(): List<GameEntity>{
        return gameDao.getAllGames()
    }

    suspend fun updateGame(game: GameEntity){
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: GameEntity){
        gameDao.deleteGame(game)
    }
}