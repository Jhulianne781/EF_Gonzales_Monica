package com.ef.monicagb.thesimpsonsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ef.monicagb.thesimpsonsapp.data.SimpsonsServiceResult
import com.ef.monicagb.thesimpsonsapp.data.repository.SimpsonsRepository
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimpsonsCharacterListViewModel : ViewModel() {
    private var _personajes : MutableLiveData<List<Personaje>> = MutableLiveData<List<Personaje>>()
    val personajes : LiveData<List<Personaje>> = _personajes

    private val repository = SimpsonsRepository()

    fun getPersonajesFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPersonajes()
            _personajes.postValue(response)
        }
    }

    fun obtenerPersonaje(personaje : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.obtenerPersonaje(personaje)
            _personajes.postValue(response)
        }
    }

}