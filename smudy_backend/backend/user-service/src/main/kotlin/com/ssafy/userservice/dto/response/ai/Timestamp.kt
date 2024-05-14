package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty


data class Timestamp(

        val word: String,

        val start_time: Double,

        val end_time: Double
)
