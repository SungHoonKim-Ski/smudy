package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.entity.Wrong
import com.ssafy.userservice.db.postgre.repository.WrongRepository
import com.ssafy.userservice.dto.response.WrongLyricResponse
import jakarta.ws.rs.core.NoContentException
import org.springframework.stereotype.Service
import java.util.*

@Service
class WrongService (
        private val wrongRepository: WrongRepository,
        private val openAIService: OpenAIService
){


    fun getRandomWrongSentence(userInternalId: UUID) : WrongLyricResponse{
        val userWrong = wrongRepository.findRandomWrongSentenceByUserInternalId(userInternalId)
        userWrong?.let {
            val response = WrongLyricResponse(
                    wrongLyricsEn = it.lyricSentenceEn,
                    wrongLyricsKo = it.lyricSentenceKo
            )

            // 실시간 번역이 필요한 경우 번역을 한 뒤 response
            if (response.wrongLyricsEn == response.wrongLyricsKo) {
                response.wrongLyricsKo = openAIService.translateLyric(it.lyricSentenceEn)
            }
            
            return response

        } ?: throw NoContentException("틀린 단어가 존재하지 않음")
    }

    fun saveAll(wrongs: List<Wrong>) {
        wrongRepository.saveAll(wrongs)
    }

}