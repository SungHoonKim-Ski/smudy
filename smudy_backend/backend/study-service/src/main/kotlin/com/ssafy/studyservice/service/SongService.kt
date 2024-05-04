package com.ssafy.studyservice.service

import com.ssafy.studyservice.db.mongodb.repository.SongRepository
import com.ssafy.studyservice.dto.request.SongId
import com.ssafy.studyservice.dto.response.SongSimple
import org.springframework.stereotype.Service


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