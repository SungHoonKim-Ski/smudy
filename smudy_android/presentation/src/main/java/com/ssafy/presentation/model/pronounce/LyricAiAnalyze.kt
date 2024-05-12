package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LyricAiAnalyze(
    val refFormantsAvg: FormantsAvg,
    val refIntensityData: IntensityData,
    val refPitchData: PitchData,
    val refTimestamps: List<Timestamp>,
    val testFormantsAvg: FormantsAvg,
    val testIntensityData: IntensityData,
    val testPitchData: PitchData,
): Parcelable