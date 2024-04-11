package com.jploran.videogamesdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.jploran.videogamesdb.application.VideoGamesDBApp
import com.jploran.videogamesdb.data.GameRepository
import com.jploran.videogamesdb.data.db.model.GameEntity
import com.jploran.videogamesdb.databinding.GameDialogBinding
import kotlinx.coroutines.launch
import java.io.IOException

class GameDialog(
    private var newGame: Boolean = true,
    private var game: GameEntity = GameEntity(
        title="",
        genre="",
        developer=""
    ), private val updateUI: () -> Unit,
    private val message: (String) -> Unit
): DialogFragment() {
    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private var saveButton: Button? = null
    private lateinit var repository: GameRepository
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)
        builder=AlertDialog.Builder(requireContext())
        repository = (requireContext().applicationContext as VideoGamesDBApp).repository

        binding.apply{
            binding.tietTitle.setText(game.title)
            binding.tietGenre.setText(game.genre)
            binding.tietDeveloper.setText(game.developer)
        }

        dialog = if(newGame)
            buildDialog("Guardar", "Cancelar",{
                //Gurdar
                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch{
                        repository.insertGame(game)
                    }

                    Toast.makeText(requireContext(), "Juego guardado", Toast.LENGTH_SHORT).show()
                    updateUI()
                }catch(e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            },{
                //Cancelar
            })
        else
            buildDialog("Actualizar", "Borrar",{

                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch{
                        repository.updateGame(game)
                    }

                    Toast.makeText(requireContext(), "Juego actualizado", Toast.LENGTH_SHORT).show()
                    updateUI()
                }catch(e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            },{
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${game.title}?")
                    .setPositiveButton("Aceptar"){ _, _ ->
                        try {
                            lifecycleScope.launch {
                                repository.deleteGame(game)
                            }
                            message("Juego eliminado")
                            updateUI()
                        }catch(e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(
                                requireContext(),
                                "Error al borrar el juego",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton("Cancelar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            })
      /*  dialog = builder.setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton("Guardar", DialogInterface.OnClickListener{ _, _->
                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch{
                        repository.insertGame(game)
                    }

                    Toast.makeText(requireContext(), "Juego guardado", Toast.LENGTH_SHORT).show()
                    updateUI()
                }catch(e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            })
            .setNegativeButton("Cancelar"){_, _->

            }.create() */

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        val alertDialog = dialog as AlertDialog
        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled=false
        binding.tietTitle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //e
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               // e
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled= validateFields()
            }

        })
        binding.tietGenre.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //e
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //e
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled= validateFields()
            }

        })
        binding.tietDeveloper.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //e
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //e
            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled= validateFields()
            }

        })
    }

    private  fun validateFields(): Boolean =
        (binding.tietTitle.text.toString().isNotEmpty() &&
                binding.tietGenre.text.toString().isNotEmpty() &&
                binding.tietDeveloper.text.toString().isNotEmpty())

    private fun buildDialog(btn1Text: String, btn2Text: String, positiveButton: () -> Unit, negativeButton: () -> Unit ): Dialog =
        builder.setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton(btn1Text){_,_ ->
                positiveButton()
            }.setNegativeButton(btn2Text){_,_->
                negativeButton()
            }.create()

}