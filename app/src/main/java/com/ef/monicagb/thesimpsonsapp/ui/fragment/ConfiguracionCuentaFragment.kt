package com.ef.monicagb.thesimpsonsapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.FragmentConfiguracionCuentaBinding
import com.ef.monicagb.thesimpsonsapp.ui.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ConfiguracionCuentaFragment : Fragment() {
    private lateinit var binding : FragmentConfiguracionCuentaBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfiguracionCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.tvConfiguracionCuenta.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Simpsonfont DEMO.otf")
        binding.tvEditarInformacion.setOnClickListener {
            SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Enhorabuena")
                .setContentText("¡Datos cambiados con éxito!")
                .setCustomImage(R.drawable.maggie)
                .setConfirmText("Cerrar")
                .show()
        }
        binding.tvCambiarContraseA.setOnClickListener {
            SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Enhorabuena")
                .setContentText("¡Contraseña restablecida!")
                .setCustomImage(R.drawable.santaslittlehelper)
                .setConfirmText("Cerrar")
                .show()
        }
        binding.tvInformacion.setOnClickListener {
            showAppInfo()
        }
        binding.tvAyuda.setOnClickListener {
            SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Soporte Técnico")
                .setContentText("Consultas al monicajhuliannegonzales@gmail.com")
                .setCustomImage(R.drawable.marge)
                .setConfirmText("Cerrar")
                .show()
        }
        binding.tvCerrarSesion.setOnClickListener {
            signOut()
        }
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toastCorrecto("¡Notificaciones Habilitadas!")
            }
            else {
                toastError("¡Notificaciones Deshabilitadas!")
            }
        }
    }

    private fun showAppInfo() {
        val versionName = requireContext().packageManager?.getPackageInfo(requireContext().packageName, 0)?.versionName
        val infoMessage = "Versión: $versionName"
        val creationMessage = "Fecha de Creación: 25/08/2023"
        val fullInfoMessage = "$creationMessage\n$infoMessage"
        SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
            .setTitleText("Información")
            .setContentText(fullInfoMessage)
            .setCustomImage(R.drawable.bart)
            .setConfirmText("Cerrar")
            .show()
    }

    private fun signOut() {
        firebaseAuth.signOut()
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        googleClient.signOut()

        /*Shared Preferences*/
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("user_logged_in", false)
        editor.apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun toastCorrecto(mensaje: String) {
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.custom_toast_success, requireView().findViewById(R.id.toast_success))
        val tvMensaje = view.findViewById<TextView>(R.id.tvMensajeToast1)
        tvMensaje.text = mensaje

        val toast = Toast(requireContext())
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

    private fun toastError(mensaje: String) {
        val layoutInflater = LayoutInflater.from(requireContext())
        val view = layoutInflater.inflate(R.layout.custom_toast_error, requireView().findViewById(R.id.toast_error))
        val tvMensaje = view.findViewById<TextView>(R.id.tvMensajeToast2)
        tvMensaje.text = mensaje

        val toast = Toast(requireContext())
        toast.setGravity(Gravity.CENTER_VERTICAL or Gravity.BOTTOM, 0, 200)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

}