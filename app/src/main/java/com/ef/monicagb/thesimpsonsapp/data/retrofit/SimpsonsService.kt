package com.ef.monicagb.thesimpsonsapp.data.retrofit

import com.ef.monicagb.thesimpsonsapp.model.Personaje
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpsonsService {
    @GET("quotes?count=36")
    suspend fun obtenerPersonajes() : List<Personaje>

    @GET("quotes")
    suspend fun obtenerPersonaje(@Query("character") personaje : String) : List<Personaje>
}