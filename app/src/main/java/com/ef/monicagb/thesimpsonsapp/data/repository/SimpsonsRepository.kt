package com.ef.monicagb.thesimpsonsapp.data.repository

import com.ef.monicagb.thesimpsonsapp.data.db.SimpsonsDao
import com.ef.monicagb.thesimpsonsapp.data.db.SimpsonsDatabase
import com.ef.monicagb.thesimpsonsapp.data.retrofit.RetrofitHelper
import com.ef.monicagb.thesimpsonsapp.model.Personaje
import com.ef.monicagb.thesimpsonsapp.model.PersonajeBD
import com.ef.monicagb.thesimpsonsapp.model.toPersonaje
import com.ef.monicagb.thesimpsonsapp.model.toPersonajeBD

class SimpsonsRepository(private val db : SimpsonsDatabase? = null) {
    private val dao : SimpsonsDao? = db?.personajeDao()

    suspend fun getPersonajes() : List<Personaje> {
        return try {
            val response = RetrofitHelper.simpsonsService.obtenerPersonajes()
            response
        }
        catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun obtenerPersonaje(personaje : String) : List<Personaje> {
        return try {
            val response = RetrofitHelper.simpsonsService.obtenerPersonaje(personaje)
            response
        }
        catch (e : Exception) {
            emptyList()
        }
    }

    suspend fun getPersonajesFavoritos() : List<Personaje> {
        dao?.let {
            val data = dao.getPersonajesFavoritos()
            val personajes : MutableList<Personaje> = mutableListOf()
            for (personajeBD in data) {
                personajes.add(personajeBD.toPersonaje())
            }
            return personajes
        } ?: kotlin.run {
            return listOf()
        }
    }

    suspend fun addPersonajeToFavoritos(personaje : Personaje) : Boolean {
        dao?.let {
            val isPersonajeFavorite = dao.isPersonajeFavorite(personaje.frase)
            if (!isPersonajeFavorite) {
                dao.addPersonajeToFavorite(personaje.toPersonajeBD())
                return true
            }
        }
        return false
    }

    suspend fun removePersonajeFromFavoritos(personaje : Personaje) {
        dao?.let {
            dao.removePersonajeFromFavoritos(personaje.toPersonajeBD())
        }
    }

}