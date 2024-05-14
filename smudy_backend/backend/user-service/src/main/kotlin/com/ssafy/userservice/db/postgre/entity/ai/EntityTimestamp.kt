package com.ssafy.userservice.db.postgre.entity.ai

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Embeddable

@Embeddable
data class EntityTimestamp(

        var word: String,

        var startTime: Double,

        var endTime: Double,
)