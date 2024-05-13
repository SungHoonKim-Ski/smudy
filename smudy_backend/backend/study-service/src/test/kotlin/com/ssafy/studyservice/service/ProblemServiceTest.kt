package com.ssafy.studyservice.service

import com.ssafy.studyservice.db.postgre.entity.Problem
import org.assertj.core.api.Assertions.assertThat
import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


//@SpringBootTest
//@EnabledIfEnvironmentVariables
//class ProblemServiceTest @Autowired constructor(
class ProblemServiceTest constructor(
//        private val problemService: ProblemService,
) {

//    companion object {
//
//        @JvmStatic
//        @BeforeAll
//        fun before() {
//            System.setProperty("JASYPT_PASSWORD", System.getenv("JASYPT_PASSWORD"))
//            System.setProperty("MONGO_PORT", System.getenv("MONGO_PORT"))
//        }
//
//    }


//    @Test
//    fun isProblemCountIs5_WhenProblemBoxIdGiven() {
//
//        val MIN_INDEX = 3272
//        val MAX_INDEX = 6205
//
//        for (problemBoxId in MIN_INDEX..MAX_INDEX) {
//            // given
//
//            // when
//            val results = problemService.getProblemsByProblemBoxId(problemBoxId)
//
//            // then
//            assertThat(results.size).isEqualTo(5)
//            assertThat(results.associateBy(Problem::lyricId).size).isEqualTo(5)
//        }
//    }

}