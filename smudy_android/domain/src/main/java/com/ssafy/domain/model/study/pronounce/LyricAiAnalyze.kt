package com.ssafy.domain.model.study.pronounce


data class LyricAiAnalyze(
    val refFormantsAvg: FormantsAvg,
    val refIntensityData: List<IntensityData>,
    val refPitchData: List<PitchData>,
    val refTimestamps: List<Timestamp>,
    val testFormantsAvg: FormantsAvg,
    val testIntensityData: List<IntensityData>,
    val testPitchData: List<PitchData>,
)