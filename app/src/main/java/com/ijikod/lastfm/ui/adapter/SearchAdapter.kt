package com.ijikod.lastfm.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.lastfm.data.model.Album

/**
 * Adapter for the list of search results.
 */
class SearchAdapter(private val navigate: (Album) -> Unit): RecyclerView.Adapter<AlbumViewHolder>() {

    private var dataList = listOf<Album>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.create(parent, navigate)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val albumDataItem = dataList[position]
        with(holder){
            bindData(albumDataItem)
        }
    }

    /**
     * Set items to adapter and notify data has changed
     * **/
    fun setDataSet(listData : List<Album>){
        dataList = listData
        notifyDataSetChanged()
    }
}