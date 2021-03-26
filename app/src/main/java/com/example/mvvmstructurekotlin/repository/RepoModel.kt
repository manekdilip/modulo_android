package com.mobi.mobidrivers.repository

import android.content.Context
import com.example.mvvmstructurekotlin.repository.dbhandler.AppRoomDatabase
import com.example.mvvmstructurekotlin.repository.dbhandler.DatabaseRepository
import com.mobi.mobidrivers.repository.rest.ApiService
import org.koin.core.KoinComponent

//TODO Used For Dependency Injection
class RepoModel(context: Context) : KoinComponent {
    var api = ApiService.create()
    var appDatabase = DatabaseRepository(AppRoomDatabase.getDatabase(context).dbDao())
}