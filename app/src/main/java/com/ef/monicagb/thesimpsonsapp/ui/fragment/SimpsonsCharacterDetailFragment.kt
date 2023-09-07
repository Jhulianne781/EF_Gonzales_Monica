package com.ef.monicagb.thesimpsonsapp.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.FragmentSimpsonsCharacterDetailBinding
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import com.ef.monicagb.thesimpsonsapp.ui.viewmodel.SimpsonsCharacterDetailViewModel

class SimpsonsCharacterDetailFragment : Fragment() {
    private lateinit var binding : FragmentSimpsonsCharacterDetailBinding
    private val args : SimpsonsCharacterDetailFragmentArgs by navArgs()
    private lateinit var personaje : Personaje
    private lateinit var viewModel : SimpsonsCharacterDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaje = args.personaje
        viewModel = ViewModelProvider(requireActivity())[SimpsonsCharacterDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpsonsCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDetallePersonajeNombre.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Simpsonfont DEMO.otf")
        binding.tvDetallePersonajeNombre.text = personaje.personaje
        binding.tvDetallePersonajeFrase.text = "Frase: ${personaje.frase}"
        binding.tvDetallePersonajeDireccion.text = "Dirección: ${personaje.direccionPersonaje}"
        Glide.with(binding.root)
            .load(personaje.imagen)
            .override(280, 280)
            .centerInside()
            .into(binding.imgDetallePersonajeSimpson)
        /*Favoritos*/
        if (!personaje.isFavorite) {
            binding.btnEliminarFavorito.visibility = View.GONE
            binding.btnAgregarFavorito.visibility = View.VISIBLE
        }
        else {
            binding.btnAgregarFavorito.visibility = View.GONE
            binding.btnEliminarFavorito.visibility = View.VISIBLE
        }
        binding.btnAgregarFavorito.setOnClickListener {
            if (!personaje.isFavorite) {
                personaje.isFavorite = true
                viewModel.addPersonajeToFavoritos(personaje)
                toastCorrecto("¡Personaje añadido a favoritos!")
            } else {
                toastError("¡Error, personaje ya añadido!")
            }
        }
        binding.btnEliminarFavorito.setOnClickListener {
            viewModel.removePersonajeFromFavoritos(personaje)
            toastError("¡Personaje eliminado de favoritos!")
        }
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