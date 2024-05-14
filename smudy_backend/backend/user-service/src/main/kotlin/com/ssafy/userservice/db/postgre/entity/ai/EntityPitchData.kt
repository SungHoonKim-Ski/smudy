package com.ssafy.userservice.db.postgre.entity.ai

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable

@Embeddable
data class EntityPitchData(

        @ElementCollection
        var times: List<Double>,

        @ElementCollection
        var values: List<Double>
)