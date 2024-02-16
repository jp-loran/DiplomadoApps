package com.jploran.componentesgraficos.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.jploran.componentesgraficos.R

class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        val sendEmail = findViewById<Button>(R.id.btnContinue)

        sendEmail.setOnClickListener {
            Toast.makeText(this, "Email enviado", Toast.LENGTH_SHORT).show()
        }


    }
}