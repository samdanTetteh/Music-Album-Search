package com.ijikod.lastfm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ijikod.lastfm.application.LastFMApplication
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails
import com.ijikod.lastfm.databinding.FragmentDetailsBinding
import com.ijikod.lastfm.presentation.vm.AlbumDetailsViewModel

/**
 * [DetailsFragment] view class to display selected album details
 * **/
class DetailsFragment: Fragment() {

    lateinit var viewModel : AlbumDetailsViewModel
    lateinit var progressBar: ProgressBar
    lateinit var albumName: TextView
    lateinit var albumArtist: TextView
    lateinit var albumTracks: TextView
    lateinit var albumWiki:TextView
    lateinit var tracksTitle : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity(), LastFMApplication.provideViewModelFactory(requireActivity()))
            .get(AlbumDetailsViewModel::class.java)
        binding.vm = viewModel

        initPage(binding)
        subscribeToDataChanges()
        return binding.root
    }

    // Initialise page views
    private fun initPage(binding: FragmentDetailsBinding){
        progressBar = binding.loadingProgress
        albumName = binding.nameTxt
        albumArtist = binding.artistTxt
        albumTracks = binding.albumTracks
        albumWiki = binding.albumDetails
        tracksTitle = binding.tracksTitle
    }

    // Show page details
    private fun showDetails(){
        progressBar.visibility = View.INVISIBLE
        albumName.visibility = View.VISIBLE
        albumArtist.visibility = View.VISIBLE
        albumTracks.visibility = View.VISIBLE
        albumWiki.visibility = View.VISIBLE
    }

    // set retrieved data to fragment views
    private fun showAlbumDetails(albumDetails: AlbumDetails){
        albumName.text = albumDetails.name
        albumArtist.text = albumDetails.artist

        if (albumDetails.tracks?.track.isNullOrEmpty().not()){
            tracksTitle.visibility = View.VISIBLE
            albumTracks.text = albumDetails.tracks?.track?.joinToString {
                it.name.toString()
            }
        }

        albumDetails.wiki?.summary?.let {
            albumWiki.text = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }


    // Listen on event changes from viewModel
    private fun subscribeToDataChanges(){
        viewModel.getAlbumDetails()
        viewModel.albumsDetails.observe(viewLifecycleOwner, Observer {
            showDetails()
            it?.let {
                showAlbumDetails(it)

                // Make sure view model does not publish any more updates to view
                // until there is a data change
                viewModel.albumsDetails.value = null
            }

        })


        viewModel.networkErrors.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
    }



}