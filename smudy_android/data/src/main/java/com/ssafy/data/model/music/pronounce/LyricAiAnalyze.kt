package com.ssafy.data.model.music.pronounce

import com.squareup.moshi.Json

@Json
data class LyricAiAnalyze(
    @Json(name = "ref_formants_avg")
    val refFormantsAvg: FormantsAvgResponse,
    @Json(name = "ref_intensity_data")
    val refIntensityData: IntensityDataResponse,
    @Json(name = "ref_pitch_data")
    val refPitchData: PitchDataResponse,
    @Json(name = "ref_timestamps")
    val refTimestamps: List<TimestampResponse>,
    @Json(name = "test_formants_avg")
    val testFormantsAvg: FormantsAvgResponse,
    @Json(name = "test_intensity_data")
    val testIntensityData: IntensityDataResponse,
    @Json(name = "test_pitch_data")
    val testPitchData: PitchDataResponse,
    @Json(name = "test_timestamps")
    val testTimestamps: List<TimestampResponse>
)