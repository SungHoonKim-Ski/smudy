package com.ssafy.studyservice.service

import com.ssafy.studyservice.db.postgre.entity.Problem
import com.ssafy.studyservice.db.postgre.repository.ProblemBoxRepository
import com.ssafy.studyservice.db.postgre.repository.ProblemRepository
import com.ssafy.studyservice.dto.response.*
import com.ssafy.studyservice.service.feign.UserFeignClient
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProblemService (
        private val problemRepository: ProblemRepository,
        private val userFeignClient: UserFeignClient,
        private val problemBoxRepository: ProblemBoxRepository,
){

    private val logger = KotlinLogging.logger {  }

    @Transactional
    fun getProblemsByProblemBoxId(problemBoxId: Int) : List<Problem>{
        return problemRepository.findByProblemBoxId(problemBoxId)
    }

    fun getFillQuiz(songId: String): FillResponse {
        return userFeignClient.getFillQuiz(songId)
    }

    fun getPronounceQuiz(songId: String): PronounceResponse {
        return userFeignClient.getPronounceQuiz(songId)
    }

    @Transactional
    fun getExpressQuiz(songId: String): ExpressResponse {
        val songSimple = userFeignClient.getExpressQuiz(songId)
        val randomProblemBox = problemBoxRepository.findRandomProblemBoxBySongId(songId)
        val problems = problemRepository.findByProblemBoxId(randomProblemBox.problemBoxId)
        return ExpressResponse(
                songName = songSimple.songName,
                songArtist = songSimple.songArtist,
                songId = songSimple.spotifyId,
                albumJacket = songSimple.albumJacket,
                problemBoxId = randomProblemBox.problemBoxId,
                problemResponses = problems.map { problem ->
                    ProblemResponse(
                            lyricSentenceKo = problem.sentenceKo,
                            lyricSentenceEn = problem.sentenceEn
                    )
                },
        )
    }

    @Transactional
    fun getPickQuiz(songId: String): PickResponse {
        val songSimple = userFeignClient.getExpressQuiz(songId)
        val randomProblemBox = problemBoxRepository.findRandomProblemBoxBySongId(songId)
        val problems = problemRepository.findByProblemBoxId(randomProblemBox.problemBoxId)
        return PickResponse(
                songName = songSimple.songName,
                songArtist = songSimple.songArtist,
                songId = songSimple.spotifyId,
                albumJacket = songSimple.albumJacket,
                problemBoxId = randomProblemBox.problemBoxId,
                problemResponses = problems.map { problem ->
                    ProblemResponse(
                            lyricSentenceKo = problem.sentenceKo,
                            lyricSentenceEn = problem.sentenceEn
                    )
                },
        )
    }


}