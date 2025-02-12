package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.entity.Song
import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.dto.response.SongSimple
import com.ssafy.userservice.dto.response.feign.*
import com.ssafy.userservice.exception.exception.SongNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SongService(
        private val songRepository: SongRepository,
) {

    private val log = KotlinLogging.logger {  }
    
    @Transactional(readOnly = true)
    fun getFillQuiz(songId: String) : FillResponse {
        return songRepository.findBySpotifyId(songId)?.let { song ->
            FillResponse(
                    songId = song.spotifyId,
                    songArtist = song.songArtist,
                    songDuration = song.songDuration,
                    songName = song.songName,
                    albumJacket = song.albumJacket,
                    lyricEnd = song.songDuration,
                    lyricsBlank = song.songLyrics.map { lyric ->
                        LyricBlank(
                                lyricStartTimeStamp = lyric.lyricTimestamp,
                                lyricBlank = lyric.lyricBlank,
                                lyricBlankAnswer = lyric.lyricBlankAnswer
                                
                        )
                    }
            )
        } ?: throw SongNotFoundException("Id가 ${songId}인 노래가 존재하지 않음(FILL)")
    }

    @Transactional(readOnly = true)
    fun getPickQuiz(songId: String) : SongSimple {

        return songRepository.findBySpotifyId(songId)?.let { song ->
            SongSimple(
                    songArtist = song.songArtist,
                    songName = song.songName,
                    albumJacket = song.albumJacket,
                    spotifyId = song.spotifyId
            )
        } ?: throw SongNotFoundException("Id가 ${songId}인 노래가 존재하지 않음(FILL)")
    }

    @Transactional(readOnly = true)
    fun getExpressQuiz(songId: String) : SongSimple {
        return songRepository.findBySpotifyId(songId)?.let { song ->
            SongSimple(
                    songArtist = song.songArtist,
                    songName = song.songName,
                    albumJacket = song.albumJacket,
                    spotifyId = song.spotifyId
            )
        } ?: throw SongNotFoundException("Id가 ${songId}인 노래가 존재하지 않음(EXPRESS)")
    }

    @Transactional(readOnly = true)
    fun getPronounceQuiz(songId: String) : PronounceResponse {
        return songRepository.findBySpotifyId(songId)?.let { song ->
            PronounceResponse(
                    songId = song.spotifyId,
                    songArtist = song.songArtist,
                    songName = song.songName,
                    albumJacket = song.albumJacket,
                    lyrics = song.songLyrics.map { Lyric(it.lyricSentenceEn) }
            )
        } ?: throw SongNotFoundException("Id가 ${songId}인 노래가 존재하지 않음(Pronounce)")
    }

    @Transactional(readOnly = true)
    fun findAllBySongIdsIn(songIds: List<String>) : List<SongSimple> {
        return songRepository.findAllBySpotifyIdIn(songIds).map {
            song -> SongSimple(
                songName = song.songName,
                songArtist = song.songArtist,
                albumJacket = song.albumJacket,
                spotifyId = song.spotifyId
            )
        }
    }

    @Transactional(readOnly = true)
    fun findSongBySongId(songId: String) : Song {
        return songRepository.findBySpotifyId(songId)
                ?: throw SongNotFoundException("spotify Id가 ${songId}인 노래가 존재하지 않음")
    }
}