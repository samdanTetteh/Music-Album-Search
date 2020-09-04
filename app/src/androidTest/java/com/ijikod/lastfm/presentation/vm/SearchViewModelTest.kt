package com.ijikod.lastfm.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ijikod.lastfm.TestUtils.Mockito
import com.ijikod.lastfm.application.LastFMApplication
import com.ijikod.lastfm.data.model.Album
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

/**
 * [SearchViewModelTest] class to test search view model
 * **/
@RunWith(AndroidJUnit4::class)
class SearchViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var vm : SearchViewModel


    private  val observer: Observer<List<Album>> = Mockito().mock()


    @Before
    fun setUp(){
        vm = SearchViewModel(LastFMApplication.provideRepository(ApplicationProvider.getApplicationContext()))
    }

    @Test
    fun `test_model_sate_change`(){
        vm.albums.observeForever(observer)
        vm.searchAlbums ("the gift")

        org.mockito.Mockito.verify(observer).onChanged(vm.albums.value)
    }

    @Test
    fun testForReturnValues(){
        vm.albums.observeForever(observer)
        vm.searchAlbums("the gift")

        vm.albums.value?.isNotEmpty()?.let { assert(it) }
    }


}