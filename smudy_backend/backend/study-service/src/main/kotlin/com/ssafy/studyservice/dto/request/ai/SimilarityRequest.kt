package com.ssafy.studyservice.dto.request.ai

data class SimilarityRequest (
        private val sentence1: String = "hello world",
        private val sentence2: String = "hello",
)