package com.jploran.registroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val email = intent.getStringExtra("email")
        val genero = intent.getStringExtra("genero")


        val textViewNombre = findViewById<TextView>(R.id.tvName)
        val textViewApellido = findViewById<TextView>(R.id.tvLastName)
        val textViewEmail = findViewById<TextView>(R.id.tvEmail)
        val textViewGenero = findViewById<TextView>(R.id.tvGenre)

        textViewNombre.text = "Nombre: $nombre"
        textViewApellido.text = "Apellido: $apellido"
        textViewEmail.text = "Email: $email"
        textViewGenero.text = "Genero: $genero"
    }
}