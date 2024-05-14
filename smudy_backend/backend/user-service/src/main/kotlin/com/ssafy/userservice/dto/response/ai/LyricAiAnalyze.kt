package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class LyricAiAnalyze(

        @get: JsonProperty("ref_timestamps")
        val refTimestamps: List<Timestamp>,

        @get: JsonProperty("test_timestamps")
        val testTimestamps: List<Timestamp>,

        @get: JsonProperty("ref_full_text")
        val refFullText: String,

        @get: JsonProperty("test_full_text")
        val testFullText: String,

        @get: JsonProperty("ref_pitch_data")
        val refPitchData: PitchData,

        @get: JsonProperty("test_pitch_data")
        val testPitchData: PitchData,

        @get: JsonProperty("ref_intensity_data")
        val refIntensityData: IntensityData,

        @get: JsonProperty("test_intensity_data")
        val testIntensityData: IntensityData,

        @get: JsonProperty("ref_formants_avg")
        val refFormantsAvg: FormantsAvg,

        @get: JsonProperty("test_formants_avg")
        val testFormantsAvg: FormantsAvg
)
