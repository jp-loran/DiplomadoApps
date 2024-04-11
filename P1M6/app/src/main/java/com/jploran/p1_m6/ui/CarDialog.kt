package com.jploran.p1_m6.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.jploran.p1_m6.R
import com.jploran.p1_m6.application.CarDBApp
import com.jploran.p1_m6.data.CarRepository
import com.jploran.p1_m6.data.db.model.CarEntity
import com.jploran.p1_m6.databinding.CarDialogBinding
import kotlinx.coroutines.launch
import java.io.IOException

class CarDialog(
    private var newCar: Boolean = true,
    private var car: CarEntity = CarEntity(
        brand="",
        model="",
        hp=0,
        price=0
    ),
    private val updateUI: () -> Unit
): DialogFragment() {

    private var _binding: CarDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private var saveButton: Button? = null
    private lateinit var repository: CarRepository


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = CarDialogBinding.inflate(requireActivity().layoutInflater)
        builder = AlertDialog.Builder(requireContext())
        repository = (requireContext().applicationContext as CarDBApp).repository

        initializeBrandDropdown()
        setSpinnerSelection()

        binding.apply {
            binding.tietModel.setText(car.model)
            binding.tietHp.setText(car.hp.toString())
            binding.tietPrice.setText(car.price.toString())
        }

        dialog = if(newCar)
        buildDialog(getString(R.string.save_btn), getString(R.string.cancel_btn),{
            //Gurdar
            car.brand = binding.brandsSpinner.selectedItem.toString()
            car.model = binding.tietModel.text.toString()
            car.hp = binding.tietHp.text.toString().toInt()
            car.price = binding.tietPrice.text.toString().toInt()

            try {
                lifecycleScope.launch{
                    repository.insertCar(car)
                }
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.insert_success), Snackbar.LENGTH_SHORT).show()
                updateUI()
            }catch(e: IOException){
                e.printStackTrace()
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.insert_error), Snackbar.LENGTH_SHORT).show()
            }
        },{
            //Cancelar
        })
        else
            buildDialog(getString(R.string.update_btn), getString(R.string.delete_btn),{

                car.brand = binding.brandsSpinner.selectedItem.toString()
                car.model = binding.tietModel.text.toString()
                car.hp = binding.tietHp.text.toString().toInt()
                car.price = binding.tietPrice.text.toString().toInt()

                try {
                    lifecycleScope.launch{
                        repository.updateCar(car)
                    }
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        getString(R.string.update_success), Snackbar.LENGTH_SHORT).show()
                    updateUI()
                }catch(e: IOException){
                    e.printStackTrace()
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        getString(R.string.update_error), Snackbar.LENGTH_SHORT).show()
                }
            },{
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirm_txt))
                    .setMessage("¿Realmente deseas eliminar el auto ${car.model}?")
                    .setPositiveButton(getString(R.string.accept_btn)){ _, _ ->
                        try {
                            lifecycleScope.launch {
                                repository.deleteCar(car)
                            }
                            updateUI()
                        }catch(e: IOException) {
                            e.printStackTrace()
                            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                getString(R.string.delete_error), Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton(getString(R.string.cancel_btn)){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            })

        return dialog
    }

    private fun setSpinnerSelection() {

        val brandsAdapter = binding.brandsSpinner.adapter
        val position = (brandsAdapter as ArrayAdapter<String>).getPosition(car.brand)

        binding.brandsSpinner.apply {
            if (position >= 0) setSelection(position)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedBrand = parent.getItemAtPosition(position) as String
                    updateHeaderImage(selectedBrand)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun updateHeaderImage(brand: String) {
        val imageResId = when (brand) {
            "BMW" -> R.drawable.bmw
            "Audi" -> R.drawable.audi
            "Mercedes" -> R.drawable.mercedes
            else -> 0
        }
        if (imageResId != 0) {
            binding.ivHeader.setImageResource(imageResId)
        }
    }

    private fun initializeBrandDropdown() {
        val spinner: Spinner = binding.brandsSpinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.brands,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

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

        binding.tietModel.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled=validateFields()
            }
        })

        binding.tietHp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled=validateFields()
            }
        })

        binding.tietPrice.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                  saveButton?.isEnabled=validateFields()
            }
        })
    }
    private  fun validateFields(): Boolean =
        (binding.tietModel.text.toString().isNotEmpty() &&
                binding.tietHp.text.toString().isNotEmpty() &&
                binding.tietHp.text.toString().toIntOrNull() != null &&
                binding.tietHp.text.toString().toInt() != 0 &&
                binding.tietPrice.text.toString().isNotEmpty() &&
                binding.tietPrice.text.toString().toIntOrNull() != null &&
                binding.tietPrice.text.toString().toInt() != 0)

    private fun buildDialog(btn1Text: String, btn2Text: String, positiveButton: () -> Unit, negativeButton: () -> Unit ): Dialog =
        builder.setView(binding.root)
            .setTitle("Auto")
            .setPositiveButton(btn1Text){_,_ ->
                positiveButton()
            }.setNegativeButton(btn2Text){_,_->
                negativeButton()
            }.create()
}