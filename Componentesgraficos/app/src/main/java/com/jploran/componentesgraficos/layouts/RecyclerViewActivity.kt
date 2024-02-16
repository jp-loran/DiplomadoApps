package com.jploran.componentesgraficos.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jploran.cineexplorer.layout.recyclerView.ItemAdapter
import com.jploran.componentesgraficos.R

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val shoppingList = findViewById<RecyclerView>(R.id.shoppingList)

        val superList = arrayListOf("Pollo", "Carne", "Verdura", "Agua", "Shampoo")
        val adapter = ItemAdapter(superList)

        shoppingList.adapter=adapter
        shoppingList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false, )
    }
}
