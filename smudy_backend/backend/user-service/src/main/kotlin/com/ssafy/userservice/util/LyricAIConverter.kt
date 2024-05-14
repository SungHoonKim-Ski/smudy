package com.ssafy.userservice.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ssafy.userservice.config.ObjectMapperConfig
import com.ssafy.userservice.db.postgre.entity.ai.EntityLyricAiAnalyze
import com.ssafy.userservice.dto.response.ai.LyricAiAnalyze
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LyricAIConverter : AttributeConverter<EntityLyricAiAnalyze, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: EntityLyricAiAnalyze?): String? {
        if (attribute == null) return null
        return try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("JSON writing error", e)
        }
    }

    override fun convertToEntityAttribute(dbData: String?): EntityLyricAiAnalyze? {
        if (dbData == null) return null
        return try {
            objectMapper.readValue(dbData, LyricAiAnalyze::class.java).parseEntity()
        } catch (e: JsonProcessingException) {
            throw IllegalArgumentException("JSON reading error", e)
        }
    }
}