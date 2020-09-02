package com.ijikod.lastfm.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijikod.lastfm.data.Repository
import com.ijikod.lastfm.presentation.vm.AlbumDetailsViewModel
import com.ijikod.lastfm.presentation.vm.SearchViewModel

class ViewModelFactory (private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(AlbumDetailsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AlbumDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}