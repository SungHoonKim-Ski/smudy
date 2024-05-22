package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.Wrong
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WrongRepository : JpaRepository<Wrong, Int> {
    @Query("" +
            "SELECT " +
            "* " +
            "FROM " +
            "wrong " +
            "WHERE " +
            "user_internal_id = :userInternalId " +
            "ORDER BY " +
            "RANDOM() LIMIT 1"
            , nativeQuery = true)
    fun findRandomWrongSentenceByUserInternalId(@Param("userInternalId") userInternalId: UUID): Wrong?
}