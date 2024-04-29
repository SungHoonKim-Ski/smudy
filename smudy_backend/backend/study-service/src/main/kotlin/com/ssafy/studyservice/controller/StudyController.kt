package com.ssafy.studyservice.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ssafy.studyservice.dto.response.ExpressResponse
import com.ssafy.studyservice.dto.request.*
import com.ssafy.studyservice.dto.response.ai.LyricAiAnalyze
import com.ssafy.studyservice.dto.response.*
import com.ssafy.studyservice.util.CommonResult
import com.ssafy.studyservice.util.SingleResult
import com.ssafy.studyservice.util.StudyResponseService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/study")
class StudyController (
        private val responseService: StudyResponseService
){
    private val logger = KotlinLogging.logger{ }
    @GetMapping("/test")
    fun signup(): ResponseEntity<SingleResult<String>> {
        var data = "study"
        logger.debug { "Hello! $data" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult("데이터","성공"))
    }

    @GetMapping("/daily")
    fun dailyLyric(): ResponseEntity<SingleResult<DailyLyricResponse>> {
        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(
                        DailyLyricResponse(
                                dailyLyricsEn = "Are you kidding me?",
                                dailyLyricsKo = "마 장난치나!"
                        )
                        ,"오늘의 한 문장 조회 성공")
        )
    }

    @GetMapping("/fill/{songId}")
    fun getFillQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<FillResponse>> {

        val response = FillResponse(
                songId = "1EzrEOXmMH3G43AXT1y7pA",
                songArtist = "Jason Mraz",
                songName = "I'm Yours",
                albumJacket = "https://i.scdn.co/image/ab67616d00001e02125b1a330b6f6100ab19dbed",
                songDuration = "04:02",
                lyricEnd = "03:56"
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:12",
                        lyricBlank = "____, you done, done me in, you bet I felt it",
                        lyricBlankAnswer = "well"
//            //            "lyric_sentence_en": "Well, you done, done me in, you bet I felt it",
                )
        )


        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:16",
                        lyricBlank = "I tried to be chill, but you're so hot that I ______",
                        lyricBlankAnswer = "melted"
//            //            "lyric_sentence_en": "I tried to be chill, but you're so hot that I melted",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:19",
                        lyricBlank = "I fell right through the ______",
                        lyricBlankAnswer = "cracks"
//            //            "lyric_sentence_en": "I fell right through the cracks",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:22",
                        lyricBlank = "Now I'm ______ to get back",
                        lyricBlankAnswer = "trying"
//            //            "lyric_sentence_en": "Now I'm trying to get back",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:25",
                        lyricBlank = "Before the cool done run out, I'll be givin' it my _______",
                        lyricBlankAnswer = "bestest"
//            //            "lyric_sentence_en": "Before the cool done run out, I'll be givin' it my bestest",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:29",
                        lyricBlank = "And nothing's gonna stop me but divine ____________",
                        lyricBlankAnswer = "intervention"
//            //            "lyric_sentence_en": "And nothing's gonna stop me but divine intervention",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:32",
                        lyricBlank = "I ______ it's again my turn",
                        lyricBlankAnswer = "reckon"
//            //            "lyric_sentence_en": "I reckon it's again my turn",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:35",
                        lyricBlank = "To win some or _____ some",
                        lyricBlankAnswer = "learn"
//            //            "lyric_sentence_en": "To win some or learn some",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:38",
                        lyricBlank = "But I won't ________ no more, no more",
                        lyricBlankAnswer = "hesitate"
//            //            "lyric_sentence_en": "But I won't hesitate no more, no more",

                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:45",
                        lyricBlank = "It cannot ____, I'm yours",
                        lyricBlankAnswer = "wait"
//            //            "lyric_sentence_en": "It cannot wait, I'm yours",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:54",
                        lyricBlank = "♪",
                        lyricBlankAnswer = ""
//            //            "lyric_sentence_en": "♪",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "00:56",
                        lyricBlank = "♪",
                        lyricBlankAnswer = "",
//            //            "lyric_sentence_en": "♪",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:04",
                        lyricBlank = "____, open up your mind and see like me",
                        lyricBlankAnswer = "well"
//            //            "lyric_sentence_en": "Well, open up your mind and see like me",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:08",
                        lyricBlank = "Open up your _____ and, damn, you're free",
                        lyricBlankAnswer = "plans"
//            //            "lyric_sentence_en": "Open up your plans and, damn, you're free",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:11",
                        lyricBlank = "Look into your _____, and you'll find love, love, love, love",
                        lyricBlankAnswer = "heart"
//            "lyric_sentence_en": "Look into your heart, and you'll find love, love, love, love",
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:17",
//            "lyric_sentence_en": "Listen to the music of the moment people dance and sing",
                        lyricBlank = "______ to the music of the moment people dance and sing",
                        lyricBlankAnswer = "listen"
                )
        )


        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:21",
//            "lyric_sentence_en": "We're just one big family",
                        lyricBlank = "We're just one big ______",
                        lyricBlankAnswer = "family"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:23",
