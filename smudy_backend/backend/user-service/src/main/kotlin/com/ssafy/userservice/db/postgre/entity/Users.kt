package com.ssafy.userservice.db.postgre.entity

import java.sql.Date

class Users {
    var userId: Long = 0
    var userInternalId: String? = null
    var userSnsId: String? = null
    var userName: String? = null
    var userImage: String? = null
    var userExp: Long = 0
    var userCreatedAt: Date? = null
    var userDeletedAt: Date? = null
    var userIsActivate: String? = null
}