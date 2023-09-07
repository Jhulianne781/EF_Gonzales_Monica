package com.ef.monicagb.thesimpsonsapp.data.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://thesimpsonsquoteapi.glitch.me/")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
    val simpsonsService : SimpsonsService = retrofit.create(SimpsonsService::class.java)
}
//https://thesimpsonsquoteapi.glitch.me/quotes?count=num