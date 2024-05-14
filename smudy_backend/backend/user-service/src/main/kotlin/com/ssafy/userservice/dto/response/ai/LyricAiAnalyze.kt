package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty
import com.ssafy.userservice.db.postgre.entity.ai.*

data class LyricAiAnalyze(

        @get: JsonProperty("ref_timestamps")
        val ttsTimestamps: List<Timestamp>,

        @get: JsonProperty("test_timestamps")
        val userTimestamps: List<Timestamp>,

        @get: JsonProperty("ref_full_text")
        val ttsFullText: String,

        @get: JsonProperty("test_full_text")
        val userFullText: String,

        @get: JsonProperty("ref_pitch_data")
        val ttsPitchData: PitchData,

        @get: JsonProperty("test_pitch_data")
        val userPitchData: PitchData,

        @get: JsonProperty("ref_intensity_data")
        val ttsIntensityData: IntensityData,

        @get: JsonProperty("test_intensity_data")
        val userIntensityData: IntensityData,

        @get: JsonProperty("ref_formants_avg")
        val ttsFormantsAvg: FormantsAvg,

        @get: JsonProperty("test_formants_avg")
        val userFormantsAvg: FormantsAvg
) {
        fun parseEntity(): EntityLyricAiAnalyze {
                return EntityLyricAiAnalyze(
                        ttsTimestamps = this.ttsTimestamps
                                .map {
                                        EntityTimestamp(
                                                word = it.word,
                                                startTime = it.start_time,
                                                endTime = it.end_time
                                        )
                                     },
                        userTimestamps = this.userTimestamps
                                .map {
                                        EntityTimestamp(
                                                word = it.word,
                                                startTime = it.start_time,
                                                endTime = it.end_time
                                        )
                                     },
                        ttsFullText = this.ttsFullText,
                        userFullText = this.userFullText,
                        ttsPitchData = this.ttsPitchData
                                .let {
                                        EntityPitchData(
                                                times = it.times,
                                                values = it.values
                                        )
                                     },
                        userPitchData = this.userPitchData
                                .let {
                                        EntityPitchData(
                                                times = it.times,
                                                values = it.values
                                        )
                                     },
                        ttsIntensityData = this.ttsIntensityData
                                .let {
                                     EntityIntensityData(
                                             times = it.times,
                                             values = it.values
                                     )
                                },
                        userIntensityData = this.userIntensityData
                                .let {
                                        EntityIntensityData(
                                                times = it.times,
                                                values = it.values
                                        )
                                },
                        ttsFormantsAvg= this.ttsFormantsAvg
                                .let {
                                     EntityFormantsAvg(
                                             f1 = it.f1,
                                             f2 = it.f2
                                     )
                                },
                        userFormantsAvg = this.userFormantsAvg
                                .let {
                                        EntityFormantsAvg(
                                                f1 = it.f1,
                                                f2 = it.f2
                                        )
                                },
                )
        }
}
