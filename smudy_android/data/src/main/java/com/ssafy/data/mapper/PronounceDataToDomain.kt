package com.ssafy.data.mapper

import com.ssafy.data.model.music.pronounce.GradedPronounceResponse
import com.ssafy.data.model.music.pronounce.LyricAiAnalyzeResponse
import com.ssafy.data.model.music.pronounce.PronounceResponse
import com.ssafy.domain.model.study.pronounce.FormantsAvg
import com.ssafy.domain.model.study.pronounce.GradedPronounce
import com.ssafy.domain.model.study.pronounce.IntensityData
import com.ssafy.domain.model.study.pronounce.LyricAiAnalyze
import com.ssafy.domain.model.study.pronounce.PitchData
import com.ssafy.domain.model.study.pronounce.PronounceProblemInfo
import com.ssafy.domain.model.study.pronounce.Timestamp

fun PronounceResponse.toPronounceProblemInfo(): PronounceProblemInfo =
    PronounceProblemInfo(
        this.songId,
        this.songArtist,
        this.songName,
        this.albumJacket,
        this.lyrics.map { it.lyric }
    )

fun GradedPronounceResponse.toGradePronounce(): GradedPronounce =
    GradedPronounce(
        userPronounce,
        ttsPronounce,
        lyricSentenceEn,
        "",
        userLyricSTT,
        lyricAiAnalyze.toLyricAiAnalyze()
        )

fun LyricAiAnalyzeResponse.toLyricAiAnalyze(): LyricAiAnalyze =
    LyricAiAnalyze(
        FormantsAvg(refFormantsAvg.f1, refFormantsAvg.f2),
        IntensityData(refIntensityData.times, refIntensityData.values),
        PitchData(refPitchData.times, refPitchData.values),
        refTimestamps.map {
            Timestamp((it.startTime + it.endTime) / 2, it.endTime - it.startTime, it.word)
        },
        FormantsAvg(testFormantsAvg.f1, testFormantsAvg.f2),
        IntensityData(testIntensityData.times.map { data -> data - testIntensityData.times[0] },testIntensityData.values),
        PitchData(testPitchData.times.map { it - testPitchData.times[0] },testPitchData.values)
    )