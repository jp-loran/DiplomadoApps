package com.jploran.ifiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jploran.ifiles.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            clickSave(binding.root)
        }

        //miToast("Hola!")

        //miToast("Hola 2", Toast.LENGTH_LONG)

    }

    fun clickSave(view: View) {
        //Clic del botón save

        view.hideKeyboard()

        if(binding.tietText.text.toString().isNotEmpty()){

            //Codificando a bytes la cadena de texto a almacenar
            val bytesToSave = binding.tietText.text.toString().encodeToByteArray()

            try{
                val file = File(filesDir,"mi_archivo.txt")

                if(!file.exists()){
                    file.createNewFile()
                }

                /*val fos = FileOutputStream(file, true)
                fos.write(bytesToSave)
                fos.close()*/

                file.writeBytes(bytesToSave)
                sbMessage(
                    binding.clRoot,
                    "Información almacenada",
                    bgColor="#50C228"
                )
            }catch(e: Exception){
                //Manejar la excepción
            }


        }else{
            sbMessage(
                binding.clRoot,
                "Ingrese la información a almacenar"
            )

            binding.tietText.error = "No puede estar vacío"
            binding.tietText.requestFocus()
        }

    }

    fun clickRead(view: View) {
        //Clic del botón read

        view.hideKeyboard()
    }
}