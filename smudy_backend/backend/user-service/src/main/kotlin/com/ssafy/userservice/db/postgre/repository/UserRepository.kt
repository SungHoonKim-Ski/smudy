package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByUserSnsId(snsId: String): User?

    fun findByUserInternalId(userInternalId: UUID) : User?
}