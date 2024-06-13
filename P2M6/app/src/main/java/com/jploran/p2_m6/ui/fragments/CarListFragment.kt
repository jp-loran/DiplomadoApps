package com.jploran.p2_m6.ui.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jploran.p2_m6.R
import com.jploran.p2_m6.application.CarRFApp
import com.jploran.p2_m6.data.CarRepository
import com.jploran.p2_m6.data.remote.model.CarDto
import com.jploran.p2_m6.databinding.FragmentCarListBinding
import com.jploran.p2_m6.ui.AuthActivity
import com.jploran.p2_m6.ui.adapters.CarAdapter
import com.jploran.p2_m6.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: CarRepository
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.car_sound)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth?.currentUser
        userId = user?.uid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mediaPlayer.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = (requireActivity().application as CarRFApp).repository

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        lifecycleScope.launch {
            val call: Call<List<CarDto>> = repository.getCars("cars")

            call.enqueue(object : Callback<List<CarDto>> {
                override fun onResponse(p0: Call<List<CarDto>>, response: Response<List<CarDto>>) {
                    binding.pbLoading.visibility = View.GONE

                    response.body()?.let { cars ->
                        binding.rvGames.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = CarAdapter(cars){ car ->

                                try {
                                    if (mediaPlayer.isPlaying) {
                                        mediaPlayer.stop()
                                        mediaPlayer.prepare()
                                    }
                                    Log.d(Constants.LOGTAG, "Starting media player")
                                    mediaPlayer.start()
                                } catch (e: Exception) {
                                    Log.e(Constants.LOGTAG, "Error playing sound: ${e.message}")
                                }

                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, CarDetailFragment.newInstance(car.id.toString()))
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<List<CarDto>>, p1: Throwable) {
                    binding.pbLoading.visibility = View.GONE
                    Log.d(Constants.LOGTAG, p1.message.toString())

                }
            })
        }
    }
}