package com.ef.monicagb.thesimpsonsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ef.monicagb.thesimpsonsapp.model.PersonajeBD

@Database(entities = [PersonajeBD::class], version = 1)
abstract class SimpsonsDatabase : RoomDatabase() {
    abstract fun personajeDao() : SimpsonsDao

    companion object {
        @Volatile
        private var instance : SimpsonsDatabase? = null
        fun getDatabase(context : Context) : SimpsonsDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    SimpsonsDatabase::class.java,
                    "simpsonsbd"
                ).build()
                instance = _instance
                return _instance
            }
        }
    }

}