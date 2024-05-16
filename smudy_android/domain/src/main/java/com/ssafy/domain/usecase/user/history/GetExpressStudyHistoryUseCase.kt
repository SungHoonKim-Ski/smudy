package com.ssafy.domain.usecase.user.history

import com.ssafy.domain.repository.StudyHistoryRepository
import javax.inject.Inject

class GetExpressStudyHistoryUseCase @Inject constructor(
    private val studyHistoryRepository: StudyHistoryRepository
) {
    suspend operator fun invoke(learnReportId:String) = studyHistoryRepository.getExpressHistory(learnReportId)
}