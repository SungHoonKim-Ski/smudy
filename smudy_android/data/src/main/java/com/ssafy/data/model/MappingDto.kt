package com.ssafy.data.model

interface MappingDto<T: Any> {
    fun toDomain():T
}