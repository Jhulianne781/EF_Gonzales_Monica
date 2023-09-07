package com.ef.monicagb.thesimpsonsapp.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.ActivityAddCharacterBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.math.RoundingMode
import java.util.UUID

class AddCharacterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddCharacterBinding
    private lateinit var firestore : FirebaseFirestore
    companion object {
        private const val IMAGE_PICKER_REQUEST_CODE = 1
        private const val PERMISSION_REQUEST_CODE = 100
    }
    private var selectedImageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCharacterBinding.inflate(layoutInflater)
        firestore = Firebase.firestore
        setContentView(binding.root)
        binding.tvCrearPersonaje.typeface = Typeface.createFromAsset(assets, "fonts/Simpsonfont DEMO.otf")
        binding.btnAgregarImagenPersonaje.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            } else {
                val imagePickerIntent = Intent(Intent.ACTION_PICK)
                imagePickerIntent.type = "image/*"
                startActivityForResult(imagePickerIntent, IMAGE_PICKER_REQUEST_CODE)
            }
        }
        binding.btnAgregarPersonajeFirebase.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            } else {
                agregarPersonajeenFirebase()
            }
        }
        /**/
        binding.tietNombrePersonajeFirebase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilNombrePersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietEdadPersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilEdadPersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietGeneroPersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilGeneroPersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietHistoriaPersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilHistoriaPersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietOcupacionPersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilOcupacionPersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietFrasePersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilFrasePersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tietEstadoPersonajeFirebase.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilEstadoPersonajeFirebase.isErrorEnabled = false
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun agregarPersonajeenFirebase() {
        val character = binding.tietNombrePersonajeFirebase.editableText.toString()
        val ageStr = binding.tietEdadPersonajeFirebase.editableText.toString()
        val gender = binding.tietGeneroPersonajeFirebase.editableText.toString()
        val history = binding.tietHistoriaPersonajeFirebase.editableText.toString()
        val occupation = binding.tietOcupacionPersonajeFirebase.editableText.toString()
        val quote = binding.tietFrasePersonajeFirebase.editableText.toString()
        val state = binding.tietEstadoPersonajeFirebase.editableText.toString()
        if (character.isNotEmpty() && ageStr.isNotEmpty() && gender.isNotEmpty() && history.isNotEmpty() &&
                occupation.isNotEmpty() && quote.isNotEmpty() && state.isNotEmpty()) {
            val age = ageStr.toDouble()
            val ageRounded = age.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)
            val ageFormatted = ageRounded.toString()
            // Subir la imagen a Firebase Storage
            selectedImageUri?.let { uri ->
                val storageRef = FirebaseStorage.getInstance().reference.child("simpsonscharacters_images/${System.currentTimeMillis()}_${UUID.randomUUID()}")
                val uploadTask = storageRef.putFile(uri)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        // Agregar el personaje a Firestore con la referencia de la imagen
                        val newPersonaje = hashMapOf<String, Any>(
                            "character" to character,
                            "age" to ageFormatted.toDouble(),
                            "gender" to gender,
                            "history" to history,
                            "occupation" to occupation,
                            "quote" to quote,
                            "state" to state,
                            "image" to downloadUri.toString()
                        )
                        firestore.collection("simpsonscharacters").add(newPersonaje)
                            .addOnSuccessListener { documentReference ->
                                toastCorrecto("¡Personaje agregado con el siguiente id: ${documentReference.id}!")
                                limpiarCampos()
                            }
                            .addOnFailureListener {
                                toastError("¡Ha ocurrido un error!")
                            }
                    }
                }
            }
        } else {
            toastError("¡Todos los campos son requeridos!")
            binding.tilNombrePersonajeFirebase.error = "¡El personaje es requerido!"
            binding.tilEdadPersonajeFirebase.error = "¡La edad es requerida!"
            binding.tilGeneroPersonajeFirebase.error = "¡El género es requerido!"
            binding.tilHistoriaPersonajeFirebase.error = "¡La historia es requerida!"
            binding.tilOcupacionPersonajeFirebase.error = "¡La ocupación es requerida!"
            binding.tilFrasePersonajeFirebase.error = "¡La frase es requerida!"
            binding.tilEstadoPersonajeFirebase.error = "¡El estado es requerido!"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            // Mostrar la imagen seleccionada en el ImageView
            selectedImageUri?.let { uri ->
                Glide.with(this)
                    .load(uri)
                    .error(R.drawable.error)
                    .into(binding.imgPersonajeSeleccionado)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val imagePickerIntent = Intent(Intent.ACTION_PICK)
                imagePickerIntent.type = "image/*"
                startActivityForResult(imagePickerIntent, IMAGE_PICKER_REQUEST_CODE)
            } else {
                toastError("¡Se requiere permiso de lectura para acceder a imágenes!")
            }
        }
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

    private fun limpiarCampos() {
        binding.tietNombrePersonajeFirebase.editableText.clear()
        binding.tietEdadPersonajeFirebase.editableText.clear()
        binding.tietGeneroPersonajeFirebase.editableText.clear()
        binding.tietHistoriaPersonajeFirebase.editableText.clear()
        binding.tietOcupacionPersonajeFirebase.editableText.clear()
        binding.tietFrasePersonajeFirebase.editableText.clear()
        binding.tietEstadoPersonajeFirebase.editableText.clear()
        binding.imgPersonajeSeleccionado.setImageDrawable(null)
        binding.tietNombrePersonajeFirebase.requestFocus()
    }

}