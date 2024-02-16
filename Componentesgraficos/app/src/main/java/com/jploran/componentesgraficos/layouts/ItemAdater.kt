package com.jploran.cineexplorer.layout.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jploran.componentesgraficos.R

class ItemAdapter(private val list: ArrayList<String>) : RecyclerView.Adapter<ItemViewHolder>() {

    var onItemSelected: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.render(list[position], onItemSelected)
    }
}

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
    val ivUser = view.findViewById<ImageView>(R.id.ivItem)
    val tvUser = view.findViewById<TextView>(R.id.tvItem)
    val root = view.findViewById<ConstraintLayout>(R.id.root)

    fun render(item:String, onItemSelected : ((String) -> Unit)?){
        tvUser.text = item
    }
}