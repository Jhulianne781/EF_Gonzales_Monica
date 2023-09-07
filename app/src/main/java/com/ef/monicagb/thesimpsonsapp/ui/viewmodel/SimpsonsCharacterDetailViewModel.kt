package com.ef.monicagb.thesimpsonsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ef.monicagb.thesimpsonsapp.data.db.SimpsonsDatabase
import com.ef.monicagb.thesimpsonsapp.data.repository.SimpsonsRepository
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import com.ef.monicagb.thesimpsonsapp.model.PersonajeBD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimpsonsCharacterDetailViewModel(application : Application) : AndroidViewModel(application) {
    private val repository : SimpsonsRepository
    init {
        val db = SimpsonsDatabase.getDatabase(application)
        repository = SimpsonsRepository(db)
    }

    fun addPersonajeToFavoritos(personaje : Personaje) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPersonajeToFavoritos(personaje)
        }
    }

    fun removePersonajeFromFavoritos(personaje : Personaje) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removePersonajeFromFavoritos(personaje)
        }
    }

}