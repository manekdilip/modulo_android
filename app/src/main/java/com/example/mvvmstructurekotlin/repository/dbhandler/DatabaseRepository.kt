package com.example.mvvmstructurekotlin.repository.dbhandler

import com.example.mvvmstructurekotlin.repository.dbhandler.DbDAO
import com.example.mvvmstructurekotlin.repository.rest.response.productlist.ProductListResponse


/**
 * This class will help to communicate with your database operation.
 */
class DatabaseRepository(val slDao: DbDAO) {

    suspend fun insertEmployee(user: MutableList<ProductListResponse.Device>) {
        slDao.insertEmployee(user)
    }
    fun getuserdata():MutableList<ProductListResponse.Device>{
        return slDao.getAllLoginDetails()
    }

}