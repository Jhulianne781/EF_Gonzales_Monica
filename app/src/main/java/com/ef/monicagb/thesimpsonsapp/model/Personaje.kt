package com.ef.monicagb.thesimpsonsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Personaje(
    val id : Int,
    @SerializedName("quote")
    val frase: String,
    @SerializedName("character")
    val personaje: String,
    @SerializedName("image")
    val imagen: String,
    @SerializedName("characterDirection")
    val direccionPersonaje: String,
    var isFavorite : Boolean = false
) : Parcelable

fun Personaje.toPersonajeBD() : PersonajeBD {
    return PersonajeBD(
        id, frase, personaje, imagen, direccionPersonaje, isFavorite
    )
}