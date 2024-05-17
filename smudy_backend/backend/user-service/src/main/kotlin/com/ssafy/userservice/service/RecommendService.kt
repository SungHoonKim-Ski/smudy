package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import com.ssafy.userservice.dto.response.RecommendResponse
import com.ssafy.userservice.dto.response.SongSimple
import com.ssafy.userservice.exception.exception.SongNotFoundException
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import java.util.*


@Service
class RecommendService(
    private val songRepository: SongRepository,
    private val learnReportRepository: LearnReportRepository,
    private val vectorStore: VectorStore,
) {

    fun getRecommendations(userInternalId: UUID): RecommendResponse {

        val userLearnReports = learnReportRepository.findAllByUserInternalId(userInternalId = userInternalId)
        val response = RecommendResponse()

        // 해당 유저의 히스토리가 존재하지 않음
        if (userLearnReports.isEmpty()) {
            // 랜덤한 노래 6곡 추천
            response.userRecommendSongs.addAll(songRepository.findRandomSongs(6))
            return response
        }

        // 히스토리의 순회하면서 해당 노래와 유사한 노래 추천
        // Set 통해 Ids 에서 중복 제거
        val originIds = userLearnReports.map { it.songId }.toSet()
        val recommendIds = mutableSetOf<String>()

        val topK = when (originIds.size) {
            1 -> 7 // 자기자신 제외한 6개 추천
            2 -> 4 // 자기자신 제외한 3개 추천 * 2
            else -> 3 // 자기자신 제외한 2개 추천 * 3
        }

        originIds.forEach { spotifyId ->
            songRepository.findBySpotifyId(spotifyId)?.let { song ->
                val results: List<Document> = vectorStore.similaritySearch(
                    SearchRequest
                        .query("${song.albumName} ${song.songArtist} ${song.songName} ${song.songGenre}")
                        .withTopK(topK)
                )
                // 첫 요소(자기자신)를 제외한 2..K번째 요소 선택
                recommendIds.addAll(results.drop(1).map { it.id })
                // recommendIds가 6개가 되면 종료
                if (recommendIds.size >= 6) return@forEach
            }
        }

        response.userRecommendSongs.addAll(recommendIds.take(6).mapNotNull { spotifyId ->
            songRepository.findBySpotifyId(spotifyId)?.let {
                SongSimple(it.songArtist, it.songName, it.spotifyId, it.albumJacket)
            }
        })

        return response
    }
}