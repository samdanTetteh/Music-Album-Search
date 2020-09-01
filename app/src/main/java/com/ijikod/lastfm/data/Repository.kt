package com.ijikod.lastfm.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijikod.lastfm.data.api.API_KEY
import com.ijikod.lastfm.data.api.LastFmApiService
import com.ijikod.lastfm.data.database.LocalCache
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumSearchResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Repository class to enforce data single source of truth
 *
 * **/
class Repository(private val service :LastFmApiService, private val cache: LocalCache) {

    lateinit var albumListData : LiveData<List<Album>>

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors : LiveData<String>
        get() = _networkErrors


    /**
     * [Repository] class to load data from local repository to serve as single source of truth
     * **/
    fun listAlbums(query: String): AlbumSearchResults {

        albumListData = cache.albumsByQuery(query)
        if (albumListData.value.isNullOrEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                getVideosFromNetwork(query, {
                    cache.insert(it)
                    albumListData = cache.albumsByQuery(query)
                    _networkErrors.postValue("")
                }, {error ->
                    _networkErrors.postValue(error)
                })
            }
        }

        return AlbumSearchResults(albumListData, networkErrors)

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