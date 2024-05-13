package com.ssafy.userservice.util

fun <T> T.isNull() : Boolean {
    return this == null
}

fun <T> T.isNotNull() : Boolean {
    return this != null
}