package com.ijikod.lastfm.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijikod.lastfm.data.api.API_KEY
import com.ijikod.lastfm.data.api.LastFmApiService
import com.ijikod.lastfm.data.database.LocalCache
import com.ijikod.lastfm.data.model.*
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * Repository class to enforce data single source of truth
 *
 * **/
class Repository(private val service :LastFmApiService, private val cache: LocalCache) {

    lateinit var albumListData: LiveData<List<Album>>

    val albumDetails = MutableLiveData<AlbumDetails>()

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

    // Load search results from remote or save to local database
    private fun listAlbums(query: String) = runBlocking {
        async {
            remoteAlbumSearch(query, {
                cache.removeAlbumsByQuery(query)
                cache.insertAlbum(it)
                albumListData = cache.albumsByQuery(query)
                _networkErrors.postValue("")
            }, { error ->
                albumListData = cache.albumsByQuery(query)
                _networkErrors.postValue(error)
            })
        }.await()
    }

    // Load album details from remote or save to local database
     fun albumDetails(album: String, artist: String, mbid: String){
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch cached album details
            val cachedAlbums= cache.albumsByMid(mbid)
            CoroutineScope(Dispatchers.Main).launch {
                albumDetails.value = cachedAlbums
            }

            if (cachedAlbums == null){
                remoteAlbumDetails(album, artist)
            }
        }
    }


    /**
     * Get albums data from remote
     * @param queryString
     * **/
    @WorkerThread
    suspend fun remoteAlbumSearch(queryString: String,
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


    @WorkerThread
    suspend fun remoteAlbumDetails(album: String, artist : String){
        try {
            val response  = service.getAlbumDetails(album, artist, API_KEY)
            val remoteDetailsData = response.body()?.album
            remoteDetailsData?.let {
                cache.insertAlbumDetails(it)
                CoroutineScope(Dispatchers.Main).launch {
                    albumDetails.value = it
                }
            }
            _networkErrors.postValue("")
        }catch (exception : Exception){
            exception.message?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    _networkErrors.value = it
                }
            }
        }

    }

}