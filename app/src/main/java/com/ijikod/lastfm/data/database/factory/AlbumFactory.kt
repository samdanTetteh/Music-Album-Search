package com.ijikod.lastfm.data.database.factory

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.ijikod.lastfm.data.model.*

/**
 * [AlbumFactory] class to help with testing
 * **/
class AlbumFactory {

    fun makeAlbum() : Album{
        val image = Image(text = "https://lastfm.freetls.fastly.net/i/u/34s/e17e8ab11291469a84bc869d59b9cf09.png", size = "small")

        return Album(
            id = 0,
            name = "The Gift of Game",
            artist = "Crazy Town",
            url = "https://www.last.fm/music/Crazy+Town/The+Gift+of+Game",
            image = listOf(image, image, image),
            mbid = "391945ae-7a6b-4e53-a72c-e20019860881",
            streamable = "0"
        )
    }


    fun makeAblumDtails(): AlbumDetails{
        val track = Track(
            name = "balance (mufasa interlude)",
            url = "https://www.last.fm/music/Beyonc%C3%A9/_/balance+(mufasa+interlude)",
            duration = "43"
        )


        val wiki = Wiki(
            published = "19 Jul 2019, 04:42",
            summary = "The Lion King: The Gift is a soundtrack album by Beyoncé, released on July 19, 2019 via Parkwood Entertainment.",
            content = "The Lion King: The Gift is a soundtrack album by Beyoncé, released on July 19, 2019 via Parkwood Entertainment."
        )

        return AlbumDetails(
            id = 0,
            name = "The Lion King: The Gift",
            artist = "Beyoncé",
            url = "https://www.last.fm/music/Beyonc%C3%A9/The+Lion+King:+The+Gift",
            listeners = "125389",
            tracks = Tracks(track = listOf(track, track)),
            wiki = wiki,
            mbid = "391945ae-7a6b-4e53-a72c-e20019860881"
        )

    }



}