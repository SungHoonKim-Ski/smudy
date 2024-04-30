package com.ssafy.authservice.repository

import com.ssafy.authservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUserInternalId(internalId: UUID): User?
}