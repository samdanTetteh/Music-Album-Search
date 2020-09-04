package com.ijikod.lastfm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class AlbumResults (
    val results: Results
)

data class Results (
    val albummatches: Albummatches
)

data class Albummatches (
    val album: List<Album>
)

/**
 * Immutable model class for a lastF album repo that holds all the information about a repository.
 * Objects of this type are received from the lastFm API.
 * This class also defines the Room repos table, where the repo [id] is the primary key which is auto generated
 */
@Entity(tableName = "albums")
data class Album (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val artist: String?,
    val url: String?,
    val image: List<Image>?,
    val streamable: String?,
    val mbid: String?
)

data class Image (
    @field:SerializedName("#text")
    var text: String,
    var size: String
)