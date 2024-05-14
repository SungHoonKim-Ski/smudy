package com.ssafy.userservice.db.postgre.entity.ai

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable

@Embeddable
data class EntityLyricAiAnalyze(

        @ElementCollection
        val ttsTimestamps: List<EntityTimestamp>,

        @ElementCollection
        val userTimestamps: List<EntityTimestamp>,

        val ttsFullText: String,

        val userFullText: String,

        val ttsPitchData: EntityPitchData,

        val userPitchData: EntityPitchData,

        val ttsIntensityData: EntityIntensityData,

        val userIntensityData: EntityIntensityData,

        val ttsFormantsAvg: EntityFormantsAvg,

        val userFormantsAvg: EntityFormantsAvg
)
