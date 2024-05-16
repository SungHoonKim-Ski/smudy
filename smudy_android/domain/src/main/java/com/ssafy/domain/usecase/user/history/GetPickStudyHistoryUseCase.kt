package com.ssafy.domain.usecase.user.history

import com.ssafy.domain.repository.StudyHistoryRepository
import javax.inject.Inject

class GetPickStudyHistoryUseCase @Inject constructor(
    private val studyHistoryRepository: StudyHistoryRepository
) {
    suspend operator fun invoke(learnReportId:String) = studyHistoryRepository.getPickHistory(learnReportId)
}