package com.ijikod.lastfm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails

@Dao
interface AlbumsDao {

    /**
     * Insert all items into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(albums: List<Album>)

    /**
     * Insert [AlbumDetails] into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbumDetails(album: AlbumDetails)

    /**
     * Select cached [AlbumDetails]
     * **/
    @Query("Select * from album_details where mbid = :mbid")
    fun albumByMid(mbid: String): LiveData<AlbumDetails>

    /**
     * Select cached [Album] list
     * **/
    @Query("SELECT * FROM albums WHERE name LIKE :queryString ORDER BY id ASC")
    fun resultsByQuery(queryString: String): LiveData<List<Album>>

    /**
     * Delete all data in local repository
     * **/
    @Query("Delete FROM albums where name LIKE :queryString")
    fun clearAlbums(queryString: String)
}