package com.ijikod.lastfm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.lastfm.Utilities.hideKeyboard
import com.ijikod.lastfm.application.LastFMApplication
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.databinding.FragmentSearchBinding
import com.ijikod.lastfm.presentation.vm.SearchViewModel
import com.ijikod.lastfm.ui.adapter.SearchAdapter


/**
 * Fragment to show Album list data
 * **/
class SearchFragment: Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter : SearchAdapter
    private lateinit var searchList : RecyclerView
    private lateinit var emptyText : TextView
    private lateinit var searchTextField : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the view model
        viewModel = ViewModelProvider(requireActivity(), LastFMApplication.provideViewModelFactory(requireContext()))
            .get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        initScreenItems(binding)
        initAdapter()
        initSearch(query)
        return binding.root
    }


    private fun initScreenItems(binding: FragmentSearchBinding){
        // Initialise screen views
        searchList = binding.list
        emptyText = binding.emptyList
        searchTextField = binding.searchRepo
    }


    private fun initAdapter() {
        // Initialise search adapter for  recycle view
        adapter = SearchAdapter {
            navigate(it)
        }

        // Assign adapter to recycler view
        searchList.adapter = adapter

        // Subscribe to data changes in view model to show Album search results
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.setDataSet(it)
        })

        // Subscribe to data changes in view model to show network errors
        viewModel.networkErrors.observe(viewLifecycleOwner, Observer<String> {
            Log.d("Search", it)
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun initSearch(query: String) {
        searchTextField.setText(query)

        // Trigger search from search button in keyboard.
        searchTextField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        // Trigger search from enter button on keyboard.
        searchTextField.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }


    private fun updateRepoListFromInput() {
        // Hide keyboard after search is triggered
        hideKeyboard()

        // Search for album with album name presented in search field
        searchTextField.text.trim().let {
            if (it.isNotEmpty()) {
                searchList.scrollToPosition(0)
                viewModel.searchAlbums(it.toString())
                adapter.setDataSet(emptyList())
            }
        }
    }


    private fun showEmptyList(show: Boolean) {
        // show or hid list based on size of list data returned
        if (show) {
            emptyText.visibility = View.VISIBLE
            searchList.visibility = View.GONE
        } else {
            emptyText.visibility = View.GONE
            searchList.visibility = View.VISIBLE
        }
    }



    private fun navigate(album: Album){

    }



    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}