package com.jploran.videogamesrf.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.jploran.videogamesrf.data.remote.model.GameDto
import com.jploran.videogamesrf.databinding.GameElementBinding

class GameViewHolder(private var binding: GameElementBinding) : RecyclerView.ViewHolder(binding.root){
    val ivThumbnail = binding.ivThumbnail
    fun bind(game: GameDto){
        binding.tvTitle.text = game.title
    }
}