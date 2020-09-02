package com.ijikod.lastfm.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijikod.lastfm.data.api.API_KEY
import com.ijikod.lastfm.data.api.LastFmApiService
import com.ijikod.lastfm.data.database.LocalCache
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumSearchResults
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * Repository class to enforce data single source of truth
 *
 * **/
class Repository(private val service :LastFmApiService, private val cache: LocalCache) {

    lateinit var albumListData: LiveData<List<Album>>

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    /**
     * Await search results from either remote or local database and server to view model
     * **/
    fun getAlbums(value: String): AlbumSearchResults {
        listAlbums(value)
        return AlbumSearchResults(albumListData, networkErrors)
    }

    // Load search results from remote and save to local database
    private fun listAlbums(query: String) = runBlocking {
        async {
            getVideosFromNetwork(query, {
                cache.removeAlbumsByQuery(query)
                cache.insert(it)
                albumListData = cache.albumsByQuery(query)
                _networkErrors.postValue("")
            }, { error ->
                albumListData = cache.albumsByQuery(query)
                _networkErrors.postValue(error)
            })
        }.await()
    }


    /**
     * Get albums data from remote
     * @param queryString
     * **/
    @WorkerThread
    suspend fun getVideosFromNetwork(queryString: String,
                                     onSuccess: (results: List<Album>) ->Unit,
                                     onError: (error: String) -> Unit){
        try {
            val response  = service.searchAlbum(queryString, API_KEY)
            val remoteData = response.body()?.results?.albummatches?.album ?: emptyList()
            onSuccess(remoteData)
        }catch (exception : Exception){
            exception.message?.let {
                onError(it)
            }
        }

    }


}