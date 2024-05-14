package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import com.ssafy.userservice.dto.response.RecommendResponse
import com.ssafy.userservice.dto.response.SongSimple
import org.springframework.stereotype.Service
import java.util.*


@Service
class RecommendService(
    private val songService: SongService,
    private val learnReportRepository: LearnReportRepository
) {

    fun getRecommendations(userInternalId: UUID): RecommendResponse {

        val userLearnReports = learnReportRepository.findAllByUserInternalId(
            userInternalId = userInternalId
        )

        val response = RecommendResponse()

        // 해당 유저의 히스토리가 존재하지 않음
        if (userLearnReports.isEmpty()) {

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Ingrid Michaelson"
                    , songName = "You And I"
                    , spotifyId = "7aohwSiTDju51QmC54AUba"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e022f9b47bdc2b1c7cae7b014af"
                )
            )

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Ingrid Michaelson"
                    , songName = "Everybody"
                    , spotifyId = "3SBzDgdTwHOMSik82ZI6L2"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e0279f6239caf413738d82d3b0f"
                )
            )

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Weezer"
                    , songName = "Say It Ain't So"
                    , spotifyId = "3qmncUJvABe0QDRwMZhbPt"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e023be73241fed381886709f761"
                )
            )

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Weezer"
                    , songName = "My Name Is Jonas"
                    , spotifyId = "08k0JhCj8oJLB7Xuclr57s"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e02f6e2ddb1cba9a5900de38cd1"
                )
            )

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Megadeth"
                    , songName = "Symphony Of Destruction"
                    , spotifyId = "1955ZZJe1TzmSR0TomnNjI"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e027d7bd3e72d507a14f8f3863d"
                )
            )

            response.userRecommendSongs.add(
                SongSimple(
                    songArtist = "Red Hot Chili Peppers"
                    , songName = "Scar Tissue"
                    , spotifyId = "1G391cbiT3v3Cywg8T7DM1"
                    , albumJacket = "https://i.scdn.co/image/ab67616d00001e0294d08ab63e57b0cae74e8595"
                )
            )
        }
        
        // TODO: 히스토리의 순회하면서 해당 노래와 유사한 노래 추천
        return response
    }
}