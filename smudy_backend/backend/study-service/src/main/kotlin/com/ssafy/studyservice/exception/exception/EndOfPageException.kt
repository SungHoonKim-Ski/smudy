package com.ssafy.studyservice.exception.exception

class EndOfPageException : RuntimeException {
    constructor(msg: String, t: Throwable) : super(msg, t)
    constructor(msg: String) : super(msg)
    constructor() : super()
}