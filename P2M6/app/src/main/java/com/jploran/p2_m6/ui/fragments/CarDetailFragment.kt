package com.jploran.p2_m6.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jploran.p2_m6.R
import com.jploran.p2_m6.application.CarRFApp
import com.jploran.p2_m6.data.CarRepository
import com.jploran.p2_m6.data.remote.model.CarDetailDto
import com.jploran.p2_m6.databinding.FragmentCarDetailBinding
import com.jploran.p2_m6.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val CAR_ID = "car_id"
class CarDetailFragment : Fragment() {
    private var _binding: FragmentCarDetailBinding? = null
    private val binding get() = _binding!!
    private var car_id: String? = null
    private lateinit var repository: CarRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            car_id = args.getString(CAR_ID)
            Log.d(Constants.LOGTAG, "Id recibido: $car_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = (requireActivity().application as CarRFApp).repository

        lifecycleScope.launch {
            car_id?.let { id ->
                val call: Call<CarDetailDto> = repository.getCarDetailApiary(id)

                call.enqueue(object: Callback<CarDetailDto>{
                    override fun onResponse(p0: Call<CarDetailDto>, p1: Response<CarDetailDto>) {
                        binding.apply {
                            pbLoading.visibility = View.INVISIBLE
                            tvTitle.text = p1.body()?.brand
                            tvModel.text = p1.body()?.model
                            tvHp.text= p1.body()?.hp
                            tvPrice.text = p1.body()?.price
                            tvYear.text = p1.body()?.year
                            tvPerformance.text = p1.body()?.performance

                            Glide.with(requireActivity())
                                .load(p1.body()?.image)
                                .into(ivImage)

                            val videoUrl = p1.body()?.video
                            if (!videoUrl.isNullOrEmpty()) {
                                vvCarVideo.setVideoURI(Uri.parse(videoUrl))
                                val mc = MediaController(requireContext())
                                mc.setAnchorView(vvCarVideo)
                                vvCarVideo.setMediaController(mc)
                                vvCarVideo.start()
                            } else {
                                vvCarVideo.visibility = View.INVISIBLE
                                Log.d(Constants.LOGTAG, "No video URL provided")
                            }


                        }
                    }

                    override fun onFailure(p0: Call<CarDetailDto>, p1: Throwable) {
                        binding.pbLoading.visibility = View.INVISIBLE
                        Log.d(Constants.LOGTAG, p1.message.toString())
                    }

                })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(carId: String) =
            CarDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(CAR_ID, carId)
                }
            }
    }
}