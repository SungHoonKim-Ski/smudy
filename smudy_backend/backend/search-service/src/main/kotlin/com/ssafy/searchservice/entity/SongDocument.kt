package com.ssafy.searchservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "es_songs")
data class SongDocument(

    @Id
    @Field(type = FieldType.Keyword, name = "song_spotify_id")
    val id: String,

    @Field(type = FieldType.Text, analyzer = "english_analyzer", name = "song_artist")
    val songArtist: String,

    @Field(type = FieldType.Text, analyzer = "english_analyzer", name = "song_lyrics")
    val songLyrics: String,

    @Field(type = FieldType.Text, analyzer = "english_analyzer", name = "song_name")
    val songName: String,

    @Field(type = FieldType.Keyword, name = "album_jacket")
    val albumJacket: String
)
