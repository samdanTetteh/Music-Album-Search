package com.ijikod.lastfm.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.data.model.Album

class AlbumDetailsViewModel(private val repository: Repository): ViewModel() {

    // Shared album item between details and search fragment
    val selectedAlbum = MutableLiveData<Album>()

    val albumsDetails = repository.albumDetails

    var networkErrors = repository.networkErrors


    /**
     * Set value to Shared album [Album] used in Details Fragment
     * **/
    fun setSelectedAlbum(album : Album){
        selectedAlbum.value = album
    }


    /**
     * Load album details from [Repository]
     * **/
    fun getAlbumDetails(){
        selectedAlbum.value?.let {
            repository.albumDetails(it.name ?: "", it.artist ?: "", it.mbid ?: "")
        }
    }
}