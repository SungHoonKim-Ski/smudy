package com.ssafy.userservice.db.postgre.repository

import com.ssafy.userservice.db.postgre.entity.StudyList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyListRepository : JpaRepository<StudyList, Int> {
}