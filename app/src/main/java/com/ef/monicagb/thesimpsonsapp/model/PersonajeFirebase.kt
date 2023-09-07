package com.ef.monicagb.thesimpsonsapp.model

import com.google.gson.annotations.SerializedName

data class PersonajeFirebase(
    @SerializedName("age")
    val edad : Double,
    @SerializedName("character")
    val personaje : String,
    @SerializedName("gender")
    val genero : String,
    @SerializedName("history")
    val historia : String,
    @SerializedName("image")
    val imagen : String,
    @SerializedName("occupation")
    val ocupacion : String,
    @SerializedName("quote")
    val frase : String,
    @SerializedName("state")
    val estado : String
)