package com.ssafy.backend_dummy.search_service.controller

import com.ssafy.backend_dummy.search_service.dto.response.*
import com.ssafy.backend_dummy.search_service.util.SearchResponseService
import com.ssafy.backend_dummy.search_service.util.SingleResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class SearchController (
        private val responseService: SearchResponseService
){
    private val logger = KotlinLogging.logger{ }
    @GetMapping("/test")
    fun test(): ResponseEntity<SingleResult<String>> {
        var data = "search"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }
    @GetMapping("/autocreate")
    fun autoCreate(
            @RequestParam(value = "query", required = true) query: String
    ): ResponseEntity<SingleResult<AutoCreateResponse>> {

        logger.debug { "autocreate: $query" }
        val response = AutoCreateResponse()

        response.songNames.add(SongName("I'm Yours"))
        response.songNames.add(SongName("93 Million Miles"))
        response.songNames.add(SongName("I Won't Give Up"))
        response.songNames.add(SongName("Absolutely Zero"))
        response.songNames.add(SongName("The Boy's Gone"))
        response.songNames.add(SongName("You And I"))
        response.songNames.add(SongName("Everybody"))
        response.songNames.add(SongName("Somebody Told Me"))
        response.songNames.add(SongName("Creep"))

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(response, "자동완성 성공")
        )
    }

    @GetMapping("")
    fun searchMusic(
            @RequestParam(value = "query", required = true) query: String
            , @RequestParam(value = "page", required = true) page: String
    ): ResponseEntity<SingleResult<SearchResponse>> {

        logger.debug { "/: $query $page" }
        val response = SearchResponse()

        response.songs.add(
                Song(
                        songArtist = "Ingrid Michaelson"
                        , songName = "You And I"
                        , spotifyId = "7aohwSiTDju51QmC54AUba"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e022f9b47bdc2b1c7cae7b014af"
                )
        )

        response.songs.add(
                Song(
                        songArtist = "Ingrid Michaelson"
                        , songName = "Everybody"
                        , spotifyId = "3SBzDgdTwHOMSik82ZI6L2"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e0279f6239caf413738d82d3b0f"
                )
        )

        response.songs.add(
                Song(
                        songArtist = "Weezer"
                        , songName = "Say It Ain't So"
                        , spotifyId = "3qmncUJvABe0QDRwMZhbPt"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e023be73241fed381886709f761"
                )
        )



        response.songs.add(
                Song(
                        songArtist = "Weezer"
                        , songName = "My Name Is Jonas"
                        , spotifyId = "08k0JhCj8oJLB7Xuclr57s"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e02f6e2ddb1cba9a5900de38cd1"
                )
        )

        response.songs.add(
                Song(
                        songArtist = "Megadeth"
                        , songName = "Symphony Of Destruction"
                        , spotifyId = "1955ZZJe1TzmSR0TomnNjI"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e027d7bd3e72d507a14f8f3863d"
                )
        )

        response.songs.add(
                Song(
                        songArtist = "Red Hot Chili Peppers"
                        , songName = "Scar Tissue"
                        , spotifyId = "1G391cbiT3v3Cywg8T7DM1"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e0294d08ab63e57b0cae74e8595"
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        response
                        ,"음악 검색 성공"
                )
        )
    }

    @GetMapping("/recommend")
    fun recommendMusic(
            @RequestHeader("Authorization") accessToken: String
    ) : ResponseEntity<SingleResult<RecommendResponse>>{
        logger.debug { "/recommend $accessToken" }

        val response = RecommendResponse()

        response.userRecommendSongs.add(
                Song(
                        songArtist = "Ingrid Michaelson"
                        , songName = "You And I"
                        , spotifyId = "7aohwSiTDju51QmC54AUba"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e022f9b47bdc2b1c7cae7b014af"
                )
        )

        response.userRecommendSongs.add(
                Song(
                        songArtist = "Ingrid Michaelson"
                        , songName = "Everybody"
                        , spotifyId = "3SBzDgdTwHOMSik82ZI6L2"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e0279f6239caf413738d82d3b0f"
                )
        )

        response.userRecommendSongs.add(
                Song(
                        songArtist = "Weezer"
                        , songName = "Say It Ain't So"
                        , spotifyId = "3qmncUJvABe0QDRwMZhbPt"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e023be73241fed381886709f761"
                )
        )



        response.userRecommendSongs.add(
                Song(
                        songArtist = "Weezer"
                        , songName = "My Name Is Jonas"
                        , spotifyId = "08k0JhCj8oJLB7Xuclr57s"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e02f6e2ddb1cba9a5900de38cd1"
                )
        )

        response.userRecommendSongs.add(
                Song(
                        songArtist = "Megadeth"
                        , songName = "Symphony Of Destruction"
                        , spotifyId = "1955ZZJe1TzmSR0TomnNjI"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e027d7bd3e72d507a14f8f3863d"
                )
        )

        response.userRecommendSongs.add(
                Song(
                        songArtist = "Red Hot Chili Peppers"
                        , songName = "Scar Tissue"
                        , spotifyId = "1G391cbiT3v3Cywg8T7DM1"
                        , albumJacket = "https://i.scdn.co/image/ab67616d00001e0294d08ab63e57b0cae74e8595"
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        response
                        ,"음악 추천 성공"
                )
        )
    }
}