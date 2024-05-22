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
        lyricSentenceEn,
        lyricSentenceKo,
        userLyricSttEn,
        lyricAiAnalyze.toLyricAiAnalyze()
        )

fun LyricAiAnalyzeResponse.toLyricAiAnalyze(): LyricAiAnalyze =
    LyricAiAnalyze(
        FormantsAvg(refFormantsAvg.f1, refFormantsAvg.f2),
        refIntensityData.map { IntensityData(it.times,it.values) },
        refPitchData.map { PitchData(it.times,it.values) },
        refTimestamps.map {
            Timestamp(it.word,it.startTime,it.endTime)
        },
        FormantsAvg(testFormantsAvg.f1, testFormantsAvg.f2),
        testIntensityData.map { IntensityData(it.times,it.values) },
        testPitchData.map { PitchData(it.times,it.values) }
    )