package com.ijikod.lastfm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ijikod.lastfm.data.model.Album
import com.ijikod.lastfm.data.model.AlbumDetails

@Database(entities = [Album::class, AlbumDetails::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LastFmDatabase: RoomDatabase() {

    /** [VideosDao] instance  **/
    abstract fun albumsDao(): AlbumsDao

    /**
     * Database singleton access
     * **/
    companion object{

        @Volatile
        private var INSTANCE: LastFmDatabase? = null

        fun getInstance(context : Context): LastFmDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                LastFmDatabase::class.java, "LASTFM.db")
                .build()
    }
}