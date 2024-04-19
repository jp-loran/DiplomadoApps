package com.jploran.uploaddm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.jploran.uploaddm.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }

    fun click(view: View){
        val fileName = "dog.png"
        val fileExt = fileName.substringAfterLast(".","")

        val file = File(cacheDir, "JuanPabloAlvarez.$fileExt")
        file.createNewFile()

        file.outputStream().use { fos->
            assets.open(fileName).copyTo(fos)
        }

        mainViewModel.uploadImage(file)
        mainViewModel.message.observe(this, Observer {message->
            binding.button.isEnabled=false

            binding.progressBar.visibility = View.INVISIBLE
            binding.tvProgress.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle("Aviso")
                .setMessage(message)
                .setPositiveButton("Aceptar"){dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        })
        mainViewModel.progress.observe(this, Observer {progress ->

            binding.apply {
                progressBar.visibility = View.VISIBLE
                tvProgress.visibility = View.VISIBLE

                progressBar.progress = progress
                tvProgress.text = "${progress.toString()}%"
            }

        })
    }
}