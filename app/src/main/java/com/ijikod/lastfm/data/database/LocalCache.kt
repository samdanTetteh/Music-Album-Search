package com.ijikod.lastfm.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source. This ensures that methods are triggered on the
 * correct executor.
 */
class LocalCache(private val albumsDao: AlbumsDao, private val ioExecutor: Executor) {


    /**
     * Insert a list of albums in the database, on a background thread.
     */
    fun insertAlbum(albums: List<Album>) {
        ioExecutor.execute {
            albumsDao.insertAll(albums)
        }
    }

    /**
     * Insert a [AlbumDetails] in the database, on a background thread.
     */
    fun insertAlbumDetails(album: AlbumDetails) {
        ioExecutor.execute {
            albumsDao.insertAlbumDetails(album)
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

    /**
     * Request [AlbumDetails] from the Dao, based on a album mid.
     * @param query album name
     */
    fun albumsByMid(mid: String): LiveData<AlbumDetails>{
        return albumsDao.albumByMid(mid)
    }


    /**
     * Remove all [Album] items  from local database with similar search query as name
     * @param query album name
     */
    fun removeAlbumsByQuery(query: String){
        val searchQuery = "%${query.replace(' ', '%')}"
        ioExecutor.execute{
            albumsDao.clearAlbums(searchQuery)
        }
    }
}