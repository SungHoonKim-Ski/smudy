package com.ssafy.userservice.db.postgre.entity

import java.sql.Date

class Streak {
    var strictId: Long = 0
    var userId: Long = 0
    var songJacket: String? = null
    var streakDate: Date? = null
}