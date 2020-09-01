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

    private val albumLiveData = MutableLiveData<AlbumSearchResults>()

    var albums: LiveData<List<Album>> = Transformations.switchMap(albumLiveData){
        repository.albumListData
    }

    var networkErrors: LiveData<String> = Transformations.switchMap(albumLiveData){
        repository.networkErrors
    }

    /**
     * Query album search data from [Repository]
     * **/
    fun searchAlbums(query: String){
        albumLiveData.value = repository.listAlbums(query)
    }

}