package com.ijikod.lastfm.data.api

import com.ijikod.lastfm.data.model.AlbumDetailsResults
import com.ijikod.lastfm.data.model.AlbumResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit [LastFmApiService] interface defining api REST requests
 * **/
interface LastFmApiService{

    /**
     * Get [AlbumResults] from searching by album name
     * **/
    @GET(value = "2.0/?method=album.search&format=json")
    suspend fun searchAlbum(@Query("album") album: String, @Query("api_key") apiKey: String) : Response<AlbumResults>


    /**
     * Get [AlbumDetailsResults] from selected
     * @param album
     * @param artist
     * **/
    @GET("2.0/?method=album.getinfo&format=json")
    suspend fun getAlbumDetails(@Query("album") album: String, @Query("artist") artist: String, @Query("api_key") apiKey: String) : Response<AlbumDetailsResults>


}