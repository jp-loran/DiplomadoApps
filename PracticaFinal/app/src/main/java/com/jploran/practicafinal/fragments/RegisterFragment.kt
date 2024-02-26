package com.jploran.practicafinal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import com.jploran.practicafinal.DataActivity
import com.jploran.practicafinal.R
import com.jploran.practicafinal.databinding.FragmentLoginBinding
import com.jploran.practicafinal.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        val etName = binding.etName
        val etLastName = binding.etLastName
        val etEmail = binding.etEmail
        val etPassword = binding.etPassword
        val rgGenre = binding.rgGenre

        binding.btnRegister.setOnClickListener {
            val selectedGenderId = rgGenre.checkedRadioButtonId
            val registerIntent = Intent(requireActivity(), DataActivity::class.java)
            if(etName.text.isNotEmpty() && etLastName.text.isNotEmpty() && etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty() && selectedGenderId != -1){

                val rgText = rgGenre.findViewById<RadioButton>(selectedGenderId)
                registerIntent.putExtra("EXTRA_NAME", etName.text.toString())
                registerIntent.putExtra("EXTRA_LAST_NAME", etLastName.text.toString())
                registerIntent.putExtra("EXTRA_EMAIL", etEmail.text.toString())
                registerIntent.putExtra("EXTRA_GENRE", rgText.text.toString())

                startActivity(registerIntent)
            }else{
                Toast.makeText(requireActivity(), getString(R.string.toast_fields), Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }
}