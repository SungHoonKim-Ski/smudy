package com.ssafy.studyservice.exception.exception

class RequestNotNumberException : RuntimeException {
    constructor(msg: String, t: Throwable) : super(msg, t)
    constructor(msg: String) : super(msg)
    constructor() : super()
}