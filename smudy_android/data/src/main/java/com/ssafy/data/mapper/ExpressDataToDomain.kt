package com.ssafy.data.mapper

import com.ssafy.data.model.music.express.ExpressGradedResultResponse
import com.ssafy.data.model.music.express.ExpressHistoryDetailResponse
import com.ssafy.data.model.music.express.ExpressProblemResponse
import com.ssafy.data.model.music.express.ExpressQuestionProblemResponse
import com.ssafy.domain.model.study.express.ExpressGradedResult
import com.ssafy.domain.model.study.express.ExpressProblem
import com.ssafy.domain.model.study.express.ExpressQuestionProblem

fun ExpressProblemResponse.toExpressProblem(): ExpressProblem =
    ExpressProblem(problemBoxId,songArtist,songId,songName,albumJacket, problems.map { it.toExpressQuestionProblem() })

fun ExpressQuestionProblemResponse.toExpressQuestionProblem(): ExpressQuestionProblem =
    ExpressQuestionProblem(lyricSentenceEn, lyricSentenceKo)

fun ExpressGradedResultResponse.toExpressGradedResult(): ExpressGradedResult =
    ExpressGradedResult(lyricSentenceEn, lyricSentenceKo, userLyricSentenceEn, userLyricSentenceKo, suggestLyricSentence, score)

fun ExpressHistoryDetailResponse.toExpressGradedResult(): ExpressGradedResult =
    ExpressGradedResult(lyricSentenceEn, lyricSentenceKo, userLyricSentenceEn, userLyricSentenceKo, suggestLyricSentence, score)