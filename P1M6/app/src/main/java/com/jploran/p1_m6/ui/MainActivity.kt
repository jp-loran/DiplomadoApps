package com.jploran.p1_m6.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jploran.p1_m6.R
import com.jploran.p1_m6.application.CarDBApp
import com.jploran.p1_m6.data.CarRepository
import com.jploran.p1_m6.data.db.model.CarEntity
import com.jploran.p1_m6.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var cars: List<CarEntity> = emptyList()
    private lateinit var repository: CarRepository
    private lateinit var carAdapter: CarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as CarDBApp).repository

        carAdapter = CarAdapter(){selectedCar ->
            val dialog = CarDialog(newCar = false, car = selectedCar, {updateUI()})
            dialog.show(supportFragmentManager, "")
        }

        binding.rvCars.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = carAdapter
        }

        updateUI()
    }



    private fun updateUI(){
        lifecycleScope.launch(){
            cars = repository.getCars()

            binding.tvSinRegistros.visibility =
                if(cars.isEmpty()) View.VISIBLE else View.INVISIBLE

            carAdapter.updateList(cars)
        }
    }

    fun click(view: View) {
        val dialog = CarDialog(updateUI = { updateUI() })
        dialog.show(supportFragmentManager, "dialogo")
    }

}
