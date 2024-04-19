package com.jploran.p2_m6.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jploran.p2_m6.data.remote.model.CarDto
import com.jploran.p2_m6.databinding.CarElementBinding

class CarAdapter(private val cars: List<CarDto>, private val onCarClicked: (CarDto) -> Unit): RecyclerView.Adapter<CarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CarElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarViewHolder(binding)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car)

        print(car.thumbnail)
        Glide.with(holder.itemView.context)
            .load(car.thumbnail)
            .into(holder.ivThumbnail)

        holder.itemView.setOnClickListener {
            onCarClicked(car)
        }
    }


}