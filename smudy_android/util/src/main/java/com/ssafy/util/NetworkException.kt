package com.ssafy.util

class NetworkException(
    val code: String,
    override val message: String
) : Exception(message) {

    companion object {
        const val NOT_FOUND_USER="404"
        const val BAD_REQUEST="400"
        const val UNAUTHORIZED="401"
        const val INTERNAL_SERVER_ERROR="500"
    }
}