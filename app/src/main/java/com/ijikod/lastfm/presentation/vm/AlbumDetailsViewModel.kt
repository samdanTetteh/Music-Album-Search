package com.ijikod.lastfm.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumData
import com.ijikod.lastfm.data.model.AlbumDetails
import com.ijikod.lastfm.data.model.AlbumSearchResults

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


    fun getAlbumDetails(){
        selectedAlbum.value?.let {
            Log.d("details VM= mbid", it.mbid)
            repository.albumDetails(it.name, it.artist, it.mbid)
        }
    }
}