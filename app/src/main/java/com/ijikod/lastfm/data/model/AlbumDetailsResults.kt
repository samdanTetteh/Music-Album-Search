package com.ijikod.lastfm.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [AlbumDetailsResults] data class to hold album details data
 * **/
data class AlbumDetailsResults (
    val album: AlbumDetails
)

@Entity(tableName = "album_details")
data class AlbumDetails (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String?,
    val artist: String,
    val mbid: String?,
    val url: String?,
    val listeners: String?,
    @Embedded
    val tracks: Tracks?,
    @Embedded
    val wiki: Wiki?
)

data class Tracks (
    val track: List<Track>
)

data class Track (
    val name: String?,
    val url: String?,
    val duration: String?
)

data class Wiki (
    val published: String?,
    val summary: String?,
    val content: String?
)