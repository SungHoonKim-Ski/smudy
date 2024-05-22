package com.ssafy.searchservice.controller

import com.ssafy.searchservice.dto.Song
import com.ssafy.searchservice.dto.SongName
import com.ssafy.searchservice.dto.response.*
import com.ssafy.searchservice.entity.SongDocument
import com.ssafy.searchservice.service.SearchService
import com.ssafy.searchservice.util.SearchResponseService
import com.ssafy.searchservice.util.SingleResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.annotation.MergedAnnotations.Search
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.data.elasticsearch.core.query.StringQuery
import org.springframework.data.elasticsearch.core.search
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/search")
class SearchController (
    private val searchService: SearchService,
    private val responseService: SearchResponseService
){

    private val logger = KotlinLogging.logger{ }

    @GetMapping("/autocreate")
    fun autoCreate(
            @RequestParam(value = "query", required = true) query: String
    ): ResponseEntity<SingleResult<AutoCreateResponse>> {

        logger.debug { "autocreate: $query" }

        val result = searchService.searchAutoCreation(query)

        val response = AutoCreateResponse()
        for(song in result) {
            response.songNames.add(SongName(song.songName))
        }

        return if(result.size == 0) {
            // no content 는 본문을 포함하지 않음
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(
                responseService.getSuccessSingleResult(response, "자동완성 성공")
            )
        }
    }

    @GetMapping("")
    fun searchMusic(
            @RequestParam(value = "query", required = true) query: String,
            @RequestParam(value = "page", required = true) page: String
    ): ResponseEntity<SingleResult<SearchResponse>> {

        logger.debug { "/: $query $page" }

        val result = searchService.search(query, page.toInt())

        val response = SearchResponse()
        for(song in result) {
            response.songs.add(
                Song(
                    songArtist = song.songArtist,
                    songName = song.songName,
                    spotifyId = song.id,
                    albumJacket = song.albumJacket
                )
            )
        }

        return if(result.size == 0) {
            // no content 는 본문을 포함하지 않음
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(
                responseService.getSuccessSingleResult(response, "음악검색 성공")
            )
        }
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