package com.ssafy.userservice.dto.response.ai

import com.fasterxml.jackson.annotation.JsonProperty

data class FormantsAvg(

        val `F1`: List<Double>,

        val `F2`: List<Double>
)