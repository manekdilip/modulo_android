package com.example.mvvmstructurekotlin.constants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Constants {


    const val baseURL = "http://storage42.com/modulotest/"
    const val OK: String = "ok"
    const val TEXT_MESSAGES: String = "TEXT_MESSAGES"
    const val databaseName = "modulodb"

    //TODO Handel API status code
    object HTTP_STATUS {
        const val OK = "00"
        const val NOTFOUND = "404"
    }


}