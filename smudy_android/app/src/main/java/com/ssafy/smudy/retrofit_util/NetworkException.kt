package com.ssafy.smudy.retrofit_util

class NetworkException(
    val code: String,
    override val message: String
) : Exception(message) {

    companion object {

    }
}