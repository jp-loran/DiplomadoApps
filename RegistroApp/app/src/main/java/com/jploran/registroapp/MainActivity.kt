package com.jploran.registroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val etName = findViewById<EditText>(R.id.etName)
        val etLastname = findViewById<EditText>(R.id.etLastname)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val rgGenre = findViewById<RadioGroup>(R.id.rgGenre)

        btnRegister.setOnClickListener {
            if (etName.text.isNotEmpty() && etLastname.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()) {
                val selectedGenderId = rgGenre.checkedRadioButtonId
                val gender = findViewById<RadioButton>(selectedGenderId)?.text.toString()
                val intent = Intent(this, DisplayActivity::class.java)

                intent.putExtra("nombre", etName.text.toString())
                intent.putExtra("apellido", etLastname.text.toString())
                intent.putExtra("email", email.text.toString())
                intent.putExtra("contraseña", password.text.toString())
                intent.putExtra("genero", gender)

                startActivity(intent)
            }else{
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_LONG).show()
            }

        }

    }

}