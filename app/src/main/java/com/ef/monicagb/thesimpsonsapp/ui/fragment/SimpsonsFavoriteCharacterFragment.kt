package com.ef.monicagb.thesimpsonsapp.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.FragmentSimpsonsFavoriteCharacterBinding
import com.ef.monicagb.thesimpsonsapp.ui.adapter.RVSimpsonsCharacterListAdapter
import com.ef.monicagb.thesimpsonsapp.ui.viewmodel.SimpsonsFavoriteCharacterViewModel

class SimpsonsFavoriteCharacterFragment : Fragment() {
    private lateinit var binding : FragmentSimpsonsFavoriteCharacterBinding
    private lateinit var viewModel : SimpsonsFavoriteCharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SimpsonsFavoriteCharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpsonsFavoriteCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvListaFavoritos.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Simpsonfont DEMO.otf")
        val adapter = RVSimpsonsCharacterListAdapter(listOf()) { personaje ->
            val destination = SimpsonsFavoriteCharacterFragmentDirections.actionSimpsonsFavoriteCharacterFragmentToSimpsonsCharacterDetailFragment(personaje)
            findNavController().navigate(destination)
        }
        binding.rvFavoritosList.layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.rvFavoritosList.adapter = adapter
        viewModel.personajesfavoritos.observe(requireActivity()) { personajes ->
            adapter.personajes = personajes
            adapter.notifyDataSetChanged()
        }
        viewModel.getPersonajesFavoritos()
    }

}