package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.Wrong
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WrongRepository : JpaRepository<Wrong, Int> {
}