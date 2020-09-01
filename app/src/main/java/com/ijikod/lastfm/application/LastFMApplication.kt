package com.ijikod.lastfm.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.data.api.RetrofitInstance
import com.ijikod.lastfm.data.database.LastFmDatabase
import com.ijikod.lastfm.data.database.LocalCache
import com.ijikod.lastfm.presentation.factory.ViewModelFactory
import java.util.concurrent.Executors

/**
 * Base application class for app initialisation and DI
 * **/
class LastFMApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }


    companion object{
        lateinit var appContext : Context

        /**
         * Creates an instance of [LocalCache] based on the database DAO.
         */
        private fun provideCache(context: Context): LocalCache {
            val database = LastFmDatabase.getInstance(context)
            return LocalCache(database.albumsDao(), Executors.newSingleThreadExecutor())
        }

        /**
         * Creates an instance of [Repository] based on the [RetrofitInstance] and a
         * [LocalCache]
         */
        private fun provideRepository(context: Context): Repository {
            return Repository(RetrofitInstance.lastApiService, provideCache(context))
        }

        /**
         * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
         * [ViewModel] objects.
         */
        fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
            return ViewModelFactory(provideRepository(context))
        }

    }

}