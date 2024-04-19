package com.jploran.mvvmdm.data.remote

import com.jploran.mvvmdm.data.remote.model.AnimeDto
import kotlinx.coroutines.delay

class AnimeProvider {
    companion object{
        private val animes = mutableListOf<AnimeDto>()

        init{
            for(i in 1..30){
                animes.add(
                    AnimeDto(
                    id=i.toLong(),
                    titulo = "Titulo $i",
                    tipo="Tipo $i",
                    fecha="Fecha $i"
                )
                )
            }
        }

        suspend fun getAnimesApi(): AnimeDto {
            val position = (0..29).random()
            delay((1000..4000).random().toLong())
            return animes[position]
        }
    }
}