package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByUserSnsId(snsId: String): Optional<User>
}