package com.ssafy.userservice.service

import com.ssafy.userservice.db.mongodb.repository.SongRepository
import com.ssafy.userservice.db.postgre.entity.User
import com.ssafy.userservice.db.postgre.repository.LearnReportRepository
import com.ssafy.userservice.db.postgre.repository.UserRepository
import com.ssafy.userservice.dto.request.SignUpRequest
import com.ssafy.userservice.dto.response.InfoResponse
import com.ssafy.userservice.dto.response.SongSimple
import com.ssafy.userservice.exception.exception.UserNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository,
        private val learnReportRepository: LearnReportRepository,
        private val songRepository: SongRepository,
) {

    private val logger = KotlinLogging.logger{ }
    @Transactional
    fun signup(signUpRequest: SignUpRequest) : String{

        userRepository.findByUserSnsId(signUpRequest.userSnsId) ?: return ""

        val saveUser = userRepository
                .save(
                    User(
                            userId = -1,
                            userExp = 0,
                            userName = signUpRequest.userSnsName,
                            userImage = signUpRequest.userImage,
                            userInternalId = UUID.randomUUID(),
                            userSnsId = signUpRequest.userSnsId,
                            createdAt = Date()
                    )
                )

        saveUser.userId

        if (saveUser.userId == -1) return ""

        return saveUser.userInternalId.toString()
    }

    @Transactional
    fun getUserInfo(userInternalId: UUID) : InfoResponse {

        val user = userRepository.findByUserInternalId(userInternalId)
        user ?: throw UserNotFoundException("존재하지 않는 유저")

        logger.info { "user : $user" }
        logger.info { "UUID : $userInternalId" }

        val userLeanReportSongIds = learnReportRepository
                .findTop4ByUserInternalId(user.userInternalId)
                .map {
                    learnReport -> learnReport.songId
                }

        logger.info { "userLeanReportSongIds : $userLeanReportSongIds" }

        val userLearnReportSongs = songRepository
                .findAllBySpotifyIdIn(userLeanReportSongIds).map {
                    song -> SongSimple(
                            songName = song.songName,
                            songArtist = song.songArtist,
                            albumJacket = song.albumJacket,
                            spotifyId = song.spotifyId
                    )
                }

        return InfoResponse(
                userName = user.userName,
                userImage = user.userImage,
                userExp = user.userExp,
                userStudyHistory = userLearnReportSongs.toMutableList()
        )

    }


//    fun pickProblems(): {
//
//    }

}