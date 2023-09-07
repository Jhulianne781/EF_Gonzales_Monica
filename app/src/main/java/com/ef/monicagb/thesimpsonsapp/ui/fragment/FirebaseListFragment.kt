package com.ef.monicagb.thesimpsonsapp.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ef.monicagb.thesimpsonsapp.R
import com.ef.monicagb.thesimpsonsapp.databinding.FragmentFirebaseListBinding
import com.ef.monicagb.thesimpsonsapp.model.PersonajeFirebase
import com.ef.monicagb.thesimpsonsapp.ui.AddCharacterActivity
import com.ef.monicagb.thesimpsonsapp.ui.adapter.RVFirebaseListAdapter
import com.ef.monicagb.thesimpsonsapp.ui.viewmodel.FirebaseCharacterListViewModel

class FirebaseListFragment : Fragment() {
    private lateinit var binding : FragmentFirebaseListBinding
    private lateinit var viewModel : FirebaseCharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FirebaseCharacterListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseListBinding.inflate(inflater, container, false)
        binding.fabAddCharacter.setOnClickListener {
            val intent = Intent(requireContext(), AddCharacterActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvListaFirebase.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Simpsonfont DEMO.otf")
        val adapter = RVFirebaseListAdapter(listOf())
        binding.rvFirebaseList.adapter = adapter
        viewModel.getFirebaseList().addOnSuccessListener { querySnapshot ->
            val personajeList = mutableListOf<PersonajeFirebase>()
            for (document in querySnapshot.documents) {
                val age = document.getDouble("age") ?: ""
                val character = document.getString("character") ?: ""
                val gender = document.getString("gender") ?: ""
                val history = document.getString("history") ?: ""
                val image = document.getString("image") ?: ""
                val occupation = document.getString("occupation") ?: ""
                val quote = document.getString("quote") ?: ""
                val state = document.getString("state") ?: ""
                personajeList.add(PersonajeFirebase(age as Double, character, gender, history, image, occupation, quote, state))
            }
            adapter.setData(personajeList)
        }
    }

}