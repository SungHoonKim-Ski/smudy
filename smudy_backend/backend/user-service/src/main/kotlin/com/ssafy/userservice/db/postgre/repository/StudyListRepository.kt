package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.StudyList
import com.ssafy.userservice.db.postgre.repository.mapping.SongIdMapping
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StudyListRepository : JpaRepository<StudyList, Int> {

    fun findAllByUserInternalId(userInternalId: UUID, pageable: Pageable) : Page<SongIdMapping>

    fun findAllByUserInternalIdAndSongIdIn(userInternalId: UUID, songIds: List<String>) : List<SongIdMapping>

    fun deleteByUserInternalIdAndSongId(userInternalId: UUID, songId: String)

}