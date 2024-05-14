package com.ssafy.userservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class LyricAIConverter<T : Any> : AttributeConverter<EntityLyricAiAnalyze, String> {

    private val mapperConfig = ObjectMapperConfig()
    private val objectMapper: ObjectMapper = mapperConfig.getObjectMapper()

    override fun convertToDatabaseColumn(attribute: EntityLyricAiAnalyze?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): EntityLyricAiAnalyze? {
        return dbData?.let { objectMapper.readValue(it, EntityLyricAiAnalyze::class.java) }
    }
}
