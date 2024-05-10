package com.ssafy.userservice.service

import com.ssafy.userservice.db.postgre.entity.LearnReport
import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import org.apache.hc.core5.http.ParseException
import org.springframework.stereotype.Service
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException
import se.michaelthelin.spotify.model_objects.specification.Recommendations
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest
import java.io.IOException
import java.util.*


@Service
class SpotifyService(
    private val songService: SongService,
    private val learnReportRepository: LearnReportRepository,
    private val spotifyApi: SpotifyApi
) {

    fun getArtistsAndGenres(userInternalId: UUID):
            Pair<Set<String>, Set<String>> {

        // 유저의 학습기록 조회
        val userLearnReports: List<LearnReport> = learnReportRepository.findAllByUserInternalId(userInternalId = userInternalId)
        // 학습기록에서 songId 기준 장르 및 가수정보 추출
        val songs = songService.findSongArtistAndGenre(userLearnReports.map { it.songId })
        // spotifyId 기준으로 중복 제거
        val distinctSongs = songs.distinctBy { it.spotifyId }

        // artistId와 genre 각각 추출 후 중복 제거
        val uniqueArtistIds = distinctSongs.map { it.songArtistId }.toSet()
        val uniqueGenres = distinctSongs.map { it.songGenre }.toSet()

        // Pair로 묶어서 반환
        return uniqueArtistIds to uniqueGenres
    }

    fun getRecommendations(userInternalId: UUID) {

        val artistAndGenre: Pair<Set<String>, Set<String>> = getArtistsAndGenres(userInternalId)

        // Spotify API에 전달할 seed 문자열 생성
        val seedArtistsString = artistAndGenre.first.joinToString(separator = ",")
        val seedGenreString = artistAndGenre.second.joinToString(separator = ",")

        // Spotify Web API 추천 진행
        // https://github.com/spotify-web-api-java/spotify-web-api-java/blob/master/examples/data/browse/GetRecommendationsExample.java
        val request: GetRecommendationsRequest = spotifyApi.recommendations
          .limit(100)
          .seed_artists(seedArtistsString)
          .seed_genres(seedGenreString)
          .target_popularity(80)
          .build()

        try {
            val recommendations: Recommendations = request.execute()
            recommendations.tracks.forEach {
                println(it.toString())
            }

        } catch (e: IOException) {
            println("IOError: " + e.message)
        } catch (e: SpotifyWebApiException) {
            println("SpotifyError: " + e.message)
        } catch (e: ParseException) {
            println("ParseError: " + e.message)
        }
    }
}