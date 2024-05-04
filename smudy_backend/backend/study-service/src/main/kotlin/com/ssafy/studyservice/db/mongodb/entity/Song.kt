package com.ssafy.studyservice.db.mongodb.entity

import jakarta.persistence.Embedded
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "trackinfo_v2")
class Song(

        @MongoId
        var id: String,

        @Field(name = "album_name")
        var albumName: String,

        @Field(name = "song_artist")
        var songArtist: String,

        @Field(name = "song_duration_ms")
        var songDurationMs: String,

        @Field(name = "song_explicit")
        var songExplicit: Boolean = false,

        @Field(name = "song_genre")
        var songGenre: String,

        @Embedded
        @Field(name = "song_lyrics")
        var songLyrics: List<Lyric> = listOf(),

        @Field(name = "song_name")
        var songName: String,

        @Field(name = "spotify_id")
        var spotifyId: String,

        @Field(name = "album_jacket")
        var albumJacket: String
)