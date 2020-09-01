package com.ijikod.lastfm.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.lastfm.data.model.Album

/**
 * Adapter for the list of search results.
 */
class SearchAdapter(private val dataList: List<Album>, private val navigate: (Album) -> Unit): RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.create(parent, navigate)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumDataItem = dataList[0]
        with(holder){
            bindData(albumDataItem)
        }
    }
}