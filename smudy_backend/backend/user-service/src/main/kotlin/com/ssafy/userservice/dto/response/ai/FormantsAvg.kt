package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class FormantsAvg(

        @get: JsonProperty("F1")
        val f1: List<Double>,

        @get: JsonProperty("F2")
        val f2: List<Double>
)