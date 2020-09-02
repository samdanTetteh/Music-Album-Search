package com.ijikod.lastfm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ijikod.lastfm.application.LastFMApplication
import com.ijikod.lastfm.databinding.FragmentDetailsBinding
import com.ijikod.lastfm.presentation.vm.AlbumDetailsViewModel

/**
 * [DetailsFragment] view class to display selected album details
 * **/
class DetailsFragment: Fragment() {

    lateinit var viewModel : AlbumDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), LastFMApplication.provideViewModelFactory(requireContext()))
            .get(AlbumDetailsViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }
}