package com.ijikod.lastfm.presentation.vm

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumSearchResults

/**
 * ViewModel class to serve as a bridge between our repository and UI
 * **/
class SearchViewModel(private val repository: Repository) :ViewModel() {

    private val queryLiveData = MutableLiveData<String>()


    var albumResults: LiveData<AlbumSearchResults> = Transformations.map(queryLiveData){
        repository.getAlbums(it)
    }
    val albums : LiveData<List<Album>> = Transformations.switchMap(albumResults){
        it.data
    }

    var networkErrors: LiveData<String> = Transformations.switchMap(albumResults){
        repository.networkErrors
    }

    /**
     * Query album search data from [Repository]
     * **/
    fun searchAlbums(query: String){
        queryLiveData.postValue(query)
    }

}