package com.ssafy.presentation.model.pronounce

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LyricAiAnalyze(
    val refFormantsAvg: FormantsAvg,
    val refIntensityData: List<IntensityData>,
    val refPitchData: List<PitchData>,
    val refTimestamps: List<Timestamp>,
    val testFormantsAvg: FormantsAvg,
    val testIntensityData: List<IntensityData>,
    val testPitchData: List<PitchData>,
): Parcelable