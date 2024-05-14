package com.ssafy.userservice.db.postgre.entity.ai

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable

@Embeddable
data class EntityFormantData(
        @ElementCollection
        var F1: List<Double>,

        @ElementCollection
        var F2: List<Double>,
)
