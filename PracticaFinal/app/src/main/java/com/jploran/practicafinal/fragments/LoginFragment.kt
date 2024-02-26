package com.jploran.practicafinal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jploran.practicafinal.DataActivity
import com.jploran.practicafinal.R
import com.jploran.practicafinal.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        val etEmail = binding.etEmail
        val etPassword = binding.etPassword

        binding.btnLogin.setOnClickListener {

            val loginIntent = Intent(requireActivity(), DataActivity::class.java)

            if(etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()){
                loginIntent.putExtra("EXTRA_EMAIL", etEmail.text.toString())
                startActivity(loginIntent)
            }else{
                Toast.makeText(requireActivity(), getString(R.string.toast_fields), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener{
            val registerFragment = RegisterFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, registerFragment)
                .addToBackStack(null)
                .commit()

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }
}