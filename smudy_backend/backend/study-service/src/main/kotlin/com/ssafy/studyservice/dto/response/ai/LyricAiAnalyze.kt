package com.ssafy.studyservice.dto.response.ai

data class LyricAiAnalyze(
        val ref_timestamps: List<Timestamp>,
        val test_timestamps: List<Timestamp>,
        val ref_pitch_data: PitchData,
        val test_pitch_data: PitchData,
        val ref_intensity_data: IntensityData,
        val test_intensity_data: IntensityData,
        val ref_formants_avg: FormantsAvg,
        val test_formants_avg: FormantsAvg
)
