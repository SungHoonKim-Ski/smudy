package com.ssafy.studyservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class Similarity (

        @JsonProperty("new_sentence")
        val newSentence: String,

        @JsonProperty("cosine_similarity")
        val cosineSimilarity: Double,
)