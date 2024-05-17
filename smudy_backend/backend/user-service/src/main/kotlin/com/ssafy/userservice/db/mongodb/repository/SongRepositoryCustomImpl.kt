package com.ssafy.userservice.db.mongodb.repository

import com.ssafy.userservice.db.mongodb.entity.Song
import com.ssafy.userservice.dto.response.SongSimple
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.stereotype.Repository

@Repository
class SongRepositoryCustomImpl(
    private val mongoTemplate: MongoTemplate
) : SongRepositoryCustom {

    override fun findRandomSongs(size: Long): List<SongSimple> {
        
        // MongoDB의 $sample 연산을 사용하여 랜덤 문서 호출
        val sampleOperation = sample(size)
        val aggregation = newAggregation(sampleOperation)
        val result = mongoTemplate.aggregate(aggregation, "track_v4", Song::class.java)

        return result.map {
            song -> SongSimple(
                songName = song.songName,
                songArtist = song.songArtist,
                albumJacket = song.albumJacket,
                spotifyId = song.spotifyId
            )
        }
    }
}