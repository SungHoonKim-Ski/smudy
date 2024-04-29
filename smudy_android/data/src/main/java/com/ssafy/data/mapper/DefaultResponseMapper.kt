package com.ssafy.data.mapper

import com.ssafy.data.model.DefaultResponse

fun <T> Result<DefaultResponse<T>>.toNonDefault(): Result<T> {
    return this.map { it.data}
}