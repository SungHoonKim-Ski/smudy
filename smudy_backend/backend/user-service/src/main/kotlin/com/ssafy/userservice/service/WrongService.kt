package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.repository.WrongRepository
import com.ssafy.userservice.dto.response.WrongLyricResponse
import jakarta.ws.rs.core.NoContentException
import org.springframework.stereotype.Service
import java.util.*

@Service
class WrongService (
        private val wrongRepository: WrongRepository
){

    fun getRandomWrongSentence(userInternalId: UUID) : WrongLyricResponse{
        val userWrong = wrongRepository.findRandomWrongSentenceByUserInternalId(userInternalId)
        userWrong?.let {
            return WrongLyricResponse(
                    wrongLyricsKo = it.lyricSentenceKo,
                    wrongLyricsEn = it.lyricSentenceEn
            )
        } ?: throw NoContentException("틀린 단어가 존재하지 않음")
    }

}