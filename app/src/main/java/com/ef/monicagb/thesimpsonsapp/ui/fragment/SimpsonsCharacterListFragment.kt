package com.ef.monicagb.thesimpsonsapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ef.monicagb.thesimpsonsapp.databinding.FragmentSimpsonsCharacterListBinding
import com.ef.monicagb.thesimpsonsapp.ui.adapter.RVSimpsonsCharacterListAdapter
import com.ef.monicagb.thesimpsonsapp.ui.viewmodel.SimpsonsCharacterListViewModel

class SimpsonsCharacterListFragment : Fragment() {
    private lateinit var binding : FragmentSimpsonsCharacterListBinding
    private lateinit var viewModel : SimpsonsCharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SimpsonsCharacterListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpsonsCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RVSimpsonsCharacterListAdapter(listOf()) { personaje ->
            val destination = SimpsonsCharacterListFragmentDirections.actionSimpsonsCharacterListFragmentToSimpsonsCharacterDetailFragment(personaje)
            findNavController().navigate(destination)
        }
        binding.rvPersonajes.layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.rvPersonajes.adapter = adapter
        viewModel.personajes.observe(viewLifecycleOwner) { personajes ->
            personajes?.let {
                adapter.personajes = it
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getPersonajesFromService()
        /**/
        binding.tilBuscar.setEndIconOnClickListener {
            if (binding.tietBuscar.text.toString() == "") {
                viewModel.getPersonajesFromService()
            } else {
                viewModel.obtenerPersonaje(binding.tietBuscar.text.toString().trim())
            }
        }
    }

}