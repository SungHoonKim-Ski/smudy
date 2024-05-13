package com.ssafy.studyservice.dto.request.ai

data class SimilarityRequest (
        val sentence1: String,
        val sentence2: String,
)