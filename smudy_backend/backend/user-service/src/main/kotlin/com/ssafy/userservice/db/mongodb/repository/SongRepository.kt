package com.ssafy.userservice.db.mongodb.repository

import com.ssafy.userservice.db.mongodb.entity.Song
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : MongoRepository<Song, String> {
    fun findBySpotifyId(spotifyId: String): Song?
    fun findSongBySongArtist(songArtist: String): List<Song>

    fun findAllBySpotifyIdIn(spotifyId: List<String>) : List<Song>
}