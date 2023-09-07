package com.ef.monicagb.thesimpsonsapp.data

sealed class SimpsonsServiceResult<T> (data : T? = null, error : Exception? = null) {
    data class Success<T>(val data : T) : SimpsonsServiceResult<T>(data, null)
    data class Error<T>(val error : Exception) : SimpsonsServiceResult<T>(null, error)
}