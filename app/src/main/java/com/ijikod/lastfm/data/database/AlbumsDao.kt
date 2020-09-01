package com.ijikod.lastfm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.lastfm.data.model.Album

@Dao
interface AlbumsDao {

    /**
     * Insert all items into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(albums: List<Album>)

    @Query("")
    fun resultsByQuery(queryString: String): LiveData<List<Album>>

    /**
     * Delete all data in local repository
     * **/
    @Query("Delete FROM albums")
    fun clearRepos()
}