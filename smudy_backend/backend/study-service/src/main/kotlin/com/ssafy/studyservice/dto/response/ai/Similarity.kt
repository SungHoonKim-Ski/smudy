package com.ssafy.studyservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class Similarity(
        @get: JsonProperty("new_sentence")
        val newSentence: String,

        @get: JsonProperty("cosine_similarity")
        val cosineSimilarity: Double
)