//            "lyric_sentence_en": "And it's our godforsaken right to be loved, loved, loved, loved, loved",
                        lyricBlank = "And it's our ___________ right to be loved, loved, loved, loved, loved",
                        lyricBlankAnswer = "godforsaken"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:32",
//            "lyric_sentence_en": "So I won't hesitate no more, no more",
                        lyricBlank = "So I won't ________ no more, no more",
                        lyricBlankAnswer = "hesitate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:39",
//            "lyric_sentence_en": "It cannot wait, I'm sure",
                        lyricBlank = "It cannot ____, I'm sure",
                        lyricBlankAnswer = "wait"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:44",
//            "lyric_sentence_en": "There's no need to complicate, our time is short",
                        lyricBlank = "There's no need to __________, our time is short",
                        lyricBlankAnswer = "complicate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:52",
//            "lyric_sentence_en": "This is our fate, I'm yours",
                        lyricBlank = "This is our ____, I'm yours",
                        lyricBlankAnswer = "fate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:58",
//            "lyric_sentence_en": "Do, do, do, do you"
                        lyricBlank = "Do, do, do, do you",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "01:59",
//            "lyric_sentence_en": "But do you, do you, do, do"
                        lyricBlank = "But do you, do you, do, do",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:01",
//            "lyric_sentence_en": "But do you want to come on?",
                        lyricBlank = "But do you ____ to come on?",
                        lyricBlankAnswer = "want"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:03",
//            "lyric_sentence_en": "Scooch on over closer, dear",
                        lyricBlank = "______ on over closer, dear",
                        lyricBlankAnswer = "scooch"
                )
        )



        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:06",
//            "lyric_sentence_en": "And I will nibble your ear",
                        lyricBlank = "And I will ______ your ear",
                        lyricBlankAnswer = "nibble"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:11",
//            "lyric_sentence_en": "♪"
                        lyricBlank = "♪",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:23",
//            "lyric_sentence_en": "I've been spending way too long checking my tongue in the mirror",
                        lyricBlank = "I've been ________ way too long checking my tongue in the mirror",
                        lyricBlankAnswer = "spending"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:26",
//            "lyric_sentence_en": "And bending over backwards just to try to see it clearer",
                        lyricBlank = "And bending over _________ just to try to see it clearer",
                        lyricBlankAnswer = "backwards"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:29",
//            "lyric_sentence_en": "But my breath fogged up the glass",
                        lyricBlank = "But my ______ fogged up the glass",
                        lyricBlankAnswer = "breath"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:32",
//            "lyric_sentence_en": "And so I drew a new face and I laughed",
                        lyricBlank = "And so I drew a new face and I _______",
                        lyricBlankAnswer = "laughed"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:36",
//            "lyric_sentence_en": "I guess what I'll be saying is there ain't no better reason",
                        lyricBlank = "I guess what I'll be ______ is there ain't no better reason",
                        lyricBlankAnswer = "saying"
                )
        )


        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:39",
//            "lyric_sentence_en": "To rid yourself of vanities and just go with the seasons",
                        lyricBlank = "To rid yourself of ________ and just go with the seasons",
                        lyricBlankAnswer = "vanities"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:42",
//            "lyric_sentence_en": "It's what we aim to do"
                        lyricBlank =  "It's what we aim to do",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:45",
//            "lyric_sentence_en": "Our name is our virtue",
                        lyricBlank = "Our name is our ______",
                        lyricBlankAnswer = "virtue"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:48",
//            "lyric_sentence_en": "But I won't hesitate no more, no more",
                        lyricBlank = "But I won't ________ no more, no more",
                        lyricBlankAnswer = "hesitate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "02:56",
//            "lyric_sentence_en": "It cannot wait, I'm yours",
                        lyricBlank = "It cannot ____, I'm yours",
                        lyricBlankAnswer = "wait"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:02",
//            "lyric_sentence_en": "Open up your mind and see like me",
                        lyricBlank = "____ up your mind and see like me",
                        lyricBlankAnswer = "open"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:05",
//            "lyric_sentence_en": "Open up your plans and, damn, you're free",
                        lyricBlank = "Open up your _____ and, damn, you're free",
                        lyricBlankAnswer = "plans"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:08",
//            "lyric_sentence_en": "Look into your heart, and you'll find that the sky is yours",
                        lyricBlank = "Look into your _____, and you'll find that the sky is yours",
                        lyricBlankAnswer = "heart"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:13",
//            "lyric_sentence_en": "So please don't, please don't, please don't",
                        lyricBlank = "So please don't, ______ don't, please don't",
                        lyricBlankAnswer = "please"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:17",
//            "lyric_sentence_en": "There's no need to complicate",
                        lyricBlank = "There's no need to __________",
                        lyricBlankAnswer = "complicate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:19",
//            "lyric_sentence_en": "'Cause our time is short",
                        lyricBlank = "'Cause our time is _____",
                        lyricBlankAnswer = "short"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:22",
