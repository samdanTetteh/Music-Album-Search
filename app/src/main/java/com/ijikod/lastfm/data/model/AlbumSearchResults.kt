package com.ijikod.lastfm.data.model

import androidx.lifecycle.LiveData

/**
 * AlbumSearchResult from a search, which contains LiveData<List<[Album]>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class AlbumSearchResults(
    val data: LiveData<List<Album>>,
    val networkErrors: LiveData<String>
)