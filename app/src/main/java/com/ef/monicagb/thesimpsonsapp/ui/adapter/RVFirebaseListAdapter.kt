package com.ef.monicagb.thesimpsonsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ef.monicagb.thesimpsonsapp.databinding.ItemPersonajefirebaseBinding
import com.ef.monicagb.thesimpsonsapp.model.PersonajeFirebase

class RVFirebaseListAdapter(var personajes : List<PersonajeFirebase>) : RecyclerView.Adapter<PersonajeFirebaseVH>() {
    fun setData(newPersonajes : List<PersonajeFirebase>) {
        personajes = newPersonajes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeFirebaseVH {
        val binding = ItemPersonajefirebaseBinding.inflate(LayoutInflater.from(parent.context))
        return PersonajeFirebaseVH(binding)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: PersonajeFirebaseVH, position: Int) {
        holder.bind(personajes[position])
    }
}

class PersonajeFirebaseVH(private val binding : ItemPersonajefirebaseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind (firebase : PersonajeFirebase) {
        binding.tvPersonajeFirebaseNombre.text = firebase.personaje
        binding.tvPersonajeFirebaseEdad.text = "Edad: ${firebase.edad} años"
        binding.tvPersonajeFirebaseGenero.text = "Género: ${firebase.genero}"
        binding.tvPersonajeFirebaseOcupacion.text = "Ocupación: ${firebase.ocupacion}"
        binding.tvPersonajeFirebaseEstado.text = "Estado: ${firebase.estado}"
        Glide.with(binding.root)
            .load(firebase.imagen)
            .override(200, 200)
            .centerInside()
            .into(binding.imgPersonajeFirebase)
    }
}