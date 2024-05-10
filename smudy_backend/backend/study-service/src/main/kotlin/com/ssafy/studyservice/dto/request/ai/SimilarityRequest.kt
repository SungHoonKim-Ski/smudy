package com.ssafy.studyservice.dto.request.ai

data class SimilarityRequest (
        private val sentence1: String,
        private val sentence2: String,
)