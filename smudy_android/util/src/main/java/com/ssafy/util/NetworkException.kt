package com.ssafy.util

class NetworkException(
    val code: String,
    override val message: String
) : Exception(message) {

    companion object {
        val NOT_FOUND_USER="404"
    }
}