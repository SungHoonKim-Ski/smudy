package com.ssafy.userservice.exception.exception

class SongNotFoundException : RuntimeException {
    constructor(msg: String, t: Throwable) : super(msg, t)
    constructor(msg: String) : super(msg)
    constructor() : super()
}