package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

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
        val ttsPitchData: List<PitchData>,

        @get: JsonProperty("test_pitch_data")
        val userPitchData: List<PitchData>,

        @get: JsonProperty("ref_intensity_data")
        val ttsIntensityData: List<IntensityData>,

        @get: JsonProperty("test_intensity_data")
        val userIntensityData: List<IntensityData>,

        @get: JsonProperty("ref_formants_avg")
        val ttsFormantsAvg: FormantsAvg,

        @get: JsonProperty("test_formants_avg")
        val userFormantsAvg: FormantsAvg
)