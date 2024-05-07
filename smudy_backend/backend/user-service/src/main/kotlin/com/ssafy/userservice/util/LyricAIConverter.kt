package com.ssafy.userservice.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class LyricAIConverter<T : Any> : AttributeConverter<LyricAiAnalyze, String> {

    private val mapperConfig = ObjectMapperConfig()
    private val objectMapper: ObjectMapper = mapperConfig.getObjectMapper()

    override fun convertToDatabaseColumn(attribute: LyricAiAnalyze?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): LyricAiAnalyze? {
        return dbData?.let { objectMapper.readValue(it, LyricAiAnalyze::class.java) }
    }
}
