package com.jploran.p1_m6.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jploran.p1_m6.data.db.model.CarEntity
import com.jploran.p1_m6.databinding.CarElementBinding

class CarAdapter(private val onCarClicked: (CarEntity) ->Unit): RecyclerView.Adapter<CarViewHolder>() {
    private var cars: List<CarEntity> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CarElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car)
        holder.itemView.setOnClickListener{
            onCarClicked(car)
        }
    }

    fun updateList(list: List<CarEntity>){
        cars=list
        notifyDataSetChanged()
    }

}