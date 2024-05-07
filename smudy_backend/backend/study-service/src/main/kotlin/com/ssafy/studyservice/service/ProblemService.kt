package com.ssafy.studyservice.service

import com.ssafy.studyservice.db.postgre.entity.Problem
import com.ssafy.studyservice.db.postgre.repository.ProblemRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProblemService (
        private val problemRepository: ProblemRepository
){


    @Transactional
    fun getProblemsByProblemBoxId(problemBoxId: Int) : List<Problem>{
        return problemRepository.findByProblemBoxId(problemBoxId)
    }
}