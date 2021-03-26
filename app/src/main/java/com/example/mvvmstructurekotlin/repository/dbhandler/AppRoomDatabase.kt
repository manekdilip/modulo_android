package com.example.mvvmstructurekotlin.repository.dbhandler

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.mvvmstructurekotlin.constants.Constants
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse

/**
 * Get the database object
 */
@Database(
    entities = arrayOf(
        ProductListResponse.Device::class
    ),
    version = 1,
    exportSchema = false
)
public abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun dbDao(): DbDAO

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    Constants.databaseName
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}