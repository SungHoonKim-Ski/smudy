package com.ssafy.userservice.db.postgre.entity.ai

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable

@Embeddable
data class EntityFormantsAvg (

    @ElementCollection
    var f1: List<Double>,

    @ElementCollection
    var f2: List<Double>,
)