package com.jploran.p2_m6.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.jploran.p2_m6.R
import com.jploran.p2_m6.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnRegistrarse.setOnClickListener {
            if(!validateFields()) return@setOnClickListener
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{authResult ->
                if(authResult.isSuccessful){
                    var userFirebase = firebaseAuth.currentUser

                    userFirebase?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "El correo de verificación se ha enviado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }?.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "No se pudo enviar el correo de verificación",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    Toast.makeText(
                        this,
                        "Usuario creado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    handleErrors(authResult)
                }
            }
        }

        binding.tvRestablecerPassword.setOnClickListener {
            val resetMail = EditText(it.context)
            resetMail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            val passwordResetDialog = AlertDialog.Builder(it.context)
                .setTitle("Restablecer contraseña")
                .setMessage("Ingrese su correo para recibir el enlace para restablecer")
                .setView(resetMail)
                .setPositiveButton("Enviar") { _, _ ->
                    val mail = resetMail.text.toString()
                    if (mail.isNotEmpty()) {
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "El enlace para restablecer la contraseña ha sido enviado a su correo",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                "El enlace no se ha podido enviar: ${it.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show() //it tiene la excepción
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Favor de ingresar la dirección de correo",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }.setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }


        binding.btnLogin.setOnClickListener {
            if(!validateFields()) return@setOnClickListener
            //autenticamos al usuario
            authenticateUser(email, password)
        }

    }



    private fun validateFields(): Boolean{
        email = binding.tietEmail.text.toString().trim()
        password = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = "Se requiere un correo"
            binding.tietEmail.requestFocus()
            return false
        }

        if(password.isEmpty() || password.length < 6){
            binding.tietContrasenia.error = "Se requiere una contraseña válida de por lo menos 6 caracteres"
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun handleErrors(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, "Error: El correo electrónico no tiene un formato correcto", Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = "Error: El correo electrónico no tiene un formato correcto"
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, "Error: La contraseña no es válida", Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = "La contraseña no es válida"
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, "Error: Una cuenta ya existe con el mismo correo, pero con diferentes datos de ingreso", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, "Error: el correo electrónico ya está en uso con otra cuenta.", Toast.LENGTH_LONG).show()
                binding.tietEmail.error = ("Error: el correo electrónico ya está en uso con otra cuenta.")
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, "Error: La sesión ha expirado. Favor de ingresar nuevamente.", Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, "Error: No existe el usuario con la información proporcionada.", Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, "La contraseña porporcionada es inválida", Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = "La contraseña debe de tener por lo menos 6 caracteres"
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, "Red no disponible o se interrumpió la conexión", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error. No se pudo autenticar exitosamente.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun authenticateUser(usr: String, psw: String){

        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            if(authResult.isSuccessful){
                Toast.makeText(
                    this,
                    "Autenticación exitosa",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                handleErrors(authResult)
            }
        }

    }

}