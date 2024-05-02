package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.dto.request.SongId
import com.ssafy.userservice.dto.response.SongSimple
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException


@Service
class SongService(
        private val songRepository: SongRepository
) {

    fun findAllBySongIds(songIds: List<SongId>) : List<SongSimple> {
        return songRepository.findAllBySpotifyIdIn(
                songIds.map { it.songId }
        ).let { songList ->
            songList.map {
                SongSimple(
                        spotifyId = it.spotifyId,
                        albumJacket = it.albumJacket,
                        songName = it.songName,
                        songArtist = it.songArtist
                )
            }
        }
    }

}