package com.example.mvvmstructurekotlin.repository.dbhandler

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse

/**
 * This interface will gives a data from the database
 */
@Dao
interface DbDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(user: MutableList<ProductListResponse.Device>)

    @Query("SELECT * FROM employee")
    fun getAllLoginDetails(): MutableList<ProductListResponse.Device>



}