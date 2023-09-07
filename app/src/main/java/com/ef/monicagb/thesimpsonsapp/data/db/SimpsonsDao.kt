package com.ef.monicagb.thesimpsonsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ef.monicagb.thesimpsonsapp.model.PersonajeBD

@Dao
interface SimpsonsDao {
    @Insert
    suspend fun addPersonajeToFavorite(personaje : PersonajeBD)

    @Query("SELECT * FROM personaje")
    suspend fun getPersonajesFavoritos() : List<PersonajeBD>

    @Delete
    suspend fun removePersonajeFromFavoritos(personaje : PersonajeBD)

    @Query("SELECT EXISTS (SELECT 1 FROM personaje WHERE frase = :quote)")
    suspend fun isPersonajeFavorite(quote : String) : Boolean

}