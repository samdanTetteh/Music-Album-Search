package com.ijikod.lastfm.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails
import com.ijikod.lastfm.data.model.AlbumSearchResults

class AlbumDetailsViewModel(private val repository: Repository): ViewModel() {

    // Shared album item between details and search fragment
    val selectedAlbum : MutableLiveData<Album> = MutableLiveData<Album>()

    var albumDetailsResults: LiveData<AlbumDetails> = Transformations.map(selectedAlbum){
        repository.getAlbumsDetails(it.name, it.artist, it.mbid)
    }

    val albumsDetails : LiveData<AlbumDetails> = Transformations.switchMap(albumDetailsResults){
        repository.albumDetails
    }

    var networkErrors: LiveData<String> = Transformations.switchMap(albumDetailsResults){
        repository.networkErrors
    }


    /**
     * Set value to Shared album [Album] used in Details Fragment
     * **/
    fun setSelectedAlbum(album : Album){
        selectedAlbum.value = album
    }
}