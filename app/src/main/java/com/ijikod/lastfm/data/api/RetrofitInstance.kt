package com.ijikod.lastfm.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_KEY = "9607b9595da1882f043c572ea9329cb7"
class RetrofitInstance {


    /**
     * Retrofit singleton instance using [MoshiConverterFactory]
     * **/
    companion object{
        private const val BASE_URL = "http://ws.audioscrobbler.com/"

        /**
         *Initialise retrofit instance to be used for http requests
         * **/
        private fun retrofit() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }


        /**
         * [LastFmApiService] to call http Api methods
         * **/
        val lastApiService: LastFmApiService by lazy {
            retrofit().create(LastFmApiService::class.java)
        }
    }



}