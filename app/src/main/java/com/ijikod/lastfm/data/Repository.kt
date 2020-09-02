package com.ijikod.lastfm.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijikod.lastfm.data.api.API_KEY
import com.ijikod.lastfm.data.api.LastFmApiService
import com.ijikod.lastfm.data.database.LocalCache
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails
import com.ijikod.lastfm.data.model.AlbumSearchResults
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * Repository class to enforce data single source of truth
 *
 * **/
class Repository(private val service :LastFmApiService, private val cache: LocalCache) {

    lateinit var albumListData: LiveData<List<Album>>

    lateinit var albumDetails: LiveData<AlbumDetails>

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


    /**
     * Await search results from either remote or local database and server to view model
     * **/
    fun getAlbumsDetails(album: String, artist: String,  mbid: String): AlbumDetails {
        albumDetails(album, artist, mbid)
        return albumDetails.value!!
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
    private fun albumDetails(album: String, artist: String, mbid: String) = runBlocking {
        async {
            // Fetch cached album details
            albumDetails = cache.albumsByMid(mbid)

            // Load remote album details
            albumDetails?.let {
                remoteAlbumDetails(album, artist, {error ->
                    _networkErrors.postValue(error)
                }, {
                    cache.insertAlbumDetails(it)
                    albumDetails = cache.albumsByMid(it.mbid)
                    _networkErrors.postValue("")
                })
            }
        }.await()
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
    suspend fun remoteAlbumDetails(album: String, artist : String,
                                   onError: (error: String) -> Unit,
                                   onSuccess: (result: AlbumDetails) -> Unit){

        try {
            val response  = service.getAlbumDetails(album, artist, API_KEY)
            val remoteDetailsData = response.body()?.album
            onSuccess(remoteDetailsData!!)
        }catch (exception : Exception){
            exception.message?.let {
                onError(it)
            }
        }

    }

}