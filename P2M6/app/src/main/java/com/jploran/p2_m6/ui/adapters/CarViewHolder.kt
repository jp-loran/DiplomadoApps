package com.jploran.p2_m6.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.jploran.p2_m6.data.remote.model.CarDto
import com.jploran.p2_m6.databinding.CarElementBinding

class CarViewHolder(private var binding: CarElementBinding): RecyclerView.ViewHolder(binding.root) {

    val ivThumbnail = binding.ivThumbnail

    fun bind(car: CarDto){
        binding.tvModel.text = car.model
        binding.tvBrand.text = car.brand
        binding.tvYear.text = car.year
    }
}