//            "lyric_sentence_en": "This, oh, this, oh, this is our fate",
                        lyricBlank = "This, oh, this, oh, this is our ____",
                        lyricBlankAnswer = "fate"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:24",
//            "lyric_sentence_en": "I'm yours"
                        lyricBlank = "I'm yours",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:33",
//            "lyric_sentence_en": "♪"
                        lyricBlank = "♪",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:41",
//            "lyric_sentence_en": "Oh, I'm yours"
                        lyricBlank = "Oh, I'm yours",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:44",
//            "lyric_sentence_en": "Oh, I'm yours"
                        lyricBlank = "Oh, I'm yours",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:47",
//            "lyric_sentence_en": "Oh, whoa-oh"
                        lyricBlank = "Oh, whoa-oh",
                        lyricBlankAnswer = ""
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:51",
//            "lyric_sentence_en": "Baby, do believe I'm yours",
                        lyricBlank = "Baby, do _______ I'm yours",
                        lyricBlankAnswer = "believe"
                )
        )

        response.lyricsBlank.add(
                LyricBlank(
                        lyricStartTimeStamp = "03:53",
//            "lyric_sentence_en": "You best believe, you best believe I'm yours",
                        lyricBlank = "You best _______, you best believe I'm yours",
                        lyricBlankAnswer = "believe"
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(response,"Fill 문제 목록 조회 성공")
        )
    }

    @PostMapping("/fill/submit")
    fun submitFill(@RequestBody request: SubmitFillRequest): ResponseEntity<SingleResult<SubmitFillResponse>> {

        logger.debug { "/submit/fill $request" }
        val response = SubmitFillResponse(
                totalSize = 52,
                score = 48,
        )

        response.result.add(
                FillResult(
                        lyricBlank = "____, you done, done me in, you bet I felt it",
                        originWord = "well",
                        userWord = "well",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I tried to be chill, but you're so hot that I ______",
                        originWord = "melted",
                        userWord = "melted",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I fell right through the ______",
                        originWord = "cracks",
                        userWord = "cracks",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Now I'm ______ to get back",
                        originWord = "trying",
                        userWord = "try",
                        isCorrect = false
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Before the cool done run out, I'll be givin' it my _______",
                        originWord = "bestest",
                        userWord = "bestest",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "And nothing's gonna stop me but divine ____________",
                        originWord = "intervention",
                        userWord = "intervention",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I ______ it's again my turn",
                        originWord = "reckon",
                        userWord = "reckon",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "To win some or _____ some",
                        originWord = "learn",
                        userWord = "learn",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "But I won't ________ no more, no more",
                        originWord = "hesitate",
                        userWord = "hesitate",
                        isCorrect = true

                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "It cannot ____, I'm yours",
                        originWord = "wait",
                        userWord = "wait",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "♪",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "♪",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "____, open up your mind and see like me",
                        originWord = "well",
                        userWord = "well",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Open up your _____ and, damn, you're free",
                        originWord = "plans",
                        userWord = "plans",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Look into your _____, and you'll find love, love, love, love",
                        originWord = "heart",
                        userWord = "heart",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "______ to the music of the moment people dance and sing",
                        originWord = "listen",
                        userWord = "listen",
                        isCorrect = true
                )
        )


        response.result.add(
                FillResult(
                        lyricBlank = "We're just one big ______",
                        originWord = "family",
                        userWord = "family",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "And it's our ___________ right to be loved, loved, loved, loved, loved",
                        originWord = "godforsaken",
                        userWord = "godforsaken",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "So I won't ________ no more, no more",
                        originWord = "hesitate",
                        userWord = "hesitate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "It cannot ____, I'm sure",
                        originWord = "wait",
                        userWord = "wait",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "There's no need to __________, our time is short",
                        originWord = "complicate",
                        userWord = "complicate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "This is our ____, I'm yours",
                        originWord = "fate",
                        userWord = "fate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Do, do, do, do you",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "But do you, do you, do, do",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "But do you ____ to come on?",
                        originWord = "want",
                        userWord = "want",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "______ on over closer, dear",
                        originWord = "scooch",
                        userWord = "scooch",
                        isCorrect = true
                )
        )



        response.result.add(
                FillResult(
                        lyricBlank = "And I will ______ your ear",
                        originWord = "nibble",
                        userWord = "nibble",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "♪",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I've been ________ way too long checking my tongue in the mirror",
                        originWord = "spending",
                        userWord = "spent",
                        isCorrect = false
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "And bending over _________ just to try to see it clearer",
                        originWord = "backwards",
                        userWord = "backward",
                        isCorrect = false
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "But my ______ fogged up the glass",
                        originWord = "breath",
                        userWord = "breath",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "And so I drew a new face and I _______",
                        originWord = "laughed",
                        userWord = "laughed",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I guess what I'll be ______ is there ain't no better reason",
                        originWord = "saying",
                        userWord = "saying",
                        isCorrect = true
                )
        )


        response.result.add(
                FillResult(
                        lyricBlank = "To rid yourself of ________ and just go with the seasons",
                        originWord = "vanities",
                        userWord = "vanities",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank =  "It's what we aim to do",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Our name is our ______",
                        originWord = "virtue",
                        userWord = "virtue",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "But I won't ________ no more, no more",
                        originWord = "hesitate",
                        userWord = "hesitate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "It cannot ____, I'm yours",
                        originWord = "wait",
                        userWord = "wait",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "____ up your mind and see like me",
                        originWord = "open",
                        userWord = "open",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Open up your _____ and, damn, you're free",
                        originWord = "plans",
                        userWord = "plans",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Look into your _____, and you'll find that the sky is yours",
                        originWord = "heart",
                        userWord = "heart",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "So please don't, ______ don't, please don't",
                        originWord = "please",
                        userWord = "please",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "There's no need to __________",
                        originWord = "complicate",
                        userWord = "complicate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "'Cause our time is _____",
                        originWord = "short",
                        userWord = "long",
                        isCorrect = false
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "This, oh, this, oh, this is our ____",
                        originWord = "fate",
                        userWord = "fate",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "I'm yours",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "♪",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Oh, I'm yours",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Oh, I'm yours",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Oh, whoa-oh",
                        originWord = "",
                        userWord = "",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "Baby, do _______ I'm yours",
                        originWord = "believe",
                        userWord = "believe",
                        isCorrect = true
                )
        )

        response.result.add(
                FillResult(
                        lyricBlank = "You best _______, you best believe I'm yours",
                        originWord = "believe",
                        userWord = "believe",
                        isCorrect = true
                )
        )

        return ResponseEntity.ok(responseService.getSuccessSingleResult(response,"Fill 제출 완료"))
    }

    @GetMapping("/pick/{songId}")
    fun getPickQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<PickResponse>>  {

        val response = PickResponse(
                problemBoxId = 10,
                songId = "1EzrEOXmMH3G43AXT1y7pA",
                songArtist = "Jason Mraz",
                songName = "I'm Yours",
                albumJacket = "https://i.scdn.co/image/ab67616d00001e02125b1a330b6f6100ab19dbed",
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Well, you done, done me in, you bet I felt it",
                        lyricSentenceKo = "당신은 나를 사로잡았고 내가 그렇다는 걸 알겠죠"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "I tried to be chill, but you're so hot that I melted",
                        lyricSentenceKo = "난 냉정해지려고 했는데, 당신이 너무 멋있어서 녹아버렸어요"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "I fell right through the cracks",
                        lyricSentenceKo = "난 좁은 틈 사이로 빠져버렸고"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Now I'm trying to get back",
                        lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Before the cool done run out, I'll be givin' it my bestest",
                        lyricSentenceKo = "이 기분이 사라지기 전에 내 최고의 것을 드릴께요"
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(response,"Pick 문제 목록 조회 성공")
        )
    }

    @PostMapping("/pick/submit")
    fun submitPick(@RequestBody request: SubmitPickRequest): ResponseEntity<SingleResult<SubmitPickResponse>> {
        logger.debug { "/pick/submit $request" }
        val response = SubmitPickResponse(score = 3)

        response.correct.add(
                Problem(
                        lyricSentenceEn = "Well, you done, done me in, you bet I felt it",
                        lyricSentenceKo = "당신은 나를 사로잡았고 내가 그렇다는 걸 알겠죠"
                )
        )

        response.wrong.add(
                WrongProblem(
                        lyricSentenceEn = "I tried to be chill, but you're so hot that I melted",
                        lyricSentenceKo = "난 냉정해지려고 했는데, 당신이 너무 멋있어서 녹아버렸어요",
                        userLyricSentence = "I to tried be but chill, you're that so hot I melted"
                )
        )

        response.correct.add(
                Problem(
                        lyricSentenceEn = "I fell right through the cracks",
                        lyricSentenceKo = "난 좁은 틈 사이로 빠져버렸고"
                )
        )

        response.wrong.add(
                WrongProblem(
                        lyricSentenceEn = "Now I'm trying to get back",
                        lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요",
                        userLyricSentence = "I'm trying Now go to back"
                )
        )

        response.correct.add(
                Problem(
                        lyricSentenceEn = "Before the cool done run out, I'll be givin' it my bestest",
                        lyricSentenceKo = "이 기분이 사라지기 전에 내 최고의 것을 드릴께요"
                )
        )
        return ResponseEntity.ok(responseService.getSuccessSingleResult(response,"Pick 제출 완료"))
    }

    @GetMapping("/express/{songId}")
    fun getExpressQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<ExpressResponse>> {

        logger.debug { "/express/$songId" }

        val response = ExpressResponse(
                problemBoxId = 10,
                songId = "1EzrEOXmMH3G43AXT1y7pA",
                songArtist = "Jason Mraz",
                songName = "I'm Yours",
                albumJacket = "https://i.scdn.co/image/ab67616d00001e02125b1a330b6f6100ab19dbed",
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Well, you done, done me in, you bet I felt it",
                        lyricSentenceKo = "당신은 나를 사로잡았고 내가 그렇다는 걸 알겠죠"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "I tried to be chill, but you're so hot that I melted",
                        lyricSentenceKo = "난 냉정해지려고 했는데, 당신이 너무 멋있어서 녹아버렸어요"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "I fell right through the cracks",
                        lyricSentenceKo = "난 좁은 틈 사이로 빠져버렸고"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Now I'm trying to get back",
                        lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요"
                )
        )

        response.problems.add(
                Problem(
                        lyricSentenceEn = "Before the cool done run out, I'll be givin' it my bestest",
                        lyricSentenceKo = "이 기분이 사라지기 전에 내 최고의 것을 드릴께요"
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(response,"Express 문제 목록 조회 성공")
        )
    }

    /**
     * 1문제 채점하는 함수
     */
    @PostMapping("/express/check")
    fun checkExpress(@RequestBody request: ExpressCheckRequest): ResponseEntity<SingleResult<ExpressCheckResponse>> {
        logger.debug { "/express/check $request" }
        val response = ExpressCheckResponse(
                lyricSentenceEn = "Now I'm trying to get back",
                lyricSentenceKo = "지금은 다시 돌아가려 애쓰고 있어요",
                userLyricSentenceEn =  "I  plan to try to return in the future.",
                userLyricSentenceKo = "나는 미래에 돌아가려고 노력할 예정이에요",
                suggestLyricSentence = "I am trying to go back now." ,
                score = 90
        )
        return ResponseEntity.ok(responseService.getSuccessSingleResult(response, "Express 문제 채점 성공"))
    }

    @PostMapping("/express/submit")
    fun submitExpress(@RequestBody request: SubmitExpressRequest): ResponseEntity<CommonResult> {
        logger.debug { "/express/submit $request" }
        return ResponseEntity.ok(responseService.getSuccessMessageResult("Express 제출 완료"))
    }


    @GetMapping("/pronounce/{songId}")
    fun getPronounceQuiz(@PathVariable("songId") songId: String): ResponseEntity<SingleResult<PronounceResponse>> {

        val response = PronounceResponse(
                songId = "1EzrEOXmMH3G43AXT1y7pA",
                songArtist = "Jason Mraz",
                songName = "I'm Yours",
                albumJacket = "https://i.scdn.co/image/ab67616d00001e02125b1a330b6f6100ab19dbed",
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Well, you done, done me in, you bet I felt it",
                )
        )


        response.lyrics.add(
                Lyric(
                    lyric = "I tried to be chill, but you're so hot that I melted",
                )
        )

        response.lyrics.add(
                Lyric(

                    lyric = "I fell right through the cracks",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Now I'm trying to get back",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Before the cool done run out, I'll be givin' it my bestest",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "And nothing's gonna stop me but divine intervention",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "I reckon it's again my turn",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "To win some or learn some",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "But I won't hesitate no more, no more",

                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "It cannot wait, I'm yours",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "♪",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "♪",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Well, open up your mind and see like me",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Open up your plans and, damn, you're free",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Look into your heart, and you'll find love, love, love, love",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Listen to the music of the moment people dance and sing",
                )
        )


        response.lyrics.add(
                Lyric(
                    lyric = "We're just one big family",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "And it's our godforsaken right to be loved, loved, loved, loved, loved",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "So I won't hesitate no more, no more",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "It cannot wait, I'm sure",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "There's no need to complicate, our time is short",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "This is our fate, I'm yours",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Do, do, do, do you"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "But do you, do you, do, do"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "But do you want to come on?",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Scooch on over closer, dear",
                )
        )



        response.lyrics.add(
                Lyric(
                    lyric = "And I will nibble your ear",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "♪"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "I've been spending way too long checking my tongue in the mirror",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "And bending over backwards just to try to see it clearer",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "But my breath fogged up the glass",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "And so I drew a new face and I laughed",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "I guess what I'll be saying is there ain't no better reason",
                )
        )


        response.lyrics.add(
                Lyric(
                    lyric = "To rid yourself of vanities and just go with the seasons",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "It's what we aim to do"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Our name is our virtue",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "But I won't hesitate no more, no more",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "It cannot wait, I'm yours",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Open up your mind and see like me",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Open up your plans and, damn, you're free",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Look into your heart, and you'll find that the sky is yours",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "So please don't, please don't, please don't",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "There's no need to complicate",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "'Cause our time is short",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "This, oh, this, oh, this is our fate",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "I'm yours"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "♪"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Oh, I'm yours"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Oh, I'm yours"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Oh, whoa-oh"
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "Baby, do believe I'm yours",
                )
        )

        response.lyrics.add(
                Lyric(
                    lyric = "You best believe, you best believe I'm yours",
                )
        )

        return ResponseEntity.ok(
                responseService.getSuccessSingleResult(response,"Pronounce 문제 목록 조회 성공")
        )
    }


    /**
     * chat gpt api 사용
     */
    @PostMapping("/translate")
    fun translate(@RequestBody request: TranslateRequest): ResponseEntity<SingleResult<TranslateResponse>> {
        logger.debug { "/translate! $request" }
        return ResponseEntity.ok(responseService.getSuccessSingleResult(
                TranslateResponse(
                        lyricKo = "대충 해석된 내용"
                )
                ,"번역 성공")
        )
    }


    @PostMapping("/pronounce/submit")
    fun submitPronounce(
            @RequestPart("userFile") userFile: MultipartFile
            , @RequestPart("ttsFile") ttsFile: MultipartFile
            , @RequestPart("json") request: SubmitPronounceRequest
    ): ResponseEntity<SingleResult<SubmitPronounceResponse>> {

        logger.debug { "/fill/pronounce $request" }
        val mapper = jacksonObjectMapper()

        val sampleAiAnalyze = mapper.readValue("" +
                "{\n" +
                "  \"ref_timestamps\": [\n" +
                "    {\n" +
                "      \"word\": \"I\",\n" +
                "      \"start_time\": 0.0,\n" +
                "      \"end_time\": 0.4\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"used\",\n" +
                "      \"start_time\": 0.4,\n" +
                "      \"end_time\": 0.6\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"to\",\n" +
                "      \"start_time\": 0.6,\n" +
                "      \"end_time\": 0.7\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"rule\",\n" +
                "      \"start_time\": 0.7,\n" +
                "      \"end_time\": 0.9\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"the\",\n" +
                "      \"start_time\": 0.9,\n" +
                "      \"end_time\": 1.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"world\",\n" +
                "      \"start_time\": 1.0,\n" +
                "      \"end_time\": 1.1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"test_timestamps\": [\n" +
                "    {\n" +
                "      \"word\": \"I\",\n" +
                "      \"start_time\": 0.2,\n" +
                "      \"end_time\": 2.7\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"used\",\n" +
                "      \"start_time\": 2.7,\n" +
                "      \"end_time\": 2.9\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"to\",\n" +
                "      \"start_time\": 2.9,\n" +
                "      \"end_time\": 3.6\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"rude\",\n" +
                "      \"start_time\": 3.6,\n" +
                "      \"end_time\": 4.9\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"to\",\n" +
                "      \"start_time\": 4.9,\n" +
                "      \"end_time\": 5.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"word\": \"world\",\n" +
                "      \"start_time\": 5.5,\n" +
                "      \"end_time\": 6.5\n" +
                "    }\n" +
                "  ],\n" +
                "  \"ref_pitch_data\": {\n" +
                "    \"times\": [1.0, 1.02, 1.04, 1.06, 1.08, 1.1],\n" +
                "    \"values\": [\n" +
                "      165.56708351799784, 168.3686070363352, 170.92842657148458,\n" +
                "      173.31039491145694, 174.6309464021384, 174.63175212093432\n" +
                "    ]\n" +
                "  },\n" +
                "  \"test_pitch_data\": {\n" +
                "    \"times\": [\n" +
                "      5.5, 5.510416666666667, 5.520833333333333, 5.53125, 5.541666666666667,\n" +
                "      5.552083333333333, 5.5625, 5.572916666666667, 5.583333333333333, 5.59375,\n" +
                "      5.604166666666667, 5.614583333333333, 5.625, 5.635416666666667,\n" +
                "      5.645833333333333, 5.65625, 5.666666666666667, 5.677083333333333, 5.6875,\n" +
                "      5.697916666666667, 5.708333333333333, 5.71875, 5.729166666666667,\n" +
                "      5.739583333333333, 5.75, 5.760416666666667, 5.770833333333333, 5.78125,\n" +
                "      5.791666666666667, 5.802083333333333, 5.8125, 5.822916666666667,\n" +
                "      5.833333333333333, 5.84375, 5.854166666666667, 5.864583333333333, 5.875,\n" +
                "      5.885416666666667, 5.895833333333333, 5.90625, 5.916666666666667,\n" +
                "      5.927083333333333, 5.9375, 5.947916666666667, 5.958333333333333, 5.96875,\n" +
                "      5.979166666666667, 5.989583333333333, 6.0, 6.010416666666667,\n" +
                "      6.020833333333333, 6.03125, 6.041666666666667, 6.052083333333333, 6.0625,\n" +
                "      6.072916666666667, 6.083333333333333, 6.09375, 6.104166666666667,\n" +
                "      6.114583333333333, 6.125, 6.135416666666667, 6.145833333333333, 6.15625,\n" +
                "      6.166666666666667, 6.177083333333333, 6.1875, 6.197916666666667,\n" +
                "      6.208333333333333, 6.21875, 6.229166666666667, 6.239583333333333, 6.25,\n" +
                "      6.260416666666667, 6.270833333333333, 6.28125, 6.291666666666667,\n" +
                "      6.302083333333333, 6.3125, 6.322916666666667, 6.333333333333333, 6.34375,\n" +
                "      6.354166666666667, 6.364583333333333, 6.375, 6.385416666666667,\n" +
                "      6.395833333333333, 6.40625, 6.416666666666667, 6.427083333333333, 6.4375,\n" +
                "      6.447916666666667, 6.458333333333333, 6.46875, 6.479166666666667,\n" +
                "      6.489583333333333, 6.5\n" +
                "    ],\n" +
                "    \"values\": [\n" +
                "      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,\n" +
                "      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,\n" +
                "      0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,\n" +
                "      0.0, 126.67163111911553, 122.35395578646565, 121.6247754102202,\n" +
                "      120.62560387451754, 119.18343006254977, 117.89744500762046,\n" +
                "      118.1342730780976, 118.08445500139055, 118.54945325180833,\n" +
                "      118.76942400995969, 118.90735237846692, 119.6773027172429,\n" +
                "      122.65086608781644, 126.37976816688663, 127.59259160541846,\n" +
                "      128.12856348164246, 128.58636375902282, 129.29798079312178,\n" +
                "      129.95107736761514, 130.18280061752685, 130.16304963263937,\n" +
                "      130.09522361603587, 130.14859705257794, 130.07822015941952,\n" +
                "      130.39776736040767, 130.5388298850403, 130.51204653344357,\n" +
                "      129.89179372603087, 124.43262686290952, 128.27321550483413,\n" +
                "      123.78854310212148, 124.9137712962845, 130.78260655568087,\n" +
                "      133.47301552353946, 0.0, 0.0, 0.0, 0.0, 130.1593772663122,\n" +
                "      122.96182884400378, 117.39945253743676, 111.79471795026136,\n" +
                "      108.60080993943298, 104.12101521890497, 103.3565693227702,\n" +
                "      146.40828864918254, 142.24523933078657, 162.19363151948082,\n" +
                "      166.57327904039352, 166.39209087365126, 173.23861966391115\n" +
                "    ]\n" +
                "  },\n" +
                "  \"ref_intensity_data\": {\n" +
                "    \"times\": [1.0, 1.025, 1.05, 1.0750000000000002, 1.1],\n" +
                "    \"values\": [\n" +
                "      76.57738841707076, 77.83117659410385, 78.57103844715, 78.88173075832528,\n" +
                "      78.96840575624839\n" +
                "    ]\n" +
                "  },\n" +
                "  \"test_intensity_data\": {\n" +
                "    \"times\": [\n" +
                "      5.5, 5.508620689655173, 5.517241379310345, 5.525862068965517,\n" +
                "      5.5344827586206895, 5.543103448275862, 5.551724137931035,\n" +
                "      5.560344827586207, 5.568965517241379, 5.577586206896552,\n" +
                "      5.586206896551724, 5.594827586206897, 5.603448275862069,\n" +
                "      5.612068965517241, 5.620689655172414, 5.629310344827586,\n" +
                "      5.637931034482759, 5.646551724137931, 5.655172413793103,\n" +
                "      5.663793103448276, 5.672413793103448, 5.681034482758621,\n" +
                "      5.689655172413793, 5.698275862068965, 5.706896551724138,\n" +
                "      5.7155172413793105, 5.724137931034483, 5.732758620689655,\n" +
                "      5.741379310344827, 5.75, 5.758620689655173, 5.767241379310345,\n" +
                "      5.775862068965517, 5.7844827586206895, 5.793103448275862,\n" +
                "      5.801724137931035, 5.810344827586207, 5.818965517241379,\n" +
                "      5.827586206896552, 5.836206896551724, 5.844827586206897,\n" +
                "      5.853448275862069, 5.862068965517241, 5.870689655172414,\n" +
                "      5.879310344827586, 5.887931034482759, 5.896551724137931,\n" +
                "      5.905172413793103, 5.913793103448276, 5.922413793103448,\n" +
                "      5.931034482758621, 5.939655172413793, 5.948275862068965,\n" +
                "      5.956896551724138, 5.9655172413793105, 5.974137931034483,\n" +
                "      5.982758620689655, 5.991379310344827, 6.0, 6.008620689655173,\n" +
                "      6.017241379310345, 6.025862068965517, 6.0344827586206895,\n" +
                "      6.043103448275862, 6.051724137931035, 6.060344827586206,\n" +
                "      6.068965517241379, 6.077586206896552, 6.086206896551724,\n" +
                "      6.094827586206897, 6.103448275862069, 6.112068965517241,\n" +
                "      6.120689655172414, 6.129310344827586, 6.137931034482759,\n" +
                "      6.146551724137931, 6.155172413793103, 6.163793103448276,\n" +
                "      6.172413793103448, 6.181034482758621, 6.189655172413794,\n" +
                "      6.198275862068965, 6.206896551724138, 6.2155172413793105,\n" +
                "      6.224137931034483, 6.232758620689655, 6.241379310344827, 6.25,\n" +
                "      6.258620689655173, 6.267241379310345, 6.275862068965517,\n" +
                "      6.2844827586206895, 6.293103448275862, 6.301724137931035,\n" +
                "      6.310344827586206, 6.318965517241379, 6.327586206896552,\n" +
                "      6.336206896551724, 6.344827586206897, 6.353448275862069,\n" +
                "      6.362068965517241, 6.370689655172414, 6.379310344827586,\n" +
                "      6.387931034482759, 6.396551724137931, 6.405172413793103,\n" +
                "      6.413793103448276, 6.422413793103448, 6.431034482758621,\n" +
                "      6.439655172413793, 6.448275862068965, 6.456896551724138,\n" +
                "      6.4655172413793105, 6.474137931034483, 6.482758620689655,\n" +
                "      6.491379310344827, 6.5\n" +
                "    ],\n" +
                "    \"values\": [\n" +
                "      42.60877441549158, 43.31571277749596, 44.01516459217306,\n" +
                "      45.185476008539986, 47.19525328772691, 49.458600217055974,\n" +
                "      49.156278572489576, 46.55797019344136, 44.373948052507046,\n" +
                "      44.913062359026725, 46.320169804713, 46.09902010563609, 43.73097317021006,\n" +
                "      41.78929233016011, 42.90225878292671, 44.38921618223422,\n" +
                "      45.11466927062245, 47.29313052100131, 49.69699839860751,\n" +
                "      50.69758241897369, 51.0909031633685, 51.40102936040823,\n" +
                "      51.004819612578444, 50.253156591129994, 49.768519293012076,\n" +
                "      49.310153193818316, 48.518470094989766, 47.576873163533634,\n" +
                "      47.099991821550226, 46.60455747431265, 45.074707704276875,\n" +
                "      43.5830711941218, 43.18436067550343, 42.45919724613977,\n" +
                "      41.930140848381455, 42.39307678347562, 42.65652424097602,\n" +
                "      42.6674928405336, 42.459197731182016, 43.702442002219364,\n" +
                "      46.81907254738857, 48.31968885578906, 47.69233645587352,\n" +
                "      45.470796804651776, 44.50268639931618, 45.03703694099051,\n" +
                "      45.76134043587547, 47.914411019008824, 48.63219777441745,\n" +
                "      47.39145400171676, 48.31736986380997, 53.97137041739428,\n" +
                "      63.641274773072396, 70.7540167748556, 74.51242170311426,\n" +
                "      76.16755336698236, 77.42991945176794, 78.66068234478709,\n" +
                "      79.63100628667506, 80.41436174029464, 81.25795998435987,\n" +
                "      82.38240722352214, 83.58424614716718, 84.6084327986519, 85.42395729652823,\n" +
                "      85.99726082747452, 86.2697742412225, 86.16868673511037, 85.61033441348648,\n" +
                "      84.30573744671767, 82.27925568478753, 80.83462121824526,\n" +
                "      80.97776011390671, 81.5075435896571, 81.67068763472818, 81.49118997402095,\n" +
                "      81.22358012455891, 81.02458011310841, 80.89537179855681,\n" +
                "      80.75688320938167, 80.55005953656524, 80.33620256486579,\n" +
                "      80.13608097807882, 79.9149214189148, 79.77573395908983, 79.74603305415029,\n" +
                "      79.74677182265705, 79.5391952180121, 78.8109082457761, 77.32878972559276,\n" +
                "      75.07410292515424, 73.22334694717811, 72.14383800530382,\n" +
                "      71.12761743146413, 70.1441435484387, 68.79825192621541, 66.21803878150189,\n" +
                "      62.07960586042656, 58.90727904633568, 59.262460914740736,\n" +
                "      60.863490945808124, 67.94292818221992, 73.87247638061484, 76.660474694892,\n" +
                "      77.28792145529427, 77.00596103955333, 76.96599054501107,\n" +
                "      77.18657841684674, 76.63202882462397, 74.98999908214418,\n" +
                "      73.53157603802897, 72.60433906359864, 72.35524302407521, 70.8517701490396,\n" +
                "      67.5762315887435, 69.61427705408038, 71.19266778025694\n" +
                "    ]\n" +
                "  },\n" +
                "  \"ref_formants_avg\": {\n" +
                "    \"F1\": [624.0388192921818, 0, 0, 0, 0, 0],\n" +
                "    \"F2\": [2402.9600894131695, 0, 0, 0, 0, 0]\n" +
                "  },\n" +
                "  \"test_formants_avg\": {\n" +
                "    \"F1\": [517.6092089065803, 0, 0, 0, 0, 0],\n" +
                "    \"F2\": [1673.153836041118, 0, 0, 0, 0, 0]\n" +
                "  }\n" +
                "}"
                , LyricAiAnalyze::class.java)

        val response = SubmitPronounceResponse(
                userPronounce = "https://file-examples.com/wp-content/storage/2017/04/file_example_MP4_480_1_5MG.mp4",
                ttsPronounce = "https://file-examples.com/wp-content/storage/2017/04/file_example_MP4_480_1_5MG.mp4",
                lyricSentenceEn = "Before the cool done run out, I'll be givin' it my bestest",
                lyricSentenceKo = "이 기분이 사라지기 전에 내 최고의 것을 드릴께요",
                userLyricSTT = "Before the coll done ran about, I will been give' it my best",
                lyricAiAnalyze = sampleAiAnalyze
        )
        return ResponseEntity.ok(responseService.getSuccessSingleResult(response,"Pronounce 제출 완료"))
    }

}