package com.ijikod.lastfm.ui.dataBinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ijikod.lastfm.R


/**
 *  A [BindingAdapter] that uses the Glide library to load the video image.
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, image : String){
    Glide.with(imageView.context).load(image).placeholder(R.drawable.last_fm).into(imageView)
}