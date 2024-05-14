package com.ssafy.userservice.util

import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze

fun LyricAiAnalyze.toAnotherAnalyze(): EntityLyricAiAnalyze {
    return EntityLyricAiAnalyze(
            ttsTimestamps = this.ttsTimestamps,
            testTimestamps = this.testTimestamps,
            refFullText = this.refFullText,
            testFullText = this.testFullText,
            refPitchData = this.refPitchData,
            testPitchData = this.testPitchData,
            refIntensityData = this.refIntensityData,
            testIntensityData = this.testIntensityData,
            refFormantsAvg = this.refFormantsAvg,
            testFormantsAvg = this.testFormantsAvg
    )
}

fun EntityLyricAiAnalyze.toLyricAiAnalyze(): LyricAiAnalyze {
    return LyricAiAnalyze(
            refTimestamps = this.refTimestamps,
            testTimestamps = this.testTimestamps,
            refFullText = this.refFullText,
            testFullText = this.testFullText,
            refPitchData = this.refPitchData,
            testPitchData = this.testPitchData,
            refIntensityData = this.refIntensityData,
            testIntensityData = this.testIntensityData,
            refFormantsAvg = this.refFormantsAvg,
            testFormantsAvg = this.testFormantsAvg
    )
}
