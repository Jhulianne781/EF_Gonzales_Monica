package com.ef.monicagb.thesimpsonsapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtTitulo.typeface = Typeface.createFromAsset(assets, "fonts/Simpsonfont DEMO.otf")
        binding.tilEmail.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(text.toString(), binding.tilPassword.editText?.text.toString())
        }
        binding.tilPassword.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(binding.tilEmail.editText?.text.toString(), text.toString())
        }
        /**/
        firebaseAuth = FirebaseAuth.getInstance()
        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    authenticateWithFirebase(account.idToken!!)
                }
                catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
        /*Shared Preferences*/
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userLoggedIn = sharedPreferences.getBoolean("user_logged_in", false)
        if (userLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        /**/
        binding.btnLogin.setOnClickListener {
            val password = binding.tietPassword.editableText.toString()
            val email = binding.tietEmail.editableText.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginWithEmailAndPassword(email, password)
            } else {
                toastError("¡Ingresa todos los campos!")
            }
        }
        binding.btnGoogle.setOnClickListener {
            loginWithGoogle()
        }
        binding.btnSignUp.setOnClickListener {
            val password = binding.tietPassword.editableText.toString()
            val email = binding.tietEmail.editableText.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUpWithEmailAndPassword(email, password)
            } else {
                toastError("¡Completa todos los campos!")
            }
        }
    }

    private fun saveUserLoggedInState() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("user_logged_in", true)
        editor.apply()
    }

    private fun loginWithEmailAndPassword(email : String, password : String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    saveUserLoggedInState()
                } else {
                    toastError("¡El usuario no se encuentra registrado en la aplicación!")
                }
            }
    }

    private fun loginWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val intent = googleClient.signInIntent
        googleLauncher.launch(intent)
    }

    private fun signUpWithEmailAndPassword(email : String, password : String) {
        if (validateEmailPassword(email, password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        toastCorrecto("¡El usuario ha sido registrado en la aplicación!")
                    } else {
                        val exception = task.exception
                        if (exception is FirebaseAuthUserCollisionException) {
                            toastError("¡El usuario ya se encuentra registrado!")
                        } else {
                            toastError("¡No es posible registrar al usuario!")
                        }
                    }
                }
        } else {
            toastError("¡No son credenciales válidas!")
        }
    }

    private fun authenticateWithFirebase(idToken : String) {
        val authCredentials = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(authCredentials)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    saveUserLoggedInState()
                }
            }
    }

    private fun validateEmailPassword(emailText : String, passwordText : String) : Boolean {
        val isEmailValid = emailText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
        val isPasswordValid = passwordText.length >= 8
        return isEmailValid && isPasswordValid
    }

    private fun toastCorrecto(mensaje : String) {
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast_success, findViewById<ViewGroup>(R.id.toast_success))
        val tvMensaje = view.findViewById<TextView>(R.id.tvMensajeToast1)
        tvMensaje.text = mensaje

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

    private fun toastError(mensaje : String) {
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast_error, findViewById<ViewGroup>(R.id.toast_error))
        val tvMensaje = view.findViewById<TextView>(R.id.tvMensajeToast2)
        tvMensaje.text = mensaje

        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

}