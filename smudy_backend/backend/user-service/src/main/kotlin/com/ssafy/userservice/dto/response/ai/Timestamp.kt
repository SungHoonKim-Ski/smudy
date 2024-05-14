package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class Timestamp(

        val word: String,

        @get: JsonProperty("start_time")
        val startTime: Double,

        @get: JsonProperty("end_time")
        val endTime: Double
)
