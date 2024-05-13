package com.ssafy.studyservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class Similarity(
        val newSentence: String,
        val cosineSimilarity: Double
)