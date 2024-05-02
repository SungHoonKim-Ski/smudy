package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.entity.StudyList
import com.ssafy.userservice.db.postgre.repository.StudyListRepository
import com.ssafy.userservice.db.postgre.repository.mapping.SongIdMapping
import com.ssafy.userservice.dto.response.SongSimple
import com.ssafy.userservice.dto.response.StudyListResponse
import com.ssafy.userservice.dto.response.WrongLyricResponse
import com.ssafy.userservice.exception.exception.EndOfPageException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.ws.rs.core.NoContentException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudyListService (
        private val studyListRepository: StudyListRepository,
        private val songRepository: SongRepository,

) {
    private val logger = KotlinLogging.logger {  }
    @Transactional
    fun getUserStudyList(userInternalId: UUID, pageable: Pageable) : StudyListResponse {

        val songIds = getUserStudySongs(userInternalId, pageable).map { it.getSongId() }
        logger.info { songIds.toString() }
        if (songIds.isEmpty()) throw EndOfPageException("스터디 리스트가 존재하지 않음")
        return StudyListResponse(
                songRepository.findAllBySpotifyIdIn(songIds).map {
                    SongSimple(
                            songArtist = it.songArtist,
                            songName = it.songName,
                            albumJacket = it.albumJacket,
                            spotifyId = it.spotifyId
                    )
                }
        )

    }

    fun getUserStudySongs(userInternalId: UUID, pageable: Pageable): List<SongIdMapping> {
        return studyListRepository.findAllByUserInternalId(userInternalId, pageable).toList()
    }


}