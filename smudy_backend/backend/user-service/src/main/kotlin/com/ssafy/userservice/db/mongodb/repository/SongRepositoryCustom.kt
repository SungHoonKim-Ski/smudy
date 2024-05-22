package com.ssafy.userservice.db.mongodb.repository

import com.ssafy.userservice.dto.response.SongSimple


interface SongRepositoryCustom {

    fun findRandomSongs(size: Long): List<SongSimple>
}