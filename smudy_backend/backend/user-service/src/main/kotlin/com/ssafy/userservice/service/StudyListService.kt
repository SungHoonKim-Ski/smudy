package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.entity.StudyList
import com.ssafy.userservice.db.postgre.repository.StudyListRepository
import com.ssafy.userservice.db.postgre.repository.mapping.SongIdMapping
import com.ssafy.userservice.dto.request.SongId
import com.ssafy.userservice.dto.response.SongSimple
import com.ssafy.userservice.dto.response.StudyListResponse
import com.ssafy.userservice.exception.exception.EndOfPageException
import com.ssafy.userservice.exception.exception.StudyListAllExistException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList

@Service
class StudyListService (
        private val studyListRepository: StudyListRepository,
        private val songService: SongService,

) {
    private val logger = KotlinLogging.logger {  }
    @Transactional
    fun getUserStudyList(userInternalId: UUID, pageable: Pageable) : StudyListResponse {

        val songIds = getUserStudySongs(userInternalId, pageable).map { it.getSongId() }
        logger.info { songIds.toString() }
        if (songIds.isEmpty()) throw EndOfPageException("스터디 리스트가 존재하지 않음")
        return StudyListResponse(
                songService.findAllBySongIdsIn(songIds).map {
                    SongSimple(
                            songArtist = it.songArtist,
                            songName = it.songName,
                            albumJacket = it.albumJacket,
                            spotifyId = it.spotifyId
                    )
                }
        )

    }

    @Transactional
    fun getUserStudySongs(userInternalId: UUID, pageable: Pageable): List<SongIdMapping> {
        return studyListRepository.findAllByUserInternalId(userInternalId, pageable).toList()
    }

    @Transactional
    fun addUserStudyList(userInternalId: UUID, songIds: List<SongId>): ArrayList<Int> {
        val existSongs = getUserStudyListIn(userInternalId, songIds).map { it.getSongId() }

        val saveSongs = songIds.filter {
            it.songId !in existSongs
        }.map {
            StudyList(
                    studyListId = -1,
                    userInternalId = userInternalId,
                    songId = it.songId
            )
        }

        val saveCount = studyListRepository.saveAll(saveSongs).size
        if (saveCount == 0) throw StudyListAllExistException("모든 곡이 스터디리스트에 존재하거나, 음악 ID가 유효하지 않습니다")
        return arrayListOf(saveCount, songIds.size - saveCount)
    }

    fun getUserStudyListIn(userInternalId: UUID, songIds: List<SongId>) : List<SongIdMapping> {
        return studyListRepository.findAllByUserInternalIdAndSongIdIn(userInternalId, songIds.map { it.songId })
    }


}