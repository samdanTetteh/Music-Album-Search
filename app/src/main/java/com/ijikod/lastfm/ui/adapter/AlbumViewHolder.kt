package com.ijikod.lastfm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ijikod.lastfm.R
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.databinding.SearchItemBinding

/**
 * View holder to display recycler view list data
 * **/
class AlbumViewHolder(binding: SearchItemBinding, val navigateToDetail: (Album) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    private val albumNameTxt = binding.albumTitleTxt
    private val artistNameTxt = binding.artistNameTxt
    private val albumArtwork = binding.albumThumb


    fun bindData(album: Album){
        albumNameTxt.text = album.name
        artistNameTxt.text = album.artist
        Glide.with(albumArtwork.context).load(album.image[0]).placeholder(R.mipmap.ic_launcher).into(albumArtwork)

        /**
         * Navigate to details screen
         * **/
        this.itemView.setOnClickListener {
            navigateToDetail(album)
        }
    }


    companion object {
        /**
         * Singleton access to [VideoViewHolder] returning inflated view
         * **/
        fun create(parent: ViewGroup, navigate: (Album) -> Unit): AlbumViewHolder {
            val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AlbumViewHolder(binding, navigate)
        }
    }


}