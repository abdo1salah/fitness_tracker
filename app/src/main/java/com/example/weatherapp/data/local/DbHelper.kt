package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.network.WeatherResponse


@Database(entities = [WeatherResponse::class], version = 1)
abstract class DBHelper: RoomDatabase() {

        abstract fun weetherDao(): WeatherDao
        companion object {
            //Ensures that the value of the variable is always up-to-date,
            //and any changes to the variable should be immediately visible to all threads.
            @Volatile
            private var INSTANCE: DBHelper? = null

            fun getDBInstance(context: Context): DBHelper {
                //Lock the current object to prevent race conditions.
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context, DBHelper::class.java, "weather").build()
                    INSTANCE = instance

                    instance
                }
            }

        }

    }
