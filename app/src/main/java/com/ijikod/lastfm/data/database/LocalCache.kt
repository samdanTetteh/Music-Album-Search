package com.ijikod.lastfm.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.ijikod.lastfm.data.model.Album
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class LocalCache(private val albumsDao: AlbumsDao, private val ioExecutor: Executor) {


    /**
     * Insert a list of albums in the database, on a background thread.
     */
    fun insert(albums: List<Album>) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", "inserting ${albums.size} repos")
            albumsDao.insertAll(albums)
        }
    }


    /**
     * Request a LiveData<List<[Album]>> from the Dao, based on a album name.
     * @param query album name
     */

    fun albumsByQuery(query: String): LiveData<List<Album>>{
        val searchQuery = "%${query.replace(' ', '%')}"
        return albumsDao.resultsByQuery(searchQuery)
    }

}