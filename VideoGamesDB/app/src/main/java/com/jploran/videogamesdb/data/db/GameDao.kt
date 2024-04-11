package com.jploran.videogamesdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jploran.videogamesdb.data.db.model.GameEntity
import com.jploran.videogamesdb.util.Constants

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game:GameEntity)

    @Query("select * from ${Constants.DATABASE_GAME_TABLE}")
    suspend fun getAllGames(): List<GameEntity>

    @Update
    suspend fun updateGame(game: GameEntity)

    @Delete
    suspend fun deleteGame(game: GameEntity)
}