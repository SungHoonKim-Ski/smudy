package com.ssafy.domain.model.study.pronounce


data class LyricAiAnalyze(
    val refFormantsAvg: FormantsAvg,
    val refIntensityData: IntensityData,
    val refPitchData: PitchData,
    val refTimestamps: List<Timestamp>,
    val testFormantsAvg: FormantsAvg,
    val testIntensityData: IntensityData,
    val testPitchData: PitchData,
)