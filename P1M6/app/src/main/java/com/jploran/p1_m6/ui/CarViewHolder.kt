package com.jploran.p1_m6.ui

import androidx.recyclerview.widget.RecyclerView
import com.jploran.p1_m6.R
import com.jploran.p1_m6.data.db.model.CarEntity
import com.jploran.p1_m6.databinding.CarElementBinding

class CarViewHolder(private val binding: CarElementBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(car: CarEntity){
        updateHeaderImage(car.brand)
        binding.apply {
            tvTitle.text= car.brand
            tvModel.text = car.model
            tvHp.text = "Potencia: ${car.hp.toString()} HP"
            tvPrice.text = "$${car.price.toString()}"
        }
    }
    private fun updateHeaderImage(brand: String) {
        val imageResId = when (brand) {
            "BMW" -> R.drawable.bmw
            "Audi" -> R.drawable.audi
            "Mercedes" -> R.drawable.mercedes
            else -> 0
        }
        if (imageResId != 0) {
            binding.ivIcon.setImageResource(imageResId)
        }
    }
}