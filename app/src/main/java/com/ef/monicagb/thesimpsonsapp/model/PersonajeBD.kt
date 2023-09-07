package com.ef.monicagb.thesimpsonsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personaje")
data class PersonajeBD(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val frase: String,
    val personaje: String,
    val imagen: String,
    val direccionPersonaje: String,
    var isFavorite : Boolean = false
)

fun PersonajeBD.toPersonaje() : Personaje {
    return Personaje(
        id, frase, personaje, imagen, direccionPersonaje, isFavorite
    )
}