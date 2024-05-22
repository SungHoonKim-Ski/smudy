package com.ssafy.domain.usecase.study.pronounce

import com.ssafy.domain.repository.PronounceRepository
import java.io.File
import javax.inject.Inject

class GradePronounceProblemUseCase @Inject constructor(
    private val pronounceRepository: PronounceRepository
) {
    suspend operator fun invoke(
        userFile: File,
        ttsFile: File,
        lyric: String,
        lyricKo: String,
        id: String
    ) =
        pronounceRepository.gradePronounceProblem(userFile, ttsFile, lyric, lyricKo, id)
}