package com.ef.monicagb.thesimpsonsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.ItemPersonajeBinding
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import com.google.android.material.bottomsheet.BottomSheetDialog

class RVSimpsonsCharacterListAdapter(var personajes : List<Personaje>, val onPersonajeClick : (Personaje) -> Unit) : RecyclerView.Adapter<PersonajeVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeVH {
        val binding = ItemPersonajeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonajeVH(binding, onPersonajeClick)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: PersonajeVH, position: Int) {
        holder.bind(personajes[position])
    }

}

class PersonajeVH(private val binding : ItemPersonajeBinding, val onPersonajeClick: (Personaje) -> Unit) : ViewHolder(binding.root) {
    fun bind(personaje : Personaje) {
        binding.tvNombrePersonaje.text = personaje.personaje
        Glide.with(binding.root)
            .load(personaje.imagen)
            .override(400, 400)
            .centerInside()
            .into(binding.imgPersonaje)
        /*Mostrar Frase del Personaje*/
        binding.cvPersonaje.setOnClickListener {
            mostrarFrase(personaje.frase)
        }
        /*Información del Personaje*/
        binding.root.setOnLongClickListener {
            onPersonajeClick(personaje)
            true
        }
    }
    fun mostrarFrase(frase : String) {
        val bottomSheetDialog = BottomSheetDialog(binding.root.context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_frase)
        val tvFrase = bottomSheetDialog.findViewById<TextView>(R.id.tvFrase)
        tvFrase!!.text = frase
        bottomSheetDialog.show()
    }
}