package com.jploran.videogamesdb.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jploran.videogamesdb.data.db.model.GameEntity
import com.jploran.videogamesdb.databinding.GameElementBinding

class GameViewHolder (private val binding: GameElementBinding): RecyclerView.ViewHolder(binding.root) {
    val ivIcon = binding.ivIcon
    fun bind(game: GameEntity){
        binding.apply {
            tvTitle.text= game.title
            tvGenre.text = game.genre
            tvDeveloper.text = game.developer
        }


    }
}