package com.ef.monicagb.thesimpsonsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ef.monicagb.thesimpsonsapp.data.db.SimpsonsDatabase
import com.ef.monicagb.thesimpsonsapp.data.repository.SimpsonsRepository
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimpsonsFavoriteCharacterViewModel(application : Application) : AndroidViewModel(application) {
    private val repository : SimpsonsRepository
    private var _personajesfavoritos : MutableLiveData<List<Personaje>> = MutableLiveData()
    var personajesfavoritos : LiveData<List<Personaje>> = _personajesfavoritos

    init {
        val db = SimpsonsDatabase.getDatabase(application)
        repository = SimpsonsRepository(db)
    }

    fun getPersonajesFavoritos() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getPersonajesFavoritos()
            _personajesfavoritos.postValue(data)
        }
    }

}