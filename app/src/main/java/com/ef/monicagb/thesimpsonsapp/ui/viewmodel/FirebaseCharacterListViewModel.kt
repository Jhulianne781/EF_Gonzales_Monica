package com.ef.monicagb.thesimpsonsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCharacterListViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    fun getFirebaseList() = firestore.collection("simpsonscharacters").get()